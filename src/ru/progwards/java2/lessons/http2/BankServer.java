package ru.progwards.java2.lessons.http2;

import java.io.IOException;
import java.net.*;

public class BankServer {

    public void ServerLoad() {
        Socket server = null;
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(80);
            serverSocket.setSoTimeout(15000);
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

    public static void main(String[] args) throws UnknownHostException {
        BankServer bankServer = new BankServer();
        bankServer.ServerLoad();
    }
}