package ru.progwards.java2.lessons.http;

import java.io.IOException;
import java.net.*;

public class BankServer {

    public void ServerLoad() {
        Socket server = null;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(80);
            serverSocket.setSoTimeout(5_000);
        } catch (SocketException e) {

        } catch (IOException e) {

        }
        while (true) {
            try {
                server = serverSocket.accept();
            } catch (SocketTimeoutException e) {
                break;
            } catch (IOException e) {

            }

            new Thread(new RequestProcessing(server)).start();
        }

    }

    public static void main(String[] args){
        new Thread( new AtmClientStart()).start();
        BankServer bankServer = new BankServer();
        bankServer.ServerLoad();
    }
}