package ru.progwards.java1.lessons.register2;

public class Counter {
    public static void inc(Register value) {
        boolean move = true;
        Bit[] valueBits = value.getBits();
        int n = valueBits.length;
        for (int i = n - 1; i >= 0; i--){
            if (valueBits[i].value && move) valueBits[i].value = false;
            else if (valueBits[i].value || move) {
                valueBits[i].value = true;
                move = false;
            }
    }
        if (move)
            for (int i = 0; i < n; i ++) valueBits[i].value = false;
            value.putRegister(valueBits);
    }

    public static void dec(Register value){
        boolean move = true;
        Bit[] valueBits = value.getBits();
        int n = valueBits.length;
        for (int i = n - 1; i >= 0; i--){
            if (valueBits[i].value && move) {
                valueBits[i].value = false;
                move = false;
            }
            else if (!valueBits[i].value && move) valueBits[i].value = true;
        }
        value.putRegister(valueBits);
    }

    public static void main(String[] args) {
        ByteRegister byteRegister = new ByteRegister();
        for (int i = 0; i < 258; i ++) {
            System.out.println(byteRegister.toDecString());
            inc(byteRegister);
        }
    }
}
