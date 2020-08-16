package ru.progwards.java1.lessons.datetime;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.HashMap;

public class SessionManager {
    private HashMap<String, UserSession> sessions = new HashMap<>(); //Коллекция
    private int sessionValid;

    public SessionManager(int sessionValid) { //Конструктор  создает экземпляр SessionManager и
        // сохраняет sessionValid - период валидности сессии в секундах.
        this.sessionValid = sessionValid * 1000; //пересчитываем в милисекунды
    }

    public void add(UserSession userSession) { //добавляет новую сессию пользователя
        System.out.println("add " + LocalDateTime.now()); //TODO
        if (sessions.get(userSession.getUserName()) == null)
            sessions.put(userSession.getUserName(), userSession);
        userSession.updateLastAccess();
    }

    public boolean checkValid(LocalDateTime dateTime) {
        System.out.println("checkValid " + LocalDateTime.now()); //TODO
        //Если срок сессии истек, возвращает false
        long sessionDuration = Duration.between(dateTime, LocalDateTime.now()).toMillis();
        if (this.sessionValid >= sessionDuration) return true;
        return false;
    }

    public UserSession find(String userName) { //проверяет наличие существующей сессии по userName.
        System.out.println("find " + LocalDateTime.now()); //TODO
        if (sessions.get(userName) != null)
            if (checkValid(sessions.get(userName).getLastAccess()) == true) {
                sessions.get(userName).updateLastAccess();
                return sessions.get(userName);
            }
        return null;
    }

    public UserSession get(int sessionHandle) { //проверяет наличие существующей сессии по хендлу.
        System.out.println("вызвана get " + LocalDateTime.now()); //TODO
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
        LocalDateTime start = LocalDateTime.now();
        Thread.sleep(500);
        System.out.println(sm.get(sessionHandle));
        Thread.sleep(500);
        LocalDateTime finis = LocalDateTime.now();
        System.out.println(Duration.between(start, finis).toMillis());
        System.out.println(sm.get(sessionHandle));
    }
}
