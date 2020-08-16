package ru.progwards.java1.lessons.datetime;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Random;

public class UserSession {
    private int sessionHandle;
    private String userName;
    private LocalDateTime lastAccess;
    public  int getSessionHandle() {
        System.out.println("getSessionHandle " + LocalDateTime.now()); //TODO
        return this.sessionHandle;
    }
    public  String getUserName() {
        System.out.println("getUserName " + LocalDateTime.now()); //TODO
        return this.userName;
    }
    public LocalDateTime getLastAccess() {
        System.out.println("getLastAccess " + LocalDateTime.now()); //TODO
        return this.lastAccess;
    }
    public void updateLastAccess() {
        this.lastAccess = LocalDateTime.now();
        System.out.println("Обновлено время " + LocalDateTime.now()); //TODO
    }
    public UserSession(String userName) {
        Random random = new Random();
        this.userName = userName;
        lastAccess = LocalDateTime.now();
        this.sessionHandle = random.nextInt();
    }

}
