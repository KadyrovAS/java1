package ru.progwards.java2.lessons.patterns;

import java.util.*;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArrayList;

public class Profiler {
    private static Profiler instance;

    class StatisticInfo{
        String sectionName; // имя секции
        int fullTime; // полное время выполнения секции в миллисекундах.
        int selfTime; //чистое время выполнения секции в миллисекундах.
        int count; // количество вызовов.
        StatisticInfo(String sectionName) {
            this.sectionName = sectionName;
        }
        @Override
        public String toString() {
            return "Секция: " + sectionName + "; " +
                    "Полное время: " + ((int) (fullTime / 1000) * 1000) + "; " +
                    "Чистое время: " + ((int) (selfTime / 1000) * 1000) + "; " +
                    "Количество вызовов: " + count;
        }
    }

    enum TypeEvent {ENTER, EXIT}

    CopyOnWriteArrayList<LocalEvent> eventsLog = new CopyOnWriteArrayList(); //журнал событий
    ConcurrentSkipListSet<String> threadsNames = new ConcurrentSkipListSet(); //список потоков

    class LocalEvent {
        String threadName; //поток, инициировавший событие
        String sectionName; // имя секции
        TypeEvent typeEvent; //вход в секцию или выход из секции
        long timeEvent; //время события

        LocalEvent(String sectionName, TypeEvent typeEvent){
            this.threadName = Thread.currentThread().getName();
            this.sectionName = sectionName;
            this.typeEvent = typeEvent;
            this.timeEvent = System.currentTimeMillis();
            threadsNames.add(this.threadName);
        }

        @Override
        public String toString() {
            return "Поток: " + threadName + "; " +
                    "Секция: " + sectionName + "; " +
                    "Событие: " + typeEvent + "; " +
                    "Время в мс: " + timeEvent;
        }
    }

    public static Profiler getInstance() {
        if (instance == null) {
            synchronized (Profiler.class) {
                if (instance == null)
                    instance = new Profiler();
            }
        }
        return instance;
    }

    public void enterSection(String name) {
        //вход в секцию
        eventsLog.add(new LocalEvent(name, TypeEvent.ENTER));
    }

    public void exitSection(String name) {
        //выход из секции
        eventsLog.add(new LocalEvent(name, TypeEvent.EXIT));
    }

    public List<StatisticInfo> getStatisticInfo() {
        Map<String, StatisticInfo> logResult = new HashMap<>();
        Stack<LocalEvent> stack = new Stack<>();
        LocalEvent stackEvent;
        StatisticInfo statisticInfo;
        for (String thread: threadsNames)
            for (LocalEvent forEvent: eventsLog)
                if (thread.compareTo(forEvent.threadName) == 0) {
                    if (forEvent.typeEvent == TypeEvent.ENTER) {
                        //был совершен вход в секцию
                        if (!stack.empty()) {
                            //совершен вход во вложенную секцию
                            stackEvent = stack.peek();
                            statisticInfo = logResult.get(stackEvent.sectionName);
                            statisticInfo.selfTime += forEvent.timeEvent;
                            logResult.put(stackEvent.sectionName, statisticInfo);
                        }
                        stack.push(forEvent);
                        if (logResult.get(forEvent.sectionName) == null)
                            logResult.put(forEvent.sectionName, new StatisticInfo(forEvent.sectionName));
                        statisticInfo = logResult.get(forEvent.sectionName);
                        statisticInfo.fullTime -= forEvent.timeEvent;
                        statisticInfo.selfTime -= forEvent.timeEvent;
                        statisticInfo.count += 1;
                        logResult.put(forEvent.sectionName, statisticInfo);
                    }
                    else {
                        //был совершен выход из секции
                        stackEvent = stack.pop();
                        statisticInfo = logResult.get(forEvent.sectionName);
                        statisticInfo.fullTime += forEvent.timeEvent;
                        statisticInfo.selfTime += forEvent.timeEvent;
                        logResult.put(forEvent.sectionName, statisticInfo);

                        if (stack.size() > 0) {
                            //секция была вложенной
                            stackEvent = stack.peek();
                            statisticInfo = logResult.get(stackEvent.sectionName);
                            statisticInfo.selfTime -= forEvent.timeEvent;
                            logResult.put(stackEvent.sectionName, statisticInfo);
                        }
                    }
                }

        return new ArrayList<StatisticInfo>(logResult.values());
    }

    public static void main(String[] args) throws InterruptedException {
        Profiler profiler = Profiler.getInstance();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i ++){
            threads[i] = new Thread(new ThreadProfiler());
            threads[i].start();
        }
        for (int i = 0; i < 10; i ++)
            threads[i].join();

        profiler.getStatisticInfo().forEach(System.out::println);
    }
}