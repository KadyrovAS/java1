package ru.progwards.java2.lessons.synchro;

import java.util.concurrent.Callable;

public class Philosopher implements Callable<Philosopher>{
    String name; //имя философа
    Fork right; //вилка справа
    Fork left; //вилка слева
    long reflectTime; //время, которое философ размышляет в мс
    long eatTime; //время, которое философ ест в мс
    long reflectSum; //суммарное время, которое философ размышлял в мс
    long eatSum; //суммарное время, которое философ ел в мс

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
        this.name = Thread.currentThread().getName();
        while (true) {
            if (Thread.interrupted()) break;
            //филосов пытается взять две вилки
            if (left.take(this) && right.take(this)) {
                //филосов взял 2 вилки
                this.eatSum += this.eatTime;
                if (eat()) break;
            }
            //филосов кладет вилки назад
            left.put(this);
            right.put(this);
            this.reflectSum += this.reflectTime;
            if (reflect()) break;
        }

        return this;
    }

    boolean reflect() {
        //филосов размышляет
        System.out.println(this.name + " размышляет");
        try {
            Thread.sleep(this.reflectTime);
        } catch (InterruptedException e) {
            return true;
        }
        return false;
    }

    boolean eat(){
    //филосов ест
        System.out.println(this.name + " кушает");
        try {
            Thread.sleep(this.eatTime);
        } catch (InterruptedException e) {
            return true;
        }
        return false;
    }
}