package ru.progwards.java2.lessons.synchro;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConcurrentAccountService implements AccountService{
    ConcurrentHashMap<String, ReadWriteLock> locks;

    public ConcurrentAccountService() {
        //locks - коллекция locks, создаваемых для каждого аккаунта индивидуально
        if (this.locks == null)
            locks = new ConcurrentHashMap<>();
    }

    public Account get(String id){
        return FileStoreService.INSTANCE.get(id);
    }

    @Override
    public  double balance(Account account) {
        return FileStoreService.INSTANCE.get(account.getId()).getAmount();
    }

    @Override
    public void deposit(Account account, double amount) {
        ReadWriteLock lock = getLock(account);
        lock.writeLock().lock();
            Account accountFound = FileStoreService.INSTANCE.get(account.getId());
            double ammountFound = accountFound.getAmount() + amount;
            accountFound.setAmount(ammountFound);
            FileStoreService.INSTANCE.update(accountFound);
        lock.writeLock().unlock();
        locks.remove(locks.get(account.getId()));
        FileStoreService.INSTANCE.rewrite();
    }

    @Override
    public void withdraw(Account account, double amount)  {
        ReadWriteLock lock = getLock(account);
        lock.writeLock().lock();
            Account accountFound = FileStoreService.INSTANCE.get(account.getId());
            double ammountFound = accountFound.getAmount() - amount;
            accountFound.setAmount(ammountFound);
            FileStoreService.INSTANCE.update(accountFound);
        lock.writeLock().unlock();
        locks.remove(locks.get(account.getId()));
        FileStoreService.INSTANCE.rewrite();
    }

    @Override
    public void transfer(Account from, Account to, double amount)  {
        ReadWriteLock lockFrom = getLock(from);
        ReadWriteLock lockTo = getLock(to);
        lockFrom.writeLock().lock();
            Account accountFrom = FileStoreService.INSTANCE.get(from.getId());
            double ammountFrom = accountFrom.getAmount() - amount;
            accountFrom.setAmount(ammountFrom);
            FileStoreService.INSTANCE.update(accountFrom);
        lockFrom.writeLock().unlock();
        locks.remove(from.getId());
        lockTo.writeLock().lock();
            Account accountTo = FileStoreService.INSTANCE.get(to.getId());
            double ammountTo = accountTo.getAmount() + amount;
            accountTo.setAmount(ammountTo);
            FileStoreService.INSTANCE.update(accountTo);
        lockTo.writeLock().unlock();
        locks.remove(to.getId());
        FileStoreService.INSTANCE.rewrite();
    }

    private ReadWriteLock getLock(Account account){
        //возвращает lock из коллекции для account
        //если lock в коллекции отсутствует - создает новый
        ReadWriteLock lock = locks.get(account.getId());
        if (lock == null) {
            lock = new ReentrantReadWriteLock();
            locks.put(account.getId(), lock);
        }
        return lock;
    }

}
