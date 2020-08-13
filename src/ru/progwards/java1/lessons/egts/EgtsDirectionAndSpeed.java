package ru.progwards.java1.lessons.egts;

public class EgtsDirectionAndSpeed {
    public static int getSpeed(short value) {
        int speed = value & 0b01111111_11111111;
        return speed;
    }


    public static int getDirection(byte dirLow, short speedAndDir) {
        int intDirLow = dirLow & 0b11111111;
        if ((speedAndDir & 0b1000_0000) == 0b1) intDirLow += 512;
        return intDirLow;
    }

    public static void main(String[] args) {
        short sh = -5;
        byte bt = 10;
        System.out.println(getDirection(bt, sh));
    }
}
