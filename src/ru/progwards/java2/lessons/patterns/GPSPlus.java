package ru.progwards.java2.lessons.patterns;
//используется для записи в базу данных
public class GPSPlus{
        public double lat; // широта
        public double lon; // долгота
        public long time; // время в мс
        public double speed; //скорость
        GPSPlus(GPS gps, double speed) {
            this.speed = speed;
            this.lat = gps.lat;
            this.lon = gps.lon;
            this.time = gps.time;
        }
        GPS getGPS(){
            return new GPS(this.lat, this.lon, this.time);
        }
}
