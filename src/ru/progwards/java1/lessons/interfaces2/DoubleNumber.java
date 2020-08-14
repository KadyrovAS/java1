package ru.progwards.java1.lessons.interfaces2;

public class DoubleNumber extends Number implements Comparable{
    double num;

    public DoubleNumber(double num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return String.valueOf(num);
    }

    @Override
    public int compareNumber(Comparable value) {
        if (this.num > Double.valueOf(value.toString())) return 1;
        else if(this.num < Double.valueOf(value.toString())) return -1;
        return 0;
    }

    @Override
    public Number mul(Number num) {
        double arg = Double.valueOf(num.toString());
        double res = this.num * arg;
        return new DoubleNumber(res);
    }

    @Override
    public Number div(Number num) {
        double arg = Double.valueOf(num.toString());
        double res = this.num / arg;
        return new DoubleNumber(res);
    }

    @Override
    Number newNumber(String strNum) {
        return new DoubleNumber(Double.valueOf(strNum));
    }
}
