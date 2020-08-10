package ru.progwards.java1.lessons.register1;

public class Summator {
    public static boolean add(ByteRegister value1, ByteRegister value2) {
        boolean move = false;
        for (int i = 7; i >= 0; i--) {
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
        byte bt1 = 0;
        byte bt2 = 1;
        ByteRegister byteRegister1 = new ByteRegister(bt1);
        ByteRegister byteRegister2 = new ByteRegister(bt2);
        System.out.println(add(byteRegister1,byteRegister2));
        System.out.println(byteRegister1.toDecString());
        System.out.println(add(byteRegister1,byteRegister2));
        System.out.println(byteRegister1.toDecString());
    }
}
