package ru.progwards.java1.lessons.datetime;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.HashMap;

public class SessionManager {
    private HashMap<String, UserSession> sessions = new HashMap<>(); //Коллекция
    private int sessionValid;

    public SessionManager(int sessionValid) { //Конструктор  создает экземпляр SessionManager и
        // сохраняет sessionValid - период валидности сессии в секундах.
        this.sessionValid = sessionValid;
    }

    public void add(UserSession userSession) { //добавляет новую сессию пользователя
        if (sessions.get(userSession.getUserName()) == null)
             sessions.put(userSession.getUserName(), userSession);
    }

    public boolean checkValid(ZonedDateTime dateTime) {
        //Если срок годности истек, возвращает false
        long sessionDuration = Duration.between(dateTime, ZonedDateTime.now()).toSeconds();
        if (this.sessionValid > sessionDuration) return true;
        return false;
    }

    public UserSession find(String userName) { //проверяет наличие существующей сессии по userName.
        if (sessions.get(userName) != null)
            if (checkValid(sessions.get(userName).getLastAccess()) == true)
                return sessions.get(userName);
        return null;
    }

    public UserSession get(int sessionHandle) { //проверяет наличие существующей сессии по хендлу.
        for (UserSession currentSession : sessions.values())
            if (currentSession.getSessionHandle() == sessionHandle)
                if (checkValid(currentSession.getLastAccess()) == true) {
                    currentSession.updateLastAccess();
                    return sessions.get(currentSession.getUserName());
                }
        return null;
    }

    public void delete(int sessionHandle) { //удаляет указанную сессию пользователя
        for (UserSession currentSession : sessions.values())
            if (currentSession.getSessionHandle() == sessionHandle)
                sessions.remove(currentSession.getUserName());
    }

    public void deleteExpired() { //удаляет все сессии с истекшим сроком годности.
        for (UserSession currentSession : sessions.values())
            if (checkValid(currentSession.getLastAccess()) == false)
                sessions.remove(currentSession.getUserName());
    }

    public static void main(String[] args) throws InterruptedException {
        int sessionHandle;
        UserSession us = new UserSession("User1");
        sessionHandle = us.getSessionHandle();
        SessionManager sm = new SessionManager(1);
        sm.add(us);
        Thread.sleep(500);
        System.out.println(sm.get(sessionHandle));
        Thread.sleep(1000);
        System.out.println(sm.get(sessionHandle));
    }
}
