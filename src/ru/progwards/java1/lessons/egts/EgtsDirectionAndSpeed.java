package ru.progwards.java1.lessons.egts;

public class EgtsDirectionAndSpeed {
    public static int getSpeed(short value) {
        int speed = value & 0b01111111_11111111;
        return speed;
    }


    public static int getDirection(byte dirLow, short speedAndDir) {
            int dir = dirLow & 0b11111111;
            if ((speedAndDir & 0b10000000_00000000) == 0b10000000_00000000)
                dir += 0b1_00000000;
            return dir;
    }


    public static void main(String[] args) {
        short sh = -5;
        byte bt = 123;
        System.out.println(getDirection(bt, sh));
    }
}
