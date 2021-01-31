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
    static final int PROCENT = 0; //Увеличиваем требуемую память в процентах для каждого потока

    public static void main(String[] args) throws InterruptedException {

        ReadWriteLock lock = new ReentrantReadWriteLock();
        HeapInterface heap = null;
        long timeStart;
        int proc = ARRAY_SIZE / (THREAD_COUNT * 5) * PROCENT / 100;
        Thread[] threads = new Thread[THREAD_COUNT];

        for (int i = 1; i <= 2; i ++) {
            //Тестируем классы Heap с различными алгоритмами
            if (i == 1) heap = new Heap1(ARRAY_SIZE);
            else if (i == 2) heap = new Heap2(ARRAY_SIZE, lock);

            timeStart = System.currentTimeMillis();
            for (int k = 0; k < THREAD_COUNT; k++) {
                threads[k] = new Thread(new ThreadHeap(heap, ARRAY_SIZE / (THREAD_COUNT * (k + 5)) + proc, k));
                threads[k].start();
                threads[k].join();
            }
            System.out.println("Время работы HEAP" + i + ": " + (System.currentTimeMillis() - timeStart));
        }

    }
}
