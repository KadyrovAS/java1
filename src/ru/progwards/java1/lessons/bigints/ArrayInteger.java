package ru.progwards.java1.lessons.bigints;

import java.math.BigInteger;

public class ArrayInteger{  //укладывает BigInteger во внутренний массив digits
    byte digits[];
    ArrayInteger(int n) {digits = new byte[n];}
    void fromInt(BigInteger value) {
        int i = 0;
        while (value != BigInteger.ZERO) {
            digits[digits.length - ++i] = value.mod(BigInteger.TEN).byteValue();
            value = value.divide(BigInteger.TEN);
        }
    }

    BigInteger toInt() { //преобразовывает из массива в BigInteger
        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i < digits.length; i++) {
            result = result.multiply(BigInteger.TEN);
            result = result.add(BigInteger.valueOf(digits[i]));
        }
        return result;
    }

    boolean add(ArrayInteger num) { //реализация сложения в столбик двух чисел, разложеных по цифрам в массиве
        byte iMod = 0; //перенос 1 в старший разряд при сложении

        if (num.digits.length > this.digits.length) return false;

        for (int i = this.digits.length - 1; i >= 0; i --) {
            this.digits[i] += num.digits[i];
            this.digits[i] += iMod;
            if (this.digits[i] > 9) {
                this.digits[i] %= 10;
                iMod = 1; //перенос 1 в старший разряд
            } else iMod = 0;
            }
        for (byte x: digits) System.out.println(x);
        if (iMod == 0) return true;
        else return false; //не хватило размерности массива
    }


    public static void main(String[] args) {
        ArrayInteger a1 = new ArrayInteger(5);
        ArrayInteger a2 = new ArrayInteger(5);

        BigInteger bg1 = BigInteger.valueOf(23856);
        BigInteger bg2 = BigInteger.valueOf(76484);

        a1.fromInt(bg1);
        a2.fromInt(bg2);

        System.out.println(a1.add(a2));
    }
}
