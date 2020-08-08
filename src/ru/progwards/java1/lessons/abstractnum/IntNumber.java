package ru.progwards.java1.lessons.abstractnum;

import java.math.BigDecimal;

public class IntNumber extends Number {
    private BigDecimal numeric;

    IntNumber(int num) {
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

    public Number newNumber(String strNum) {
        return new IntNumber(Integer.valueOf(strNum));
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
        Number intNumber = new Number(25);
        Number number = intNumber.div(new IntNumber(5));
        System.out.println("number = " + number);
    }
}
