package ru.progwards.java1.lessons.abstractnum;

public class IntNumber extends Number {
    public int numeric;

    public IntNumber(int num) {
        this.numeric = num;
    }

    @Override
    public Number mul(Number num) {
        return num.mul(this);
    }

    public Number div(Number num) {
        return new Number(0);
    }

    public Number newNumber(String strNum) {
        return new Number(Integer.valueOf(strNum));
    }

    public String toString() {
        return String.valueOf(this.numeric);
    }

    public static void main(String[] args) {
        IntNumber intNumber = new IntNumber(25);

        System.out.println(intNumber.mul(new Number(5)));
    }
}
