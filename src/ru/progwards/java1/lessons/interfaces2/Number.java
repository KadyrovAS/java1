package ru.progwards.java1.lessons.interfaces2;


public class Number implements Comparable {

    @Override
    public int compareTo(Comparable value) {
        return 0;
    }
    public Number mul(Number num) {
        return null;
    }

    public Number div(Number num) {
        return null;
    }

    Number newNumber(String strNum) {
        return null;
    }

    public String toString() {
        return null;
    }

    public static void main(String[] args) {
        IntNumber intNumber1 = new IntNumber(45);
        IntNumber intNumber2 = new IntNumber(36);
        System.out.println(intNumber1.compareTo(intNumber2));
    }

}
