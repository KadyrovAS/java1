package ru.progwards.java2.lessons.patterns;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
//записывает в базу данных все значения GPS и speed при условии, что speed не выходит за границы 3 сигм
public class MovingObjectSigma implements MoveObj{
    MovingObject movingObject = new MovingObject();

    @Override
    public void addСoordinates(double lat, double lon, long time) {
        GPS gps = new GPS(lat, lon, time);
        if (movingObject.dataBase.getSize() == 0) {
            movingObject.dataBase.addCoordinates(new GPSPlus(gps, 0));
            System.out.println();
            return;
        }
        GPS gps0 = movingObject.dataBase.getLast().getGPS();
        double speed = calculateSpeed(gps0, gps);

        if (movingObject.dataBase.getSize() < 50) {
            movingObject.dataBase.addCoordinates(new GPSPlus(gps, speed));
            System.out.println("скорость = " + speed);
        }
        else if (movingObject.dataBase.isPossible(speed)) {
            movingObject.dataBase.addCoordinates(new GPSPlus(gps, speed));
            System.out.println("скорость = " + speed);
        }
        else
            System.out.println("(скорость = " + speed + ")");
    }

    public static void main(String[] args) throws IOException, ParseException {
        //Выводит на консоль координаты gps и скорость объекта
        //Если скорость объекта в диапазон 3 сигм не попадает, то скорость выводится на консоль в скобках

        Path path = Paths.get("c:/data/tracks/hintertux_real_1.csv");
        List<String> base = Files.readAllLines(path);
        MovingObjectSigma movingObjectSigma = new MovingObjectSigma();
        base.remove(0);
        int count = 0;
        for (String line: base){
            count++;
            String[] values = line.split(",");
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(values[0]);
            long time = date.getTime();
            double lat = Double.valueOf(values[1]);
            double lon = Double.valueOf(values[2]);
            double speed = Double.valueOf(values[3]);
            System.out.print(count + ". Время: " + time + "; " +
                    " lat = " + lat + "; " +
                    " lon = " + lon + "; ");
            movingObjectSigma.addСoordinates(lat, lon,time);

        }
    }
}