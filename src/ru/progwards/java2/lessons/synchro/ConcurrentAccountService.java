package ru.progwards.java2.lessons.synchro;

import java.io.IOException;
import java.text.ParseException;
import java.util.concurrent.locks.ReadWriteLock;

public class ConcurrentAccountService implements AccountService{
    StoreService dataBase = new FactoryDataBase().createDataBase("file");

    @Override
    public Account get(String id) throws IOException, ParseException {
        return dataBase.get(id);
    }

    @Override
    public  double balance(Account account) throws IOException, ParseException {
        return dataBase.get(account.getId()).getAmount();
    }

    @Override
    public void deposit(Account account, double amount) throws IOException, ParseException, InvalidPointerException {
        ReadWriteLock lock = dataBase.getLock(account);
        lock.writeLock().lock();
            Account accountFound = dataBase.get(account.getId());
            if (accountFound == null) {
                //аккуунт не найден
                lock.writeLock().unlock();
                return;
            }
            double ammountFound = accountFound.getAmount() + amount;
            accountFound.setAmount(ammountFound);
            dataBase.update(accountFound);
        lock.writeLock().unlock();
        dataBase.rewrite();
    }

    @Override
    public void withdraw(Account account, double amount) throws IOException, ParseException, InvalidPointerException {
        ReadWriteLock lock = dataBase.getLock(account);
        lock.writeLock().lock();
            Account accountFound = dataBase.get(account.getId());
            if (accountFound == null) {
                //аккаунт не найден
                lock.writeLock().unlock();
                return;
            }

            double ammountFound = accountFound.getAmount() - amount;
            accountFound.setAmount(ammountFound);
            dataBase.update(accountFound);
        lock.writeLock().unlock();
        dataBase.rewrite();
    }

    @Override
    public void transfer(Account from, Account to, double amount) throws IOException, ParseException, InvalidPointerException {
        ReadWriteLock lockFrom = dataBase.getLock(from);
        ReadWriteLock lockTo = dataBase.getLock(to);
        lockFrom.writeLock().lock();
        lockTo.writeLock().lock();
            Account accountFrom = dataBase.get(from.getId());
            Account accountTo = dataBase.get(to.getId());
            if (accountFrom == null || accountTo == null) {
                //не наден один из аккаунтов
                lockFrom.writeLock().unlock();
                lockTo.writeLock().unlock();
                return;
            }
            double ammountFrom = accountFrom.getAmount() - amount;
            accountFrom.setAmount(ammountFrom);
            double ammountTo = accountTo.getAmount() + amount;
            accountTo.setAmount(ammountTo);
            dataBase.update(accountFrom);
            dataBase.update(accountTo);
        lockFrom.writeLock().unlock();
        lockTo.writeLock().unlock();
        dataBase.rewrite();
    }

}
