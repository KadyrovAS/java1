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

    public synchronized boolean take(Philosopher philosopher) {
        if (!this.condition && (this.philosopher == null || !this.philosopher.equals(philosopher))) {
            return false; //Вилка занята или очередь должна перейти к другому философу
        }

        this.condition = false;
        this.philosopher = philosopher; //Филосов взял вилку
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