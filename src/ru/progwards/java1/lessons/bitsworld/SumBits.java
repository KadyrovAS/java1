package ru.progwards.java1.lessons.bitsworld;

public class SumBits {
    public static int sumBits(byte value) {
        int sum = 0;
        for (int i = 1; i <= 8; i++) {
            int result = value & 0b00000000_00000000_00000000_00000001;
            sum += result;
            value >>= 1;
        }
        return sum;
    }

    public static void main(String[] args) {
        byte value = 0b1111111;
        System.out.println(sumBits(value));
    }
}
