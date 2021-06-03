package ru.progwards.java2.lessons.synchro;

import java.util.concurrent.locks.ReentrantLock;

public class ConcurrentAccountService implements AccountService{
//    StoreService dataBase = FileStoreService.INSTANCE;
    //Создание dataBase c помощью фабрики
    StoreService dataBase = new FactoryDataBase().createDataBase("file");

    @Override
    public Account get(String id) {
        return dataBase.get(id);
    }

    @Override
    public  double balance(Account account)  {
        return dataBase.get(account.getId()).getAmount();
    }

    @Override
    public void deposit(Account account, double amount)  {
        ReentrantLock lock = dataBase.getLock(account);
        lock.lock();
            Account accountFound = dataBase.get(account.getId());
            if (accountFound == null) {
                //аккуунт не найден
                lock.unlock();
                return;
            }
            double ammountFound = accountFound.getAmount() + amount;

            accountFound.setAmount(ammountFound);
            dataBase.update(accountFound);
        lock.unlock();
        dataBase.rewrite();
    }

    @Override
    public void withdraw(Account account, double amount) {
        ReentrantLock lock = dataBase.getLock(account);
        lock.lock();
            Account accountFound = dataBase.get(account.getId());
            if (accountFound == null) {
                //аккаунт не найден
                lock.unlock();
                return;
            }

            double ammountFound = accountFound.getAmount() - amount;
            accountFound.setAmount(ammountFound);
            dataBase.update(accountFound);
        lock.unlock();
        dataBase.rewrite();
    }

    @Override
    public void transfer(Account from, Account to, double amount) {
        ReentrantLock lockFrom = dataBase.getLock(from);
        ReentrantLock lockTo = dataBase.getLock(to);

        while (true) {
            if (lockFrom.tryLock() && lockTo.tryLock()) break;
            lockFrom.unlock();
            lockTo.unlock();
            try {
                wait();
            } catch (InterruptedException e) {

            }
        }
            Account accountFrom = dataBase.get(from.getId());
            Account accountTo = dataBase.get(to.getId());
            if (accountFrom == null || accountTo == null) {
                //не наден один из аккаунтов
                notifyAll();
                lockFrom.unlock();
                lockTo.unlock();
                return;
            }
        double ammountFrom = accountFrom.getAmount() - amount;
        accountFrom.setAmount(ammountFrom);
        double ammountTo = accountTo.getAmount() + amount;
        accountTo.setAmount(ammountTo);
        dataBase.update(accountFrom);
        dataBase.update(accountTo);
        lockFrom.unlock();
        lockTo.unlock();
        dataBase.rewrite();
    }

}
