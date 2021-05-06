package ru.progwards.java2.lessons.synchro;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class Store {

    private static Map<String, Account> store = new HashMap<>();

    static {
        for (int i = 0; i < 10 ; i++) {
            Account acc = new Account();
            String id = UUID.randomUUID().toString();
            acc.setId(id);
            acc.setPin(1000+i);
            acc.setHolder("Account_"+i);
            acc.setDate(new Date(System.currentTimeMillis()+365*24*3600*1000));
            acc.setAmount(Math.random()*1_000_000);

            store.put(acc.getId(), acc);
        }
    }

    public static Map<String, Account> getStore(){
        return store;
    }

    public static void main(String[] args) {
        getStore().forEach((key,value)->FileStoreService.INSTANCE.insert(value));
    }
}
