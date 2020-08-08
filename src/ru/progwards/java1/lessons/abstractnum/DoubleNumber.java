package ru.progwards.java1.lessons.abstractnum;

import java.math.BigDecimal;

public class DoubleNumber extends Number {
    public BigDecimal numeric;

    public DoubleNumber(double num) {
        this.numeric = BigDecimal.valueOf(num);
    }

    @Override
    public Number mul(Number num) {
        Number arg = new Number(this.getNumeric());
        return arg.mul(num);
    }

    @Override
    public Number div(Number num) {
        Number arg = new Number(this.getNumeric());
        return arg.div(num);
    }

    @Override
    public Number newNumber(String strNum) {
        return new DoubleNumber(Double.valueOf(strNum));
    }

    @Override
    public String toString() {
        return String.valueOf(this.numeric);
    }

    @Override
    public BigDecimal getNumeric() {
        return this.numeric;
    }

    public static void main(String[] args) {
        DoubleNumber doubleNumber = new DoubleNumber(6.25);
        System.out.println(doubleNumber.div(new DoubleNumber(2.5)));
    }
}
