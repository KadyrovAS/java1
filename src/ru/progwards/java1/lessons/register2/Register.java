package ru.progwards.java1.lessons.register2;

public class Register {
    public String toString(){
        return null;
    }
    public String toDecString() {
        return null;
    }
    public Bit[] getBits() { return null; }
    public void putRegister(Bit[] value) {}

    public static int n2Mult(int n) { //возведение 2 в степень n
        int result = 1;
        if (n == 0) return 1;
        for (int i = 0; i < n; i ++) result *= 2;
        return result;
    }

    public Register copyRegister() {return null;}
    public Register toTwosComplement(Register value){return null;}
}
