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
        ForkCondition takeForkLeft; //состояние левой вилки
        ForkCondition takeForkRight; //состояние правой вилки
        String line = Thread.currentThread().getName();
        this.name = "Философ №" + line.substring(line.length() - 1);

        while (true) {
            takeForkLeft = left.take(this);
            if (takeForkLeft == ForkCondition.Interrapted) return this;
            if (takeForkLeft == ForkCondition.Released) continue;

            takeForkRight = right.take(this);
            if (takeForkRight == ForkCondition.Interrapted) return this;
            if (takeForkRight == ForkCondition.Released) continue;

            this.eatSum += this.eatTime;
            if (eat()) return this;
            left.put(this);
            right.put(this);
            this.reflectSum += this.reflectTime;
            if (reflect()) return this;
        }
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
        System.out.println(this.name + " кушает вилками " + left.forkName + " и " + right.forkName);
        try {
            Thread.sleep(this.eatTime);
        } catch (InterruptedException e) {
            return true;
        }
        return false;
    }
}