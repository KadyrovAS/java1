package ru.progwards.java1.lessons.egts;

import ru.progwards.java1.lessons.register1.Bit;
import ru.progwards.java1.lessons.register1.ByteRegister;

public class EgtsDirectionAndSpeed {
    public static int getSpeed(short value) {
        short speedAndDir = value;
        int res = 0;
        int mult = 1;
        for (int i = 0; i < 15; i++) {
            if (i > 0) mult *= 2;
            if ((speedAndDir & 1) == 1) res += mult;
            speedAndDir >>= 1;
        }

        return res;
    }


    public static int getDirection(byte dirLow, short speedAndDir) {
        short valSpeed = speedAndDir;
        valSpeed >>= 15;

        byte btLow = dirLow;
        int res = 0;
        int mult = 1;
        for (int i = 0; i < 8; i++) {
            if (i > 0) mult *= 2;
            if ((speedAndDir & 1) == 1) res += mult;
            speedAndDir >>= 1;
        }
        if ((valSpeed & 1) == 1) {
            mult *= 2;
            res += mult;
        }
        return res;
    }

    public static void main(String[] args) {
        short sh = 5;
        byte bt = 24;
        getDirection(bt, sh);
    }
}
