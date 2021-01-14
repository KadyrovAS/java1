package ru.progwards.java2.lessons.threads;

import java.util.concurrent.locks.ReentrantLock;

public class PrintScan{
    final static int ARRAYSIZE = 2;
    static ReentrantLock lock = new ReentrantLock();
    static void print(String name, int[] pages) throws InterruptedException {
        Thread.sleep(50);
        lock.lock();
        for (int i = 0; i < pages.length; i ++)
            System.out.println("print <" + name + "> " + pages[i]);
        lock.unlock();
    }

    static void scan(String name, int[] pages) throws InterruptedException {
        Thread.sleep(70);
        lock.lock();
        for (int i = 0; i < pages.length; i ++)
            System.out.println("input <" + name + "> " + pages[i]);
        lock.unlock();
    }

    static class Print implements Runnable {
        int[]pages = new int[ARRAYSIZE];
        Print(){
            for (int i = 0; i < ARRAYSIZE; i++) pages[i] = i;
        }
        @Override
        public void run() {
            try {
                print(Thread.currentThread().getName(), pages);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Scan implements Runnable {
        int[]pages = new int[ARRAYSIZE];
        Scan(){
            for (int i = 0; i < ARRAYSIZE; i++) pages[i] = i;
        }
        @Override
        public void run() {
            try {
                scan(Thread.currentThread().getName(), pages);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        Thread[] threads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0)
                threads[i] = new Thread(new Print());
            else
                threads[i] = new Thread(new Scan());
            threads[i].start();
        }
    }
}
