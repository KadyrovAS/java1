package ru.progwards.java1.lessons.datetime;

public class StatisticInfo{
    public String sectionName; // имя секции
    public int fullTime; // полное время выполнения секции в миллисекундах.
    public int selfTime; //чистое время выполнения секции в миллисекундах.
    public int count; // количество вызовов.
    StatisticInfo() {
        System.out.println("StatisticInfo() - обнуляем переменные");
        this.sectionName = "";
        this.fullTime = 0;
        this.selfTime = 0;
        this.count = 0;
    }
}