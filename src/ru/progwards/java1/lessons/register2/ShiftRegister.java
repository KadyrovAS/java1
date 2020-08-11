package ru.progwards.java1.lessons.register2;

public class ShiftRegister {
    public static void left(Register value){
        Bit[] bits = value.getBits();
        int n = bits.length;
        for (int i = 0; i < n - 1; i ++)
            bits[i].value = bits[i+1].value;
        bits[n - 1].value = false;
        value.putRegister(bits);
    }

    public static void right(Register value){
        Bit[] bits = value.getBits();
        int n = bits.length;
        for (int i = n - 1; i > 0; i --) bits[i].value = bits[i-1].value;
        bits[0].value = false;
        value.putRegister(bits);
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
