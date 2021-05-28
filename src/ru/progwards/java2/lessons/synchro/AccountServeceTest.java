package ru.progwards.java2.lessons.synchro;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;

class ThreadTest extends Thread {

    @Override
    public void run() {
        //Вариант с фабрикой
//        StoreService dataBase = new FactoryDataBase().createDataBase("file");
        StoreService dataBase = FileStoreService.INSTANCE;

        ConcurrentAccountService accountService = new ConcurrentAccountService();

        Collection<Account> collection = null;
        try {
            collection = dataBase.get();
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        Thread[] myThread = new Thread[100];
        for (int i = 0; i < 100; i++) {
            myThread[i] = new ThreadTest();
            myThread[i].start();
        }

    }
}
