package ru.progwards.java1.lessons.egts;

public class EgtsDirectionAndSpeed {
    public static int getSpeed(short value) {
        int speed = value & 0b01111111_11111111;
        return speed;
    }


    public static int getDirection(byte dirLow, short speedAndDir) {
        byte dir = dirLow;
        int res = 0;
        int mul = 1;
        for (int i = 0; i < 8; i ++) {
            if (i > 0) mul *= 2;
            if ((dir & 0b1) == 0b1) res += mul;
            dir >>= 1;
        }

        if ((speedAndDir & 0b10000000) == 0b10000000) res += mul * 2;
        return res;
    }


    public static void main(String[] args) {
        short sh = 5;
        byte bt = 113;
        System.out.println(getDirection(bt, sh));
    }
}
