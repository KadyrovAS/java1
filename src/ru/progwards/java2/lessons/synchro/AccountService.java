package ru.progwards.java2.lessons.synchro;

import java.io.IOException;
import java.text.ParseException;

public interface AccountService {

    public Account get(String id) throws IOException, ParseException;
    public double balance(Account account) throws IOException, ParseException;
    public void deposit(Account account, double amount) throws IOException, ParseException, InvalidPointerException;
    public void withdraw(Account account, double amount) throws IOException, ParseException, InvalidPointerException;
    public void transfer(Account from, Account to, double amount) throws IOException, ParseException, InvalidPointerException;

}