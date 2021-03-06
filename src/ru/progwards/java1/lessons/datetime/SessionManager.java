package ru.progwards.java1.lessons.datetime;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SessionManager {
    private HashMap<Integer, UserSession> sessions = new HashMap<>(); //Коллекция
    private int sessionValid;

    public SessionManager(int sessionValid) { //Конструктор  создает экземпляр SessionManager и
        // сохраняет sessionValid - период валидности сессии в секундах.
        this.sessionValid = sessionValid * 1000; //пересчитываем в милисекунды
    }

    public void add(UserSession userSession) { //добавляет новую сессию пользователя
        if (sessions.get(userSession.getSessionHandle()) == null)
            sessions.put(userSession.getSessionHandle(), userSession);
        userSession.updateLastAccess();
    }

    public boolean checkValid(LocalDateTime dateTime) {
        //Если срок сессии истек, возвращает false
        long sessionDuration = Duration.between(dateTime, LocalDateTime.now()).toMillis();
        if (this.sessionValid >= sessionDuration) return true;
        return false;
    }

    public UserSession find(String userName) { //проверяет наличие существующей сессии по userName.
        deleteExpired();
        for (UserSession value: sessions.values())
            if (value.getUserName().compareTo(userName) == 0)
                return value;
        return null;
    }

    public UserSession get(int sessionHandle) { //проверяет наличие существующей сессии по хендлу.
        deleteExpired();
        if (sessions.get(sessionHandle) != null)
            sessions.get(sessionHandle).updateLastAccess();
        return sessions.get(sessionHandle);
    }

    public void delete(int sessionHandle) { //удаляет указанную сессию пользователя
        sessions.remove(sessionHandle);
    }

    public void deleteExpired() { //удаляет все сессии с истекшим сроком годности.
        List <Integer> keys = new ArrayList<Integer>();
        for (int currentKey : sessions.keySet())
            if (!checkValid(sessions.get(currentKey).getLastAccess()))
                keys.add(currentKey);
        for (int currentKey: keys)
            sessions.remove(currentKey);
    }

    public static void main(String[] args) throws InterruptedException {
        UserSession us = new UserSession("User1");
        us.getSessionHandle();
        SessionManager sm = new SessionManager(1);
        sm.add(us);
        LocalDateTime start = LocalDateTime.now();
        Thread.sleep(1000);
        System.out.println(sm.checkValid(us.getLastAccess()));
    }
}
