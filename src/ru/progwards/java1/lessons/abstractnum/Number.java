package ru.progwards.java1.lessons.abstractnum;

import java.math.BigDecimal;

public class Number {
    private BigDecimal numeric;
    Number(int numeric) {this.numeric = BigDecimal.valueOf(numeric);}
    Number(long numeric) {this.numeric = BigDecimal.valueOf(numeric);}
    Number(double numeric) {this.numeric = BigDecimal.valueOf(numeric);}
    Number(BigDecimal numeric) {this.numeric = numeric;}
    public Number mul(Number num) {
        if(this.getClass().getName().compareTo(
                num.getClass().getName()) == 0) return null;
        return new Number(this.numeric.multiply(num.numeric));
    }
    public Number div(Number num) {
        if(this.getClass().getName().compareTo(
                num.getClass().getName()) == 0) return null;
        return new Number(this.numeric.divide(num.numeric));

    }
    public Number newNumber(String strNum) {
        return new Number(Integer.valueOf(strNum));
    }
    public String toString() {
        return String.valueOf(this.numeric);
    }

    public static void main(String[] args) {
        Number num = new Number(25);
        System.out.println(num.mul(num.newNumber("5")));
    }
}
