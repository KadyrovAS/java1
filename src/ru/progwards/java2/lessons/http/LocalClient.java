package ru.progwards.java2.lessons.http;

import ru.progwards.java2.lessons.synchro.Account;
import ru.progwards.java2.lessons.synchro.FactoryDataBase;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

//Класс, который работает у клиента
public class LocalClient {

    private List<String> listLines = new ArrayList<>();

    public List<String> getListLines() {
        return listLines;
    }
    public void connectToServer(String request)  {
        //Обеспечивает связь с сервером и обмен сообщениями
        //Все принимаемые сообщения от сервера записываются в listLines
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName("192.168.1.70");
        } catch (UnknownHostException e) {
            try {
                inetAddress = InetAddress.getLocalHost();
            } catch (UnknownHostException er) {
                er.printStackTrace();
            }
        }

        try {
            Socket client = new Socket(inetAddress, 80);
            InputStream is = client.getInputStream();
            OutputStream os = client.getOutputStream();

            Scanner scanner = new Scanner(is);

            PrintWriter pw = new PrintWriter(os);
            pw.println(request);
            pw.flush();

            listLines.clear();
            while (scanner.hasNextLine()) {
                listLines.add(scanner.nextLine());
                if (listLines.get(listLines.size() - 1).compareTo("") == 0)
                    break;
            }
            listLines.add(scanner.nextLine());

            listLines.forEach(System.out::println);
            System.out.println();

            client.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String deposit(Account account, double amount){
        return  "GET/Deposit?id=" + account.getId() +
                "&pin=" + account.getPin() + "&amount=" + amount + " HTTP/1.1\n" +
                "hostname: localhost\n\n";
    }

    private static String balance(Account account) {
        return  "GET/GetBalance?id=" + account.getId() +
                "&pin=" + account.getPin() + " HTTP/1.1\n" +
                "hostname: localhost\n\n";
    }

    private static String withdraw(Account account, double amount) {
        return "GET/Withdraw?id=" + account.getId() +
                "&pin=" + account.getPin() + "&amount=" + amount + " HTTP/1.1\n" +
                "hostname: localhost\n\n";
    }

    private static String transfer(Account from, Account to, double amount) {
        return "GET/Transfer?id=" + from.getId() +
                "&pin=" + from.getPin() + "&amount=" + amount +
                "&id2=" + to.getId() + " HTTP/1.1\n" +
                "hostname: localhost\n\n";
    }

    public static void main(String[] args) throws IOException {
        Collection<Account> collection = new FactoryDataBase().createDataBase("file").get();
        LocalClient localClient = new LocalClient();

        //всем клиентам кладем на счет по 100 баксов
        for (Account account: collection) {
            localClient.connectToServer(deposit(account, 100));
            localClient.getListLines().forEach(System.out::println);
            System.out.println();
        }
    }
}
