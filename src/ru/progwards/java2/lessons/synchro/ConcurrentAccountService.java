package ru.progwards.java2.lessons.synchro;

import java.io.IOException;
import java.nio.file.Files;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrentAccountService implements AccountService{
    private FileStoreService service;
    ReadWriteLock lock;

    public ConcurrentAccountService(FileStoreService fileStoreService, ReadWriteLock lock) {
        this.service = fileStoreService;
        this.lock = lock;
    }

    @Override
    public synchronized double balance(Account account) {
        return service.get(account.getId()).getAmount();
    }

    @Override
    public void deposit(Account account, double amount) throws IOException, InvalidPointerException {
        lock.readLock().lock();
        Account accountFound = service.get(account.getId());
        double ammountFound = accountFound.getAmount() + amount;
        lock.readLock().unlock();

        lock.writeLock().lock();
        accountFound.setAmount(ammountFound);
        service.update(accountFound);
        lock.writeLock().unlock();
    }

    @Override
    public void withdraw(Account account, double amount) throws IOException, InvalidPointerException {
        lock.readLock().lock();
        Account accountFound = service.get(account.getId());
        double ammountFound = accountFound.getAmount() - amount;
        lock.readLock().unlock();

        lock.writeLock().lock();
        accountFound.setAmount(ammountFound);
        service.update(accountFound);
        lock.writeLock().unlock();
    }

    @Override
    public void transfer(Account from, Account to, double amount) throws IOException, InvalidPointerException {
        withdraw(from, amount);
        deposit(to, amount);
    }

    public static void main(String[] args) throws IOException, InvalidPointerException {
        FileStoreService service = new FileStoreService("c:/work/own/java/account.csv");
        Files.writeString(service.path, "");
        ReadWriteLock lock = new ReentrantReadWriteLock();
        ConcurrentAccountService accountService = new ConcurrentAccountService(service, lock);

        Store.getStore().forEach((key, account) -> {
            try {
                service.insert(account);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidPointerException e) {
                e.printStackTrace();
            }
        });

        Collection<Account> list = service.get();
        list.forEach(value -> System.out.println(value));

        Account account1 = new Account();
        Account account2 = new Account();
        for (Account value: list) {
            if (value.getHolder().compareTo("Account_1") == 0)
                account1 = value;

            if (value.getHolder().compareTo("Account_2") == 0)
                account2 = value;
        }

        accountService.transfer(account1, account2, 100000);

        System.out.println("-----------------------------------------------------");

        list = service.get();
        list.forEach(value -> System.out.println(value));
    }

}
