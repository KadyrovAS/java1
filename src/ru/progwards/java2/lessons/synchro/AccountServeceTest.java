package ru.progwards.java2.lessons.synchro;

import java.io.IOException;
import java.util.Collection;

class ThreadTest extends Thread {
    StoreService dataBase;

    ThreadTest(StoreService dataBase){
        this.dataBase = dataBase;
    }

    @Override
    public void run() {
        //Вариант с фабрикой

        ConcurrentAccountService accountService = new ConcurrentAccountService(dataBase);

        Collection<Account> collection = null;
        try {
            collection = dataBase.get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < 10; i++)
            for (Account account : collection) {
                accountService.deposit(account, 10);
            }

        Account account0 = null;
        for (Account account: collection)
            account0 = account;

        for (int i = 0; i < 10; i++)
            for (Account account : collection) {
                accountService.transfer(account0, account, 10);
            }

    }
}

public class AccountServeceTest {
    public static void main(String[] args) {
        StoreService dataBase = new FactoryDataBase().createDataBase("file");

        Thread[] myThread = new Thread[100];
        for (int i = 0; i < 100; i++) {
            myThread[i] = new ThreadTest(dataBase);
            myThread[i].start();
        }

    }
}
