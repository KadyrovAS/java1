package ru.progwards.java1.lessons.egts;

public class ByteRegister {
    Bit[] bits = new Bit[8];
    public ByteRegister() {
        for (int i = 0; i < 8; i ++) this.bits[i] = new Bit(false);
    }

    public ByteRegister(byte value) {
        for (int i = 0; i < 8; i ++) {
            if ((value & 1) == 1) this.bits[7-i] = new Bit(true);
            else this.bits[7-i] = new Bit(false);
            value >>= 1;
        }
    }

    @Override
    public String toString() {
        String result = "";
        for (Bit bit: this.bits) result += bit.toString();
        return result;
            }

    public String toDecString() {
        int result = 0;
        int n = this.bits.length;
        for (int i = 0; i < n; i ++){
            result += Integer.valueOf(this.bits[i].toString()) * n2Mult(n - i - 1);
        }
        return String.valueOf(result);
    }

    public Bit[] getBits() {
        return this.bits;
    }

    public static int n2Mult(int n) { //возведение 2 в степень n
        int result = 1;
        if (n == 0) return 1;
        for (int i = 0; i < n; i ++) result *= 2;
        return result;
    }

    public static void main(String[] args) {
        byte bt = 5;

    }
}
