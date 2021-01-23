package ru.progwards.java2.lessons.synchro;

import java.io.IOException;

public interface AccountService {

    public double balance(Account account);
    public void deposit(Account account, double amount) throws IOException, InvalidPointerException;
    public void withdraw(Account account, double amount) throws IOException, InvalidPointerException;
    public void transfer(Account from, Account to, double amount) throws IOException, InvalidPointerException;

}