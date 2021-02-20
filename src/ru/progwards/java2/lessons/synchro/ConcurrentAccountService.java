package ru.progwards.java2.lessons.synchro;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrentAccountService implements AccountService{
    private FileStoreService service;
    private ReadWriteLock lock;

    public ConcurrentAccountService(FileStoreService fileStoreService, ReadWriteLock lock) {
        this.service = fileStoreService;
        this.lock = lock;
    }

    @Override
    public synchronized double balance(Account account) {
        return service.get(account.getId()).getAmount();
    }

    @Override
    public void deposit(Account account, double amount) {
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
    public void withdraw(Account account, double amount)  {
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
    public void transfer(Account from, Account to, double amount)  {
        withdraw(from, amount);
        deposit(to, amount);
    }

    public static void main(String[] args) throws IOException {
        FileStoreService service = new FileStoreService("d:/java/account.csv");
        Files.writeString(service.path, "");
        ReadWriteLock lock = new ReentrantReadWriteLock();
        ConcurrentAccountService accountService = new ConcurrentAccountService(service, lock);

        Store.getStore().forEach((key, account) -> {
            service.insert(account);
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
