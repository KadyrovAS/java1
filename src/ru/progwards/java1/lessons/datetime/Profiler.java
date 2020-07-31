package ru.progwards.java1.lessons.datetime;

import java.util.*;

public class Profiler {

    static class CurrentLevel {
        String nameSection; //имя секции
        long entryTime; //время вызова секции

        CurrentLevel(String nameSection, long entryTime) {
            this.nameSection = nameSection;
            this.entryTime = entryTime;
        }
    }

    static HashMap<String, StatisticInfo> mapStatistic = new HashMap<>();
    static LinkedList<CurrentLevel> listCurrentInfo = new LinkedList<>();

    public static void enterSection(String name) {
        long currentTime = System.currentTimeMillis();
        StatisticInfo currentInfo = new StatisticInfo();
        if (mapStatistic.get(name) == null) currentInfo.sectionName = name;
        else currentInfo = mapStatistic.get(name);
        currentInfo.count++;
        System.out.println("enterSection " + currentInfo.sectionName + " " + currentInfo.count + " " +
                           currentInfo.fullTime + " " + currentInfo.selfTime);
        listCurrentInfo.add(new CurrentLevel(name, currentTime));
        mapStatistic.put(name, currentInfo);
    }

    public static void exitSection(String name) {
        long timeExit = System.currentTimeMillis();
        Long durationTime;

        StatisticInfo currentInfo = mapStatistic.get(name);
        durationTime = timeExit - listCurrentInfo.getLast().entryTime;

        System.out.println("exitSection " + currentInfo.sectionName + " " + currentInfo.count + " " +
                currentInfo.fullTime + " " + currentInfo.selfTime);

        currentInfo.fullTime += durationTime;

        System.out.println();

        mapStatistic.put(name, currentInfo);
        listCurrentInfo.removeLast();
        if (listCurrentInfo.size() > 0) { //корректировка selfTime на внешнем уровне
            currentInfo = mapStatistic.get(listCurrentInfo.getLast().nameSection);
            currentInfo.selfTime -= durationTime;
            mapStatistic.put(currentInfo.sectionName, currentInfo);

            System.out.println("after remove: " + currentInfo.sectionName + " " + currentInfo.count + " " +
                    currentInfo.fullTime + " " + currentInfo.selfTime);

        }
    }

    public static List<StatisticInfo> getStatisticInfo() {
        List<StatisticInfo> statisticInfo = new ArrayList<>();
        statisticInfo.addAll(mapStatistic.values());
        for (int i = 0; i < statisticInfo.size(); i ++)
            statisticInfo.get(i).selfTime += statisticInfo.get(i).fullTime;
        statisticInfo.sort(new Comparator<StatisticInfo>() {
            @Override
            public int compare(StatisticInfo o1, StatisticInfo o2) {
                return o1.sectionName.compareTo(o2.sectionName);
            }
        });
        return statisticInfo;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 2; i++) {
            enterSection("Process1");
            Thread.sleep(100);
            exitSection("Process1");
        }
        enterSection("Process1");
        Thread.sleep(100);
        for (int i = 0; i < 4; i ++) {
            enterSection("Process2");
            Thread.sleep(200);
            enterSection("Process3");
            Thread.sleep(100);
            exitSection("Process3");
            exitSection("Process2");
        }
        exitSection("Process1");

        for (StatisticInfo item : getStatisticInfo())
            System.out.println(item.sectionName + " " +
                    item.fullTime / 100 * 100 + " " + item.selfTime / 100 * 100 + " " + item.count);
    }
}