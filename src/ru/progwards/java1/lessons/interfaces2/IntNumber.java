package ru.progwards.java1.lessons.interfaces2;

public class IntNumber extends Number implements Comparable {
    int num;

    public IntNumber(int num) {
        this.num = num;
    }

    @Override
    public int compareNumber(Comparable value) {
        if (this.num > Integer.valueOf(value.toString())) return 1;
        else if(this.num < Integer.valueOf(value.toString())) return -1;
        return 0;
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
