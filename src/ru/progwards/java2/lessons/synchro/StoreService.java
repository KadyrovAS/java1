package ru.progwards.java2.lessons.synchro;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;

public interface StoreService {
    public Account get(String id) throws IOException, ParseException;
    public Collection<Account> get() throws IOException;
    public void delete(String id) throws IOException, InvalidPointerException;
    public void insert(Account account) throws IOException, InvalidPointerException;
    public void update(Account account) throws IOException, InvalidPointerException;
    public ReentrantLock getLock(Account account);
    public void rewrite();
}