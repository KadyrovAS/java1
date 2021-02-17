package ru.progwards.java2.lessons.synchro;

import java.io.IOException;
import java.util.Collection;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class ThreadTest extends Thread {
    ConcurrentAccountService Accountservice;
    FileStoreService service;
    ThreadTest(FileStoreService service, ReadWriteLock lock){
        this.service = service;
        this.Accountservice = new ConcurrentAccountService(service, lock);
    }
    @Override
    public void run() {
        Collection<Account> collection = service.get();

        for (int i = 0; i < 10; i ++)
        for (Account value: collection) {

//            if (currentThread().getName().endsWith("0"))

        }

    }
}

public class AccountServeceTest{
    public static void main(String[] args) throws IOException {
        FileStoreService service = new FileStoreService("c:/work/own/java/account.csv");
        ReadWriteLock lock = new ReentrantReadWriteLock();
        Thread[] myThread = new Thread[10];
        for (int i = 0; i < 10; i ++) {
            myThread[i] = new ThreadTest(service, lock);
            myThread[i].start();
        }
    }
}
