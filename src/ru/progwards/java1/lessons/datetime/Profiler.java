package ru.progwards.java1.lessons.datetime;

import java.util.*;

public class Profiler {

    static class CurrentLevel {
        String nameSection; //имя секции
        long entryTime; //время вызова секции
        long exitTime; //время выхода из секции

        CurrentLevel(String nameSection, long entryTime, long exitTime) {
            this.nameSection = nameSection; //имя секции
            this.entryTime = entryTime; //время входа в секцию
            this.exitTime = exitTime; //время выхода из секции
        }
    }

    static ArrayList<CurrentLevel> arrayInfo = new ArrayList<>(); // содержит журнал событий (вход-выход)
    static List<StatisticInfo> statisticInfo = new ArrayList<>(); //оработанная информация
    static LinkedList<CurrentLevel> listLevel = new LinkedList<>(); //стэк

    public static void enterSection(String name) { //вход в секцию
        arrayInfo.add(new CurrentLevel(name, System.currentTimeMillis(), 0));
    }

    public static void exitSection(String name) { //выход из секции
        arrayInfo.add(new CurrentLevel(name, 0, System.currentTimeMillis()));
    }

    public static List<StatisticInfo> getStatisticInfo() {
        int nFound = 0;
        long durationTime;
        boolean hasFound;
        statisticInfo.clear();
        listLevel.clear();

        for (int i = 0; i < arrayInfo.size(); i ++) {
            nFound = 0;
            hasFound = false;
            if (arrayInfo.get(i).entryTime != 0) {  //вход в секцию
                listLevel.add(new CurrentLevel(arrayInfo.get(i).nameSection, arrayInfo.get(i).entryTime, 0));
                if (statisticInfo.size() > 0) //осуществляет поиск секции в statisticInfo
                    for (int k = 0; k < statisticInfo.size(); k++)
                        if (statisticInfo.get(k).sectionName.compareTo(arrayInfo.get(i).nameSection) == 0) {
                            nFound = k;
                            hasFound = true;
                        }
                if (!hasFound) {
                    statisticInfo.add(new StatisticInfo());
                    nFound = statisticInfo.size() - 1;
                    statisticInfo.get(nFound).sectionName = arrayInfo.get(i).nameSection;
                }
                statisticInfo.get(nFound).count ++;
            } else { //выход из секции
                for (int k = 0; k < statisticInfo.size(); k++)
                    if (arrayInfo.get(i).nameSection.compareTo(statisticInfo.get(k).sectionName) == 0)
                        nFound = k; //индекс в statisticInfo

                if (arrayInfo.get(i).nameSection.compareTo(listLevel.getLast().nameSection) != 0)
                    return null; //нарушена последовательность входа-выхода из секций

                durationTime = arrayInfo.get(i).exitTime - listLevel.getLast().entryTime;

                statisticInfo.get(nFound).fullTime += durationTime;

                listLevel.removeLast();
                if (listLevel.size() > 0)
                    for (int k = 0; k < statisticInfo.size(); k++)
                        if (listLevel.getLast().nameSection.compareTo(statisticInfo.get(k).sectionName) == 0)
                            statisticInfo.get(k).selfTime -= durationTime;
            }
        }

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

    public static void main(String[] args){
        for (int n = 0; n < 2; n++){
            Profiler.enterSection("Process1");
            try { Thread.sleep(100); } catch (InterruptedException e) {}
            Profiler.exitSection("Process1");
        }

        Profiler.enterSection("Process1");
        try { Thread.sleep(100); } catch (InterruptedException e) {}

        for (int n = 0; n < 3; n++){
            Profiler.enterSection("Process2");
            try { Thread.sleep(100); } catch (InterruptedException e) {}

            Profiler.enterSection("Process3");
            try { Thread.sleep(100); } catch (InterruptedException e) {}
            Profiler.exitSection("Process3");

            Profiler.exitSection("Process2");
        }

        Profiler.exitSection("Process1");
//        getStatisticInfo();

        printInfo();
//        printInfo();
//        printInfo();
    }

    private static void printInfo(){
        for (StatisticInfo si : Profiler.getStatisticInfo()){
            System.out.println(si.sectionName + " " + (si.fullTime  / 100 * 100) + " " + (si.selfTime  / 100 * 100) + " " + si.count);
        }
    }
}