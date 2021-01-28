package ru.progwards.java2.lessons.synchro;

import java.util.Random;
import java.util.TreeMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class ThreadHeap implements Runnable{
    HeapInterface heap;
    int arrayAlloc;
    int localValueSize;
    ThreadHeap(HeapInterface heap, int arrayAlloc, int localValueSize){
        this.heap = heap;
        this.arrayAlloc = arrayAlloc;
        this.localValueSize = localValueSize;
    }

    @Override
    public void run() {
        TreeMap<Integer,String> treemap = new TreeMap<>();
        Random random = new Random();
        int value1 = (int) Math.pow(10,localValueSize);
        int value2 = (int) Math.pow(10, localValueSize + 1);

        for (int i = 0; i < 10_000; i ++){
            //записываем в массив
            random.ints(arrayAlloc, value1, value2).forEach(value -> {
                try {
                    int ptr = heap.put(String.valueOf(value));
                    treemap.put(ptr, String.valueOf(value));
                } catch (OutOfMemoryException e) {
                    System.out.println(e.getMessage());
                }
            });

            // выполняем проверку
            for (int ptr: treemap.keySet()) 
                if (treemap.get(ptr).compareTo(heap.getBytes(ptr)) != 0)
                    System.out.println("Ошибка!!! Прочитанные данные не соответствуют записанным!!!");
            //очищаем heap
            for (int ptr: treemap.keySet()) {
                try {
                    heap.free(ptr);
                } catch (InvalidPointerException e) {
                    e.printStackTrace();
                }
            }
            treemap.clear();
        }

    }
}

public class HeapTest{
    static final int ARRAY_SIZE = 100_000;
    static final int THREAD_COUNT = 10;
    static final int PROCENT = 2; //Увеличиваем требуемую память в процентах для каждого потока

    public static void main(String[] args) throws InterruptedException {

        ReadWriteLock lock = new ReentrantReadWriteLock();
        Heap heap = new Heap(ARRAY_SIZE);
        Heap2 heap2 = new Heap2(ARRAY_SIZE, lock);
        int proc = ARRAY_SIZE / (THREAD_COUNT * 5) * PROCENT / 100;
        Thread[] threads = new Thread[THREAD_COUNT];

        //Тестируем класс Heap с использованием synchronized
        long timeStart = System.currentTimeMillis();
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new Thread(new ThreadHeap(heap,ARRAY_SIZE / (THREAD_COUNT * (i + 5)) + proc, i ));
            threads[i].start();
            threads[i].join();
        }
        System.out.println("Время работы HEAP: " + (System.currentTimeMillis() - timeStart));

        //Тестируем класс Heap2 с использованием ReadWriteLock
        timeStart = System.currentTimeMillis();
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new Thread(new ThreadHeap(heap2,ARRAY_SIZE / (THREAD_COUNT * (i + 5)) + proc, i ));
            threads[i].start();
            threads[i].join();
        }
        System.out.println("Время работы HEAP2: " + (System.currentTimeMillis() - timeStart));
    }
}
