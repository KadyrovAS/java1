package ru.progwards.java1.lessons.register2;

public class Register {
    Bit[] bits;

    public String toString(){
        String result = "";

        for (Bit bit: this.bits) result += bit.toString();
        return result;
    }
    public String toDecString() {
        int result = 0;
        int mult = 1;
        int n = this.bits.length;
        for (int i = n - 1; i >= 0; i --){
            if (i < n - 1) mult *= 2;
            if (this.bits[i].value) result += mult;
        }
        return String.valueOf(result);

    }
    public Bit[] getBits() {
        return this.bits;
    }
    public void putRegister(Bit[] value) {
        int n = value.length;
        for (int i = 0; i < n; i ++) this.bits[i] = value[i];
    }

    public Register toTwosComplement(Register value) {
        Register currenRegister = value;
        int n = value.bits.length;
        if (currenRegister.bits[0].value) {
            for (int i = 0; i < n; i++)
                if (currenRegister.bits[i].value) currenRegister.bits[i].value = false;
                else currenRegister.bits[i].value = true;
            Counter.inc(currenRegister);
        } else {
            Counter.dec(currenRegister);
            for (int i = 0; i < n; i++)
                if (currenRegister.bits[i].value) currenRegister.bits[i].value = false;
                else currenRegister.bits[i].value = true;
        }
        return currenRegister;
    }

    public static void main(String[] args) {
        IntRegister intRegister = new IntRegister(128);
//        System.out.println(intRegister.toString());
        Bit[] bits = intRegister.getBits();
        System.out.println(intRegister.toDecString());
    }
}
