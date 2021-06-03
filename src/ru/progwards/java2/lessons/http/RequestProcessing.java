package ru.progwards.java2.lessons.http;
import ru.progwards.java2.lessons.synchro.Account;
import ru.progwards.java2.lessons.synchro.ConcurrentAccountService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class RequestProcessing implements Runnable {
    Socket server;
    ConcurrentAccountService service;

    public RequestProcessing(Socket server) {
        this.server = server;
    }

    @Override
    public void run() {
        this.service = new ConcurrentAccountService();
        String responseLine;
        List<String> listLine = new ArrayList<>();
        try (
                InputStream is = server.getInputStream();
                OutputStream os = server.getOutputStream();
        ) {
            Scanner sc = new Scanner(is);
            while (sc.hasNextLine()) {
                listLine.add(sc.nextLine());
                if (listLine.get(listLine.size() - 1).compareTo("") == 0)
                    break;
            }


            //Авторизация клиента
            //Проверяется наличие id в базе и соответствие pin
            //Также проверяется структура запроса

        RequestLines requestLines = new RequestLines(listLine);
        if (requestLines.getCode() != 0) {
            responseLine = "The request structure is not correct";
            fResponse(os, requestLines.getCode(), responseLine);
            return;
        }

        String id = requestLines.getCommands().get("id");
        Account localAccont = this.service.get(id);
        if (localAccont == null) {
            responseLine = "The client with the id = " + id + " is not in the database";
            fResponse(os, 401, responseLine);
            return;
        }

        String pin = requestLines.getCommands().get("pin");
        int pinInt = Integer.valueOf(pin);
        if (localAccont.getPin() != pinInt){
            responseLine = "Error entering pin";
            fResponse(os, 401, responseLine);
            return;
        }

            //Методы:
            //GetBalance - Получения информации по балансу
            //Transfer - Перевод на другую карту
            //Deposit - Пополнение счета
            //Withdraw - Снятие наличных
            //Параметры: param1 = id
            //           param2 = pin
            //           param3 = amount
            //           param4 = id2 (При выполнении transfer)


            if (requestLines.getCommand().compareTo("GetBalance") == 0){
                //Получение баланса
                responseLine = "Your balance is " + localAccont.getAmount() + "  rubles";
                fResponse(os, 200, responseLine);
            }
            else if (requestLines.getCommand().compareTo("Transfer") == 0){
                //Перевод на другую карту
                String id2 = requestLines.getCommands().get("id2");
                if (id2 == null) {
                    responseLine ="The client with the id = " + id2 + " is not in the database";
                    fResponse(os, 404, responseLine);
                }
                String amountStr = requestLines.getCommands().get("amount");
                if (amountStr == null){
                    responseLine = "The transfer amount is not specified";
                    fResponse(os, 400, responseLine);
                    return;
                }
                Account toAccount = service.get(id2);
                if (toAccount == null) {
                    responseLine = "The transfer amount is not specified";
                    fResponse(os, 400, responseLine);
                    return;
                }
                double amountDbl = Double.valueOf(amountStr);
                service.transfer(localAccont, toAccount, amountDbl);
                responseLine = "transfer in the amount of "+ amountStr + " rubles id " +
                        id2 + " completed";
                fResponse(os, 200, responseLine);
            }
            else if (requestLines.getCommand().compareTo("Deposit") == 0){
                //Пополнение счета
                String amountStr = requestLines.getCommands().get("amount");
                if (amountStr == null){
                    responseLine = "The transfer amount is not specified";
                    fResponse(os, 400, responseLine);
                    return;
                }
                double amountDbl = Double.valueOf(amountStr);
                service.deposit(localAccont, amountDbl);
                responseLine = "Your account has been replenished by " + amountStr + " rubles";
                fResponse(os, 200, responseLine);
            }
            else if (requestLines.getCommand().compareTo("Withdraw") == 0){
                //Снятие наличных
                String amountStr = requestLines.getCommands().get("amount");
                if (amountStr == null){
                    responseLine = "The transfer amount is not specified";
                    fResponse(os, 400, responseLine);
                    return;
                }
                double amountDbl = Double.valueOf(amountStr);
                service.withdraw(localAccont, amountDbl);
                responseLine = "Account balance reduced by " + amountStr + " rubles";
                fResponse(os, 200, responseLine);
            }
            else {
                responseLine = "The request structure is not correct";
                fResponse(os, 400, responseLine);
                return;
            }

        } catch (IOException e) {

        }

    }

    private void fResponse(OutputStream os, int code, String line){
        //Отправляет ответ клиенту
        String lineResponse = "HTTP/1.1 " + code +
                (code == 200 ? " OK" : " Bad Request") + "\n" +
                "Content-Type: text/html; charset=utf-8\n" +
                "Content-Length: " + line.length() +"\n\n" + line;
        PrintWriter pr = new PrintWriter(os);
        pr.println(lineResponse);
        pr.flush();
    }
}
