package ru.progwards.java1.lessons.register1;

public class ShiftRegister {
    public static void left(ByteRegister value){
        for (int i = 0; i < 7; i ++) value.bits[i].value = value.bits[i+1].value;
        value.bits[7].value = false;
    }

    public static void right(ByteRegister value){
        for (int i = 7; i > 0; i --) value.bits[i].value = value.bits[i-1].value;
        value.bits[0].value = false;
    }

    public static void main(String[] args) {
        byte bt = 1;
        ByteRegister byteRegister = new ByteRegister(bt);
        System.out.println(byteRegister.bits[7]);
        System.out.print(byteRegister.toDecString() + "   ");
        System.out.println(byteRegister.toString());
        left(byteRegister);
        System.out.print(byteRegister.toDecString() + "   ");
        System.out.println(byteRegister.toString());

    }
}
