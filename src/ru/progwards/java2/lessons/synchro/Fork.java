package ru.progwards.java2.lessons.synchro;

public class Fork{
    private boolean condition;
    String forkName;
    Philosopher philosopher = null;

    public Fork(String forkName) {
        this.forkName = forkName;
        this.condition = true; //Вилка свободна
    }

    public boolean get() {
        return this.condition;
    }

    public synchronized ForkCondition take(Philosopher philosopher)  {
        if (this.condition && (this.philosopher == null || !this.philosopher.equals(philosopher))) {
            this.condition = false;
            this.philosopher = philosopher;
            return ForkCondition.WasTaken; //Филосов взял вилку
        }
        //если ранее философ взял какую-либо вилку, то нужно ее положить
        philosopher.left.put(philosopher);
        philosopher.right.put(philosopher);
        try {
            wait();
            return ForkCondition.Released; //вилка освободилась
        }
        catch (InterruptedException e) {
            return ForkCondition.Interrapted; //поток был прерван извне
        }
    }

    public synchronized void put(Philosopher philosopher) {
        if (philosopher.equals(this.philosopher))
            this.condition = true; //Филосов положил вилку. Вилка свободна
        notify();
    }
}