package ru.progwards.java1.lessons.interfaces2;

public class ArraySort extends Number {

    public static void sort(Number[] a) {
        Number number;
        for (int i = 0; i < a.length-1; i ++)
            for (int k = i + 1; k < a.length; k ++)
                if (a[i].compareTo(a[k]) > 0) {
                    number = a[i];
                    a[i] = a[k];
                    a[k] = number;
                }
    }

    public static void main(String[] args) {
        IntNumber[] ar = new IntNumber[5];
        ar[0] = new IntNumber(10);
        ar[1] = new IntNumber(4);
        ar[2] = new IntNumber(45);
        ar[3] = new IntNumber(3);
        ar[4] = new IntNumber(14);
        sort(ar);
        for (Number number: ar) System.out.println(number);
    }
}
