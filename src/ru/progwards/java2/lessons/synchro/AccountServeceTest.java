package ru.progwards.java2.lessons.synchro;
import java.util.Collection;

class ThreadTest extends Thread {
    ConcurrentAccountService accountService;

    @Override
    public void run() {
        this.accountService = new ConcurrentAccountService();
        Collection<Account> collection = FileStoreService.INSTANCE.get();

        for (int i = 0; i < 10; i ++)
        for (Account value: collection) {
            accountService.deposit(value, 500);
        }
    }
}

public class AccountServeceTest{
    public static void main(String[] args) {
        Thread[] myThread = new Thread[10];
        for (int i = 0; i < 1; i ++) {
            myThread[i] = new ThreadTest();
            myThread[i].start();
        }
    }
}
