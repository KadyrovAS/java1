package ru.progwards.java1.lessons.abstractnum;

public class DoubleNumber extends Number{
    public double numeric;

    public DoubleNumber(double num) {
        this.numeric = num;
    }

    public Number mul(Number num) {
        return new Number(0);
    }

    public Number div(Number num) {
        return new Number(0);
    }

    public Number newNumber(String strNum) {
        return new Number(Double.valueOf(strNum));
    }

    public String toString() {
        return String.valueOf(this.numeric);
    }
}
