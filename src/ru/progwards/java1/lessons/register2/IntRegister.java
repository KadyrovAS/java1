package ru.progwards.java1.lessons.register2;

public class IntRegister extends Register {

    Bit[] bits = new Bit[32];
    public IntRegister() {
        for (int i = 0; i < 32; i ++) this.bits[i] = new Bit(false);
    }
    public IntRegister(int value) {
        int sym;
        for (int i = 0; i < 32; i ++) {
            if ((value & 1) == 1) this.bits[31-i] = new Bit(true);
            else this.bits[31-i] = new Bit(false);
            value >>= 1;
        }
    }

    @Override
    public void putRegister(Bit[] value)   {
        int n = value.length;
        for (int i = 0; i < n; i ++) this.bits[i] = value[i];
    }

    @Override
    public Bit[] getBits() {
        return this.bits;
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

    public Register toTwosComplement(Register value) {
        IntRegister currenRegister = (IntRegister) value;
        if (currenRegister.bits[0].value) {
            for (int i = 0; i < 32; i++)
                if (currenRegister.bits[i].value) currenRegister.bits[i].value = false;
                else currenRegister.bits[i].value = true;
                Counter.inc(currenRegister);
        } else {
            Counter.dec(currenRegister);
            for (int i = 0; i < 32; i++)
                if (currenRegister.bits[i].value) currenRegister.bits[i].value = false;
                else currenRegister.bits[i].value = true;
        }
        return currenRegister;
    }

    public Register copyRegister() {
        IntRegister intRegister = new IntRegister();
        for (int i = 0; i < 32; i ++)
            intRegister.bits[i] = new Bit(this.bits[i].value);
        return intRegister;
    }

    public static void main(String[] args) {
        IntRegister intRegister = new IntRegister(25);
        System.out.println(intRegister.toString());
        System.out.println(intRegister.toTwosComplement(intRegister).toString());
        System.out.println(intRegister.toTwosComplement(intRegister).toString());
    }

}
