package ru.progwards.java2.lessons.http;

import ru.progwards.java2.lessons.synchro.Account;
import ru.progwards.java2.lessons.synchro.FactoryDataBase;

import java.io.IOException;
import java.util.Collection;

public class AtmClientStart implements Runnable{
    @Override
    public void run() {
        Collection<Account> collection = null;
        try {
            collection = new FactoryDataBase().createDataBase("file").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        AtmClient localClient = new AtmClient();

        //всем клиентам кладем на счет по 100 баксов
        for (Account account: collection) {
            localClient.connectToServer(localClient.deposit(account, 100));
        }
    }
}
