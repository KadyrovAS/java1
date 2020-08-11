package ru.progwards.java1.lessons.register1;

import ru.progwards.java1.lessons.register2.Register;

public class Summator {
    public static boolean add(ByteRegister value1, ByteRegister value2) {
        boolean move = false;

        int n1 = value1.bits.length;

        for (int i = n1 - 1; i >= 0; i--) {
            if (value1.bits[i].value && value2.bits[i].value) {
                value1.bits[i].value = move;
                move = true;
            } else if (value1.bits[i].value || value2.bits[i].value) value1.bits[i].value = !move;
            else if (!value1.bits[i].value && !value2.bits[i].value) {
                value1.bits[i].value = move;
                move = false;
            }
        }
        return !move;
    }

    public static void main(String[] args) {
        byte bt1 = 18;
        byte bt2 = -7;
        ByteRegister byteRegister1 = new ByteRegister(bt1);
        ByteRegister byteRegister2 = new ByteRegister(bt2);
        add(byteRegister1, byteRegister2);
        System.out.println(byteRegister1.toDecString());
    }
}
