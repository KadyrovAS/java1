package ru.progwards.java1.lessons.register2;

public class ShiftRegister {
    public static void left(ByteRegister value){
        int n = value.bits.length;
        for (int i = 0; i < n - 1; i ++)
            value.bits[i].value = value.bits[i+1].value;
        value.bits[n - 1].value = false;
    }

    public static void right(ByteRegister value){
        int n = value.bits.length;
        for (int i = n - 1; i > 0; i --) value.bits[i].value = value.bits[i-1].value;
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
