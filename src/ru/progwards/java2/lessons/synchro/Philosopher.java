package ru.progwards.java2.lessons.synchro;

import java.util.concurrent.Callable;

public class Philosopher implements Callable<Philosopher>{
    String name; //Имя философа
    Fork right; //вилка справа
    Fork left; //вилка слева
    long reflectTime; //время, которое философ размышляет в мс
    long eatTime; //время, которое философ ест в мс
    long reflectSum; //суммарное время, которое философ размышлял в мс
    long eatSum; //суммарное время, которое философ ел в мс
    long period = 500; //период времени в мс, через который делать сообщения

    public Philosopher(Fork right, Fork left, long reflectTime, long eatTime){
        this.right = right;
        this.left = left;
        this.reflectSum = 0;
        this.eatSum = 0;
        this.reflectTime = reflectTime;
        this.eatTime = eatTime;
    }

    @Override
    public Philosopher call() {
        System.out.println("Я филосов " + Thread.currentThread().getName());

        long timeStartReflecting; //начало ожидания
        long timeStartEating; //начал есть
        this.name = Thread.currentThread().getName();
        timeStartReflecting = System.currentTimeMillis();
        int count = 0;

        while (true) {
                count = reflect(timeStartReflecting, count);

                if (Thread.interrupted()) break;

                //пытаемся взять две вилки
            if (left.take(this) && right.take(this)) {
                //филосов кушает
                reflectSum += System.currentTimeMillis() - timeStartReflecting;

                timeStartEating = System.currentTimeMillis();
                eat(timeStartEating);
                eatSum += System.currentTimeMillis() - timeStartEating;
                timeStartReflecting = System.currentTimeMillis();
                count = 0;
            }
                //филосов поел и кладет вилки
            left.put(this);
            right.put(this);

            if (Thread.interrupted()) break;
        }

        System.out.println(Thread.currentThread().getName() + " завершил работу!!!");
        return this;
    }

    int reflect(long timeStartReflecting, int count) {
        //филосов размышляет
        while (System.currentTimeMillis() - timeStartReflecting < this.reflectTime) {
            if (Thread.interrupted()) return count;
            if (period * count < System.currentTimeMillis() - timeStartReflecting) {
                System.out.println("Размышляет " + Thread.currentThread().getName());
                count++;
            }
        }
        return count;
    }

    void eat(long timeStartEating){
        //филосов ест
        int count = 0;

        while (System.currentTimeMillis() - timeStartEating < this.eatTime) {
            if (Thread.interrupted()) return;
            if (period * count < System.currentTimeMillis() - timeStartEating) {
                System.out.println("Ест " + Thread.currentThread().getName());
                count++;
            }
        }
    }
}
