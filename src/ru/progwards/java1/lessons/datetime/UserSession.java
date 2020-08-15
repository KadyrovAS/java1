package ru.progwards.java1.lessons.datetime;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Random;

public class UserSession {
    private int sessionHandle;
    private String userName;
    private LocalDateTime lastAccess;
    public  int getSessionHandle() {return this.sessionHandle;}
    public  String getUserName() {return this.userName;}
    public LocalDateTime getLastAccess() {return this.lastAccess;}
    public void updateLastAccess() {this.lastAccess = LocalDateTime.now();}
    public UserSession(String userName) {
        Random random = new Random();
        this.userName = userName;
        lastAccess = LocalDateTime.now();
        this.sessionHandle = random.nextInt();
    }

}
