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

        long timeStartReflecting; //начало ожидания
        long timeStartEating; //начал есть
        this.name = Thread.currentThread().getName();
        timeStartReflecting = System.currentTimeMillis();
        boolean interruptFlag = false;

        while (true) {
            if (Thread.interrupted()) break;
            if (System.currentTimeMillis() - timeStartReflecting < this.reflectTime) {
                interruptFlag = reflect();
                reflectSum += System.currentTimeMillis() - timeStartReflecting;
                if (interruptFlag) break;
                timeStartReflecting = System.currentTimeMillis();
            }

                //филосов пытается взять две вилки
            if (left.take(this) && right.take(this)) {
                //филосов взял 2 вилки

                reflectSum += System.currentTimeMillis() - timeStartReflecting;
                timeStartEating = System.currentTimeMillis();
                interruptFlag = eat();
                eatSum += System.currentTimeMillis() - timeStartEating;
                if (interruptFlag) break;
                timeStartReflecting = System.currentTimeMillis();
            }
                //филосов кладет вилки назад
            left.put(this);
            right.put(this);
        }

        return this;
    }

    boolean reflect() {
        //филосов размышляет
        try {
            Thread.sleep(this.reflectTime);
        } catch (InterruptedException e) {
            return true;
        }
        return false;
    }

    boolean eat(){
        //филосов ест
        try {
            Thread.sleep(this.eatTime);
        } catch (InterruptedException e) {
            return true;
        }
        return false;
    }
}
