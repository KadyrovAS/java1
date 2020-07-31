package ru.progwards.java1.lessons.datetime;

import java.time.ZonedDateTime;
import java.util.Random;

public class UserSession {
    private int sessionHandle;
    private String userName;
    private ZonedDateTime lastAccess;
    public  int getSessionHandle() {return this.sessionHandle;}
    public  String getUserName() {return this.userName;}
    public ZonedDateTime getLastAccess() {return this.lastAccess;}
    public void updateLastAccess() {this.lastAccess = ZonedDateTime.now();}
    public UserSession(String userName) {
        Random random = new Random();
        this.userName = userName;
        lastAccess = ZonedDateTime.now();
        this.sessionHandle = random.nextInt();
//        System.out.println("UserSession userName=" + userName + "; lastAccess =" + lastAccess.toString());
    }

}
