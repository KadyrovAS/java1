package ru.progwards.java2.lessons.synchro;

import java.util.concurrent.ConcurrentLinkedQueue;

public class Fork {
    private boolean condition;
    String forkName;
    Philosopher philosopher;
    ConcurrentLinkedQueue<Philosopher> linkedQueue = new ConcurrentLinkedQueue<>();

    public Fork(String forkName){
        //Вилка свободна
        this.forkName = forkName;
        this.condition = true;
    }

    public boolean get(){
        return this.condition;
    }

    public synchronized boolean take(Philosopher philosopher){
        if (!this.condition && !linkedQueue.contains(philosopher)) {
            linkedQueue.add(philosopher);
            return false;
        }
        if (linkedQueue.isEmpty() || linkedQueue.peek().equals(philosopher)) {

            this.condition = false;
            this.philosopher = philosopher;
            linkedQueue.poll();
            return true;
        }
        return false;
    }

    public void put(Philosopher philosopher) {
        if (philosopher.equals(this.philosopher))
            this.condition = true;
    }

    @Override
    public String toString() {
        return "Fork{" +
                "forkName='" + forkName + '\'' +
                '}';
    }
}

