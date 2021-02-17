package ru.progwards.java2.lessons.patterns;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class PatternProxyTest {
    public static void main(String[] args) throws IOException, ParseException {
        Path path = Paths.get("d:/Own/Tracks/hintertux_real_1.csv");
        List<String> base = Files.readAllLines(path);

//        Объект оригинал CalculateSpeed имплементирует интерфейс CalcSpeed и возвращает значение скорости
//        независимо от ошибки
//        Объект CalculateSpeedProxy тоже имплементирует CalcSpeed, но учитывает возможную ошибку и
//        отбраковывает соответствующую координату
//        GPSCoordinates calculateSpeed = new CalculateSpeed();

        CalcSpeed calculateSpeed = new CalculateSpeedProxy();

        base.remove(0);
        for (String line : base) {
            String[] values = line.split(",");
            Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(values[0]);
            long time = date.getTime();
            double lat = Double.valueOf(values[1]);
            double lon = Double.valueOf(values[2]);
            double speed = Double.valueOf(values[3]);
            System.out.println(calculateSpeed.printSpeed(new GPS(lat,lon,time)));
        }
    }
}
