package ru.progwards.java2.lessons.patterns;

import java.util.List;

public interface MoveObj{
    public void addСoordinates(double lat, double lon, long time);
    public default double calculateSpeed(GPS gps1, GPS gps2) {
        //осуществляет рассчет скорости по gps координатам
        //радиус Земли
        final int R = 6372795;
        //перевод коордитат в радианы
        double lat1 = gps1.lat * Math.PI / 180;
        double lat2 = gps2.lat * Math.PI / 180;
        double long1 = gps1.lon * Math.PI / 180;
        double long2 = gps2.lon * Math.PI / 180;
        //вычисление косинусов и синусов широт и разницы долгот
        var cl1 = Math.cos(lat1);
        var cl2 = Math.cos(lat2);
        var sl1 = Math.sin(lat1);
        var sl2 = Math.sin(lat2);
        var delta = long2 - long1;
        var cdelta = Math.cos(delta);
        var sdelta = Math.sin(delta);
        //вычисления длины большого круга
        var y = Math.sqrt(Math.pow(cl2 * sdelta, 2) + Math.pow(cl1 * sl2 - sl1 * cl2 * cdelta, 2));
        var x = sl1 * sl2 + cl1 * cl2 * cdelta;
        var ad = Math.atan2(y, x);
        var dist = ad * R; //расстояние между двумя координатами в метрах
        double speed = dist / (gps2.time - gps1.time) * 1000; //скорость в м/с
        return speed;
    }
}
