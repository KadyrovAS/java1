package ru.progwards.java2.lessons.patterns;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

//записывает в базу данных все значения GPS и speed при условии, что speed не выходит за границы 3 сигм
public class CalculateSpeedProxy implements CalcSpeed {
    CalculateSpeed calculateSpeed = new CalculateSpeed();
    List<Double>listSpeed = new ArrayList<>();

    @Override
    public Double getSpeed(GPS gps) {
        double speed = calculateSpeed.getSpeed(gps); // запрашиваем рассчет скорости у объекта оригинала
        listSpeed.add(speed);
        double s = listSpeed.stream().reduce(0D, (val1, val2)->val1 + val2); //сумма всех скоростей
        double m = 0; //математическое ожидание
        if (listSpeed.size() > 0)
            m = s / listSpeed.size();
        Double finalM = m;
        //сумма разностей квадратов для рассчета дисперсии
        Double sq = listSpeed.stream().map(val->Math.pow(finalM - val, 2)).reduce((val1, val2)->val1+val2).get();
        double d = 0;
        if (listSpeed.size() > 0)
            d = Math.sqrt(sq) / listSpeed.size(); //дисперсия
        double sko = Math.sqrt(d); //сигма
        if (speed <= m + 3 * sko && speed >= m - 3 * sko || listSpeed.size() <= 50)
            return speed;
        else {
            //отбракованную координату удаляем из списка
            listSpeed.remove(listSpeed.size() - 1);
            return null;
        }
    }

    @Override
    public String printSpeed(GPS gps) {
        DecimalFormat df = new DecimalFormat("#.##");
        Double speed = getSpeed(gps);
        if (speed == null)
            return "Координата " + gps + " отбракована";
        else
            return "Координата " + gps +
                    " Скорость объекта: " + df.format(speed) + " м/с";
    }

}