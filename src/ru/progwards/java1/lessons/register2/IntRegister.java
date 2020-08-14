package ru.progwards.java1.lessons.register2;

public class IntRegister extends Register {

    public IntRegister() {
        bits = new Bit[32];
        for (int i = 0; i < 32; i ++) this.bits[i] = new Bit(false);
    }
    public IntRegister(int value) {
        int sym;
        bits = new Bit[32];
        for (int i = 0; i < 32; i ++) {
            if ((value & 1) == 1) this.bits[31-i] = new Bit(true);
            else this.bits[31-i] = new Bit(false);
            value >>= 1;
        }
    }

    public static void main(String[] args) {
        IntRegister intRegister = new IntRegister(25);
        System.out.println(intRegister.toString());
//        System.out.println(intRegister.toTwosComplement(intRegister).toString());
//        System.out.println(intRegister.toTwosComplement(intRegister).toString());
    }

}
