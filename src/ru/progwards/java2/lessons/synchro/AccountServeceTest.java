package ru.progwards.java2.lessons.synchro;

import java.util.Collection;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class ThreadTest extends Thread {
    ConcurrentAccountService accountService;
    FileStoreService service;
    ThreadTest(FileStoreService service, ReadWriteLock lock){
        this.service = service;
        this.accountService = new ConcurrentAccountService(service, lock);
    }
    @Override
    public void run() {
        Collection<Account> collection = service.get();

        for (int i = 0; i < 10; i ++)
        for (Account value: collection) {
            accountService.deposit(value, 500);
        }

    }
}

public class AccountServeceTest{
    public static void main(String[] args) {
        FileStoreService service = new FileStoreService("d:/java/account.csv");
        ReadWriteLock lock = new ReentrantReadWriteLock();
        Thread[] myThread = new Thread[10];
        for (int i = 0; i < 10; i ++) {
            myThread[i] = new ThreadTest(service, lock);
            myThread[i].start();
        }
    }
}
