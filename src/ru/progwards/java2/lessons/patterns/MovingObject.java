package ru.progwards.java2.lessons.patterns;
//записывает в базу данных все значения GPS и speed, без учета 3 сигм
public class MovingObject implements MoveObj{
    DataBase dataBase = new DataBase();

    @Override
    public void addСoordinates(double lat, double lon, long time) {
        GPS gps = new GPS(lat, lon, time);
        if (dataBase.getSize() == 0)
            dataBase.addCoordinates(new GPSPlus(gps, 0));
        else {
            GPS gps0 = dataBase.getLast().getGPS();
            double speed = calculateSpeed(gps0, gps);
            dataBase.addCoordinates(new GPSPlus(gps, speed));
        }
    }

    public static void main(String[] args) {
        MovingObject movingObject = new MovingObject();
    }
}