
package ru.progwards.java1.lessons.bigints;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BigAlgebra {
//    алгоритм быстрого возведения в степень pow числа num в BigDecimal
//    степень числа pow рассмотрим в двоичном виде
//    Если младший разряд = 1, то число num возводится в 1-ю степень
//    Если второй разряд = 1, то число возводится в квадрат и умножается на предыдущее произведение и т.д.
//    Например, число a^5. 5 = 0b101. a * (a * a) * (a * a) или a^1 * a^4


    static BigDecimal fastPow(BigDecimal num, int pow) {
        int sim;
        BigDecimal result = BigDecimal.ONE;
        BigDecimal multResult = num;
        while (pow != 0) {
            sim = pow & 0b1;
            if (sim == 0b1) result = result.multiply(multResult);
            multResult = multResult.multiply(multResult);
            pow >>= 1;
        }
        return result;
    }

    //    алгоритм вычисления n-го числа фибоначчи в BigInteger
    static BigInteger fibonacci(int n) {
        BigInteger a1 = BigInteger.ONE;
        BigInteger a2 = BigInteger.ONE;
        BigInteger a3 = BigInteger.ZERO;
        if (n < 3) return BigInteger.ONE;
        for (int i = 3; i <= n; i ++) {
            a3 = a1.add(a2);
            a1 = a2;
            a2 = a3;
        }
        return a3;
    }

    public static void main(String[] args) {
        for (int i = 1; i <= 15; i ++) {
            System.out.println(fibonacci(i));
        }
    }

}
