package ru.progwards.java1.lessons.abstractnum;

public class IntNumber {
    public int numeric;
    public IntNumber(int num) {this.numeric = num;}

    public Number mul(Number num) {
        Number intResult = new Number(this.numeric * Integer.valueOf(num.toString()));
        return intResult;
    }
    public Number div(Number num) {
        Number intResult = new Number(this.numeric / Integer.valueOf(num.toString()));
        return intResult;
    }
//    public Number newNumber(String strNum)
}
