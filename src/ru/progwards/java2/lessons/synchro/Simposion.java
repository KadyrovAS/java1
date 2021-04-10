package ru.progwards.java2.lessons.synchro;

import java.util.concurrent.*;
enum ForkCondition{WasTaken, Released, Interrapted} //была взята, освободилась, прерывание извне

public class Simposion {
    static final long ALLTIME = 20_000;
    long reflectTime; // время в мс, через которое философ проголодается
    long eatTime; // время в мс, через которое получив 2 вилки философ наестся и положит вилки на место
    ExecutorService executor;
    Future<Philosopher>[] futures;

    Simposion(long reflectTime, long eatTime) {
        this.reflectTime = reflectTime;
        this.eatTime = eatTime;
        this.executor = Executors.newFixedThreadPool(5);
        this.futures = new Future[5];
    }

    void start() throws Exception {
        //запускает философскую беседу
        Fork[] forks = new Fork[5];
        for (int i = 0; i < 5; i ++)
            forks[i] = new Fork("№" + (i + 1));

        for (int i = 0; i < 5; i ++){
            int k = (i == 4 ? 0: i + 1);
            futures[i] = executor.submit(new Philosopher(forks[i], forks[k], reflectTime, eatTime));
        }

        Thread.sleep(ALLTIME);
        stop();
        print();
    }

    void stop(){
        //завершает философскую беседу
        executor.shutdownNow();
    }

    void print() throws ExecutionException, InterruptedException {
        //печатает результаты беседы в формате Философ name, ел ххх, размышлял xxx, где ххх время в мс
        for (int i = 0; i < 5; i ++) {
            Philosopher philosopher = futures[i].get();
            System.out.println(philosopher.name + " ел " + philosopher.eatSum + " размышлял " + philosopher.reflectSum);
        }
    }

    public static void main(String[] args) throws Exception {
        Simposion simposion = new Simposion(1000,1000);
        simposion.start();
    }
}