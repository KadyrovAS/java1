package ru.progwards.java1.lessons.register1;

public class Counter {
    public static void inc(ByteRegister value) {
        boolean move = true;
        for (int i = 7; i >= 0; i--){
            if (value.bits[i].value && move) value.bits[i].value = false;
            else if (value.bits[i].value || move) {
                value.bits[i].value = true;
                move = false;
            }
    }
        if (move)
            for (int i = 0; i < 8; i ++) value.bits[i].value = false;
    }

    public static void dec(ByteRegister value){
        boolean move = true;
        for (int i = 7; i >= 0; i--){
            if (value.bits[i].value && move) {
                value.bits[i].value = false;
                move = false;
            }
            else if (!value.bits[i].value && move) value.bits[i].value = true;
        }
    }

    public static void main(String[] args) {
        ByteRegister byteRegister = new ByteRegister();
        for (int i = 0; i < 258; i ++) {
            System.out.println(byteRegister.toDecString());
            inc(byteRegister);
        }
    }
}
