package ru.progwards.java1.lessons.register2;

public class ByteRegister extends Register {
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
    public void putRegister(Bit[] value) {
        int n = value.length;
        for (int i = 0; i < n; i ++) this.bits[i] = value[i];
    }

    @Override
    public String toString() {
        String result = "";
        for (Bit bit: this.bits) result += bit.toString();
        return result;
            }

    @Override
    public String toDecString() {
        int result = 0;
        int n = this.bits.length;
        for (int i = 0; i < n; i ++){
            result += Integer.valueOf(this.bits[i].toString()) * n2Mult(n - i - 1);
        }
        return String.valueOf(result);
    }

    @Override
    public Bit[] getBits() {
        return this.bits;
    }

    public Register toTwosComplement(Register value) {
        ByteRegister currenRegister = (ByteRegister) value;
        if (currenRegister.bits[0].value) {
            for (int i = 0; i < 8; i++)
                if (currenRegister.bits[i].value) currenRegister.bits[i].value = false;
                else currenRegister.bits[i].value = true;
            Counter.inc(currenRegister);
        } else {
            Counter.dec(currenRegister);
            for (int i = 0; i < 8; i++)
                if (currenRegister.bits[i].value) currenRegister.bits[i].value = false;
                else currenRegister.bits[i].value = true;
        }
        return currenRegister;
    }

    public Register copyRegister() {
        ByteRegister byteRegister = new ByteRegister();
        for (int i = 0; i < 8; i ++)
            byteRegister.bits[i] = new Bit(this.bits[i].value);
        return byteRegister;
    }

    public static void main(String[] args) {
        byte bt = 5;

        ByteRegister byteRegister = new ByteRegister(bt);
        System.out.println(byteRegister.toString());
        System.out.println(byteRegister.toTwosComplement(byteRegister).toString());
        System.out.println(byteRegister.toTwosComplement(byteRegister).toString());
    }
}
