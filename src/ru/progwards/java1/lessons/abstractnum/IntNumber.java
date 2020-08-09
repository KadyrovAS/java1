package ru.progwards.java1.lessons.abstractnum;

public class IntNumber extends Number {
    int num;

    public IntNumber(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return String.valueOf(num);
    }

    @Override
    public Number mul(Number num) {
        int arg = Integer.valueOf(num.toString());
        int res = this.num * arg;
        return new IntNumber(res);
    }

    @Override
    public Number div(Number num) {
        int arg = Integer.valueOf(num.toString());
        int res = this.num / arg;
        return new IntNumber(res);
    }

    @Override
    Number newNumber(String strNum) {
        return new IntNumber(Integer.valueOf(strNum));
    }
}
