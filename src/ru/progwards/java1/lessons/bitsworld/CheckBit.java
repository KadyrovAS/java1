package ru.progwards.java1.lessons.bitsworld;

public class CheckBit {
    public static int checkBit(byte value, int bitNumber) {
        int result = 0;
        for (int i = 1; i <= bitNumber + 1; i++) {
            result = value & 0b00000000_00000000_00000000_00000001;
            value >>= 1;
        }
    return result;
    }

    public static void main(String[] args) {
        byte value = 0b1010111;
        System.out.println(checkBit(value, 3));
    }
}
