package ru.progwards.java1.lessons.register2;

public class ByteRegister extends Register {

    public ByteRegister() {
        bits = new Bit[8];
        for (int i = 0; i < 8; i ++) this.bits[i] = new Bit(false);
    }

    public ByteRegister(byte value) {
        bits = new Bit[8];
        for (int i = 0; i < 8; i ++) {
            if ((value & 1) == 1) this.bits[7-i] = new Bit(true);
            else this.bits[7-i] = new Bit(false);
            value >>= 1;
        }
    }

    public static void main(String[] args) {
        byte bt = 5;

        ByteRegister byteRegister = new ByteRegister(bt);
        System.out.println(byteRegister.toString());
        System.out.println(byteRegister.toTwosComplement(byteRegister).toString());
        System.out.println(byteRegister.toTwosComplement(byteRegister).toString());
    }
}
