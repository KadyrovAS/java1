package ru.progwards.java1.lessons.datetime;

import java.util.*;

public class Profiler{

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
    static boolean entry = false; //При открытии секции true, при закрытии секции - false
    //если происходит закрытие секции при entry==true - это значит, что у текущей секции
    //отсутствуют вложенные секции, поэтому durationTime добавляем еще и к selfTime текущей секции

    public static void enterSection(String name) {
        entry = true;
        StatisticInfo currentInfo = new StatisticInfo();
        if (mapStatistic.get(name) == null) currentInfo.sectionName = name;
        else currentInfo = mapStatistic.get(name);
        currentInfo.count ++;
        listCurrentInfo.add(new CurrentLevel(name, System.currentTimeMillis()));
        mapStatistic.put(name, currentInfo);
    }

    public static void exitSection(String name) {
        long timeExit = System.currentTimeMillis();
        long durationTime;
        String nameSection;

        StatisticInfo currentInfo = mapStatistic.get(name);;
        durationTime = timeExit - listCurrentInfo.getLast().entryTime;
        currentInfo.fullTime += durationTime;

        //Добавление durationTime к selfTime текущего элемента, если у него отсутствуют вложенные вызовы
        if (entry == true) currentInfo.selfTime += durationTime;

        mapStatistic.put(name, currentInfo);
        if (listCurrentInfo.size() > 1) {
            // Добавление durationTime к selfTime элементу на уровень выше
            nameSection = listCurrentInfo.get(listCurrentInfo.size() - 2).nameSection;
            currentInfo = mapStatistic.get(nameSection);
            currentInfo.selfTime += durationTime;
            mapStatistic.put(nameSection, currentInfo);
        }

        entry = false;
        listCurrentInfo.removeLast();
    }

    public static List<StatisticInfo> getStatisticInfo() {
        List<StatisticInfo> statisticInfo = new ArrayList<>();
        statisticInfo.addAll(mapStatistic.values());
        statisticInfo.sort(new Comparator<StatisticInfo>(){
            @Override
            public int compare(StatisticInfo o1, StatisticInfo o2) {
                return o1.sectionName.compareTo(o2.sectionName);
            }
        });
        return statisticInfo;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 2; i ++) {
            enterSection("Process1");
            Thread.sleep(1000);
            exitSection("Process1");
        }
        enterSection("Process1");
        Thread.sleep(1000);
        for (int i = 0; i < 4; i ++) {
            enterSection("Process2");
            Thread.sleep(2000);
            enterSection("Process3");
            Thread.sleep(3000);
            exitSection("Process3");
            exitSection("Process2");
        }
        exitSection("Process1");
        for (StatisticInfo item: getStatisticInfo()) System.out.println(item.sectionName + " " +
                item.fullTime / 100 * 100 + " " + item.selfTime / 100 * 100 + " " + item.count);
    }
}