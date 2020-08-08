package ru.progwards.java1.lessons.abstractnum;

import java.math.BigDecimal;
import java.util.StringTokenizer;

public class Number {
    private BigDecimal numeric;

    Number() {}

    Number(int numeric) {
        this.numeric = BigDecimal.valueOf(numeric);
    }

    Number(long numeric) {
        this.numeric = BigDecimal.valueOf(numeric);
    }

    Number(double numeric) {
        this.numeric = BigDecimal.valueOf(numeric);
    }

    Number(BigDecimal numeric) {
        this.numeric = numeric;
    }

    public BigDecimal getNumeric() {
        return this.numeric;
    }

    public Number mul(Number num) {
        if (this.getClass().getName().compareTo(
                num.getClass().getName()) == 0) return null;

        return new Number(this.getNumeric().multiply(num.getNumeric()));
    }

    public Number div(Number num) {
        if (this.getClass().getName().compareTo(
                num.getClass().getName()) == 0) return null;
        return new Number(this.getNumeric().divide(num.getNumeric()));

    }

    public Number newNumber(String strNum) {
        return new Number(Integer.valueOf(strNum));
    }

    public String toString() {
        return String.valueOf(this.numeric);
    }

    public static void main(String[] args) {
        String txt =
                "StringTokenizer - этот класс, " +
                        "нам строку разобьёт на раз.";
        StringTokenizer tokenizer = new StringTokenizer(txt, " .,");
        while (tokenizer.hasMoreTokens())
            System.out.print(tokenizer.nextToken());    }
}
