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

// Для #traceout
//        System.out.println("enterSection" + name + " " + currentInfo.count + " " + currentInfo.fullTime + " " +
//                           currentInfo.selfTime);

        listCurrentInfo.add(new CurrentLevel(name, currentTime));
        mapStatistic.put(name, currentInfo);
    }

    public static void exitSection(String name) {
        long timeExit = System.currentTimeMillis();
        Long durationTime;
        StatisticInfo currentInfo = mapStatistic.get(name);
        durationTime = timeExit - listCurrentInfo.getLast().entryTime;
        currentInfo.fullTime += durationTime;
        mapStatistic.put(name, currentInfo);

// Для #traceout
//        System.out.println("exitSection " + currentInfo.sectionName + " " + currentInfo.count + " " +
//                             currentInfo.fullTime + " " + currentInfo.selfTime);

        listCurrentInfo.removeLast();
        if (listCurrentInfo.size() > 0) { //корректировка selfTime на внешнем уровне
            currentInfo = mapStatistic.get(listCurrentInfo.getLast().nameSection);
            currentInfo.selfTime -= durationTime;
            mapStatistic.put(currentInfo.sectionName, currentInfo);
//Для #traceout
//            System.out.println("afterExit " + currentInfo.sectionName + " " + currentInfo.count + " " +
//                    currentInfo.fullTime + " " + currentInfo.selfTime);

        }
    }

    public static List<StatisticInfo> getStatisticInfo() {
        List<StatisticInfo> statisticInfo = new ArrayList<>();
        statisticInfo.addAll(mapStatistic.values());
        for (int i = 0; i < statisticInfo.size(); i ++) {
//            mapStatistic.remove(statisticInfo.get(i).sectionName);
            statisticInfo.get(i).selfTime += statisticInfo.get(i).fullTime;
        }
//        for (int i = 0; i < listCurrentInfo.size(); i ++) listCurrentInfo.removeLast();

        statisticInfo.sort(new Comparator<StatisticInfo>() {
            @Override
            public int compare(StatisticInfo o1, StatisticInfo o2) {
                return o1.sectionName.compareTo(o2.sectionName);
            }
        });

        return statisticInfo;
    }

    public static void main(String[] args){
        for (int n = 0; n < 2; n++){
            Profiler.enterSection("Process1");
            try { Thread.sleep(100); } catch (InterruptedException e) {}
            Profiler.exitSection("Process1");
        }
        printInfo();
        printInfo();
        printInfo();
    }

    private static void printInfo(){
        for (StatisticInfo si : Profiler.getStatisticInfo()){
            System.out.println(si.sectionName + " " + (si.fullTime  / 100 * 100) + " " + (si.selfTime  / 100 * 100) + " " + si.count);
        }
    }
}