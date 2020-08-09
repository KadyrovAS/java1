package ru.progwards.java1.lessons.abstractnum;

public class DoubleNumber extends Number {
    double num;

    public DoubleNumber(double num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return String.valueOf(num);
    }

    @Override
    public Number mul(Number num) {
        double arg = Double.valueOf(num.toString());
        double res = this.num * arg;
        return new DoubleNumber(res);
    }

    @Override
    public Number div(Number num) {
        double arg = Integer.valueOf(num.toString());
        double res = this.num / arg;
        return new DoubleNumber(res);
    }

    @Override
    Number newNumber(String strNum) {
        return new DoubleNumber(Double.valueOf(strNum));
    }
}
