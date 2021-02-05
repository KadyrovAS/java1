package ru.progwards.java2.lessons.patterns;

import java.util.LinkedList;
//Имитирует базу данных. При записи координат и скорости объекта пересчитываются переменные,
//необходимые для рассчета дисперсии и сигма

public class DataBase{
    private LinkedList<GPSPlus> coordinates = new LinkedList<>();
    private double s = 0; //сумма для рассчета математического ожидания
    private double sd = 0; //для рассчета дисперсии
    private double sko = 0;
    private double m = 0; //математическое ожидание
    private double d; //дисперсия

    public void addCoordinates(GPSPlus gpsPlus) {
        if (coordinates.size() == 0){
            coordinates.push(gpsPlus);
            return;
        }

        s += gpsPlus.speed;
        m = s / coordinates.size();
        sd += Math.pow(m - gpsPlus.speed, 2);
        d = Math.sqrt(sd) / coordinates.size();
        sko = Math.sqrt(d);

        coordinates.push(gpsPlus);
    }

    public GPSPlus getLast() {
        return coordinates.peek();
    }

    public int getSize() {
        return coordinates.size();
    }

    public boolean isPossible(double speed) {
        //возвращает true, если speed находится в диапазоне 3 сигм
        System.out.print(" m = " + String.format("%.2f",m) + " sigma * 3 = " + String.format("%.2f", sko * 3) + " ");
        if (m - sko * 3 < speed && m + sko * 3 > speed)
            return true;
        else
            return false;
    }
}
