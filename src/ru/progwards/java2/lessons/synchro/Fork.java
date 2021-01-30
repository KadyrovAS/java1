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

    public boolean take(Philosopher philosopher) {
        if (!this.condition) {
            return false; //Вилка занята
        }
        synchronized (this) {
            this.condition = false;
            this.philosopher = philosopher; //Филосов взял вилку
        }
        return true;
    }

    public void put(Philosopher philosopher) {
        if (philosopher.equals(this.philosopher))
            this.condition = true; //Филосов положил вилку. Вилка свободна
    }

    @Override
    public String toString() {
        return "Fork{" +
                "forkName='" + forkName + '\'' +
                '}';
    }
}