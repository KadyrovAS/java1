package ru.progwards.java2.lessons.synchro;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;

class ThreadTest extends Thread {

    @Override
    public void run() {
        StoreService dataBase = new FactoryDataBase().createDataBase("file");
        ConcurrentAccountService accountService = new ConcurrentAccountService();

        Collection<Account> collection = null;
        try {
            collection = dataBase.get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + " collection.size = " + collection.size());
        for (int i = 0; i < 10; i++)
            for (Account account : collection) {
                try {
                    accountService.deposit(account, 10);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (InvalidPointerException e) {
                    e.printStackTrace();
                }
            }
    }
}

public class AccountServeceTest {
    public static void main(String[] args) {

        Thread[] myThread = new Thread[10];
        for (int i = 0; i < 10; i++) {
            myThread[i] = new ThreadTest();
            myThread[i].start();
        }

    }
}
