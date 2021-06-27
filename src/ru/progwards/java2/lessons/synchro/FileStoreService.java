package ru.progwards.java2.lessons.synchro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReentrantLock;

public enum FileStoreService implements StoreService{
    INSTANCE;

    public final String FILE_DATA_BASE_PATH = "d:/Java/account.csv";
    public final Path path;
    private List<Account> collection = new CopyOnWriteArrayList<>();
    public ConcurrentHashMap<String, ReentrantLock> mapLocks;

    FileStoreService() {
        this.path = Paths.get(FILE_DATA_BASE_PATH);
        this.mapLocks = new ConcurrentHashMap<>();
        try {
            if (!Files.exists(path))
                Files.writeString(path, "");
            Files.lines(path).forEach(line -> collection.add(parseAccount(line)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        collection.forEach(account -> mapLocks.put(account.getId(), new ReentrantLock()));
    }

    @Override
    public Account get(String id) {
        for (Account account : collection)
            if (account != null && account.getId().compareTo(id) == 0)
                return account;

        return null;
    }

    @Override
    public Collection<Account> get() {
        return collection;
    }

    @Override
    public void delete(String id) throws InvalidPointerException {
        for (int i = 0; i < collection.size(); i ++)
            if (collection.get(i).getId().compareTo(id) == 0) {
                collection.remove(i);
                rewrite();
                return;
            }

        throw new InvalidPointerException("Аккаунт с id = " + id + " в базе данных отсутствует!");
    }

    @Override
    public void insert(Account account) {
        for (Account value : collection)
            if (value.getId().compareTo(account.getId()) == 0) {
                update(account);
                return;
            }
        collection.add(account);
        mapLocks.put(account.getId(), new ReentrantLock());
        rewrite();
    }

    @Override
    public void update(Account account)  {
        for (int i = 0; i < collection.size(); i ++) {
            if (collection.get(i).getId().compareTo(account.getId()) == 0){
                collection.set(i, account);
                return;
            }
        }
        try {
            throw new InvalidPointerException("Аккаунт с id = " + account.getId() + " в базе данных отсутствует!");
        } catch (InvalidPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    public synchronized void rewrite()  {
        //Перезаписываем всю коллекцию в файл
        try {
            Files.writeString(path, "");
            collection.forEach(value -> {
                try {
                    Files.writeString(path, value.toString(), StandardOpenOption.APPEND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ReentrantLock getLock(Account account){
        //возвращает lock из коллекции для account
       return mapLocks.get(account.getId());
    }

    private Account parseAccount(String accountString) {
        String[] line = accountString.split(",");
        line[0] = line[0].substring(3).replaceAll("'", "");
        line[1] = line[1].substring(8).replaceAll("'", "");
        line[2] = line[2].substring(6);
        line[3] = line[3].substring(8);
        line[4] = line[4].substring(5);

        Account account = new Account();
        account.setId(line[0]);
        account.setHolder(line[1]);
        try {
            account.setDate(account.simpleDateFormat.parse(line[2]));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        account.setAmount(Double.valueOf(line[3]));
        account.setPin(Integer.valueOf(line[4]));
        return account;
    }

}
