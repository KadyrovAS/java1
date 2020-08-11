package ru.progwards.java1.lessons.register2;

public class Summator {
    public static boolean add(Register value1, Register value2) {
        boolean move = false;
        Bit[] valueBits1 = value1.getBits();
        Bit[] valueBits2 = value2.getBits();

        int n1 = valueBits1.length;

        for (int i = n1 - 1; i >= 0; i--) {
            if (valueBits1[i].value && valueBits2[i].value) {
                valueBits1[i].value = move;
                move = true;
            } else if (valueBits1[i].value || valueBits2[i].value) valueBits1[i].value = !move;
            else if (!valueBits1[i].value && !valueBits2[i].value) {
                valueBits1[i].value = move;
                move = false;
            }
        }
        value1.putRegister(valueBits1);
        return !move;
    }


    public static void sub(Register value1, Register value2) {
        Register value = value2.copyRegister();
        value = value.toTwosComplement(value);
        add(value1, value);
    }
    public static void main(String[] args) {
        byte bt1 = 75;
        byte bt2 = 30;
        ByteRegister byteRegister1 = new ByteRegister(bt1);
        ByteRegister byteRegister2 = new ByteRegister(bt2);
        sub(byteRegister1,byteRegister2);
        System.out.println(byteRegister1.toDecString());

    }
}
