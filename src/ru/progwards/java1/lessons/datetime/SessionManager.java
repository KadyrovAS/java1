package ru.progwards.java1.lessons.datetime;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;

public class SessionManager {
    private HashMap<String, UserSession> sessions = new HashMap<>(); //Коллекция
    private int sessionValid;

    public SessionManager(int sessionValid) { //Конструктор  создает экземпляр SessionManager и
        // сохраняет sessionValid - период валидности сессии в секундах.
        this.sessionValid = sessionValid;
        sessions.clear();
    }

    public void add(UserSession userSession) { //добавляет новую сессию пользователя
        if (sessions.get(userSession.getUserName()) != null)
            sessions.put(userSession.getUserName(), userSession);
    }

    public boolean valid(UserSession userSession) {
        //Если срок годности истек, возвращает false
        long sessionDuration = Duration.between(userSession.getLastAccess(), ZonedDateTime.now()).toSeconds();
        if (this.sessionValid > sessionDuration) return true;
        return false;
    }

    public UserSession find(String userName) { //проверяет наличие существующей сессии по userName.
        if (sessions.get(userName) != null)
            if (valid(sessions.get(userName)) == true)
                return sessions.get(userName);
        return null;
    }

    public UserSession get(int sessionHandle) { //проверяет наличие существующей сессии по хендлу.
        for (UserSession currentSession : sessions.values())
            if (currentSession.getSessionHandle() == sessionHandle)
                if (valid(currentSession) == true)
                    return sessions.get(currentSession.getUserName());
        return null;
    }

    public void delete(int sessionHandle) { //удаляет указанную сессию пользователя
        for (UserSession currentSession : sessions.values())
            if (currentSession.getSessionHandle() == sessionHandle)
                sessions.remove(currentSession.getUserName());
    }

    public void deleteExpired() { //удаляет все сессии с истекшим сроком годности.
        for (UserSession currentSession : sessions.values())
            if (valid(currentSession) == false)
                sessions.remove(currentSession.getUserName());
    }
}
