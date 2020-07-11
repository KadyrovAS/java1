package ru.progwards.java1.lessons.bigints;

import java.math.BigInteger;

public class ArrayInteger{
    byte digits[];
    ArrayInteger(int n) {digits = new byte[n];}

    void fromInt(BigInteger value) { //укладывает BigInteger во внутренний массив digits
        int i = 0;
        while (value != BigInteger.ZERO) {
            digits[digits.length - ++i] = value.mod(BigInteger.TEN).byteValue();
            value = value.divide(BigInteger.TEN);
        }

        System.out.println();
        for (byte s: digits) System.out.println(s);
    }

    BigInteger toInt() { //преобразовывает из массива в BigInteger
        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i < digits.length; i++) {
            result = result.multiply(BigInteger.TEN);
            result = result.add(BigInteger.valueOf(digits[i]));
        }
        return result;
    }

    boolean add(ArrayInteger num) { //реализация сложения в столбик двух чисел, разложенных по цифрам в массиве
        byte iMod = 0; //перенос 1 в старший разряд при сложении
        int d1, d2;

        if (num.digits.length > this.digits.length) return false;

        for (int i = 1; i <= this.digits.length; i ++) {
            d1 = this.digits.length - i;
            d2 = num.digits.length - i;

            if (d2 >= 0) this.digits[d1] += num.digits[d2];
            this.digits[d1] += iMod;
            if (this.digits[d1] > 9) {
                this.digits[d1] %= 10;
                iMod = 1; //перенос 1 в старший разряд
            } else iMod = 0;
            }

        if (iMod == 0) return true;
        else return false; //не хватило размерности массива
    }


    public static void main(String[] args) {
        ArrayInteger a1 = new ArrayInteger(6);
        ArrayInteger a2 = new ArrayInteger(5);

        BigInteger bg1 = BigInteger.valueOf(23856);
        BigInteger bg2 = BigInteger.valueOf(76484);

        a1.fromInt(bg1);
        a2.fromInt(bg2);

        System.out.println(a1.add(a2));
    }
}
