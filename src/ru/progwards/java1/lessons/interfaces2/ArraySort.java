package ru.progwards.java1.lessons.interfaces2;

public class ArraySort extends Number {

    public static void sort(Comparable<Number>[] a) {
        Comparable <Number> number;
        for (int i = 0; i < a.length-1; i ++)
            for (int k = i + 1; k < a.length; k ++)
                if (a[i].compareNumber(a[k]) < 0) {
                    number = a[i];
                    a[i] = a[k];
                    a[k] = number;
                }
    }
}
