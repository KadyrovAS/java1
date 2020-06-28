package ru.progwards.java1.lessons.arrays;

public class ArraySort {
    public static void main(String[]args){
        int[] ar ={5,4,3,2,1};
        sort(ar);
    }


    public static void sort(int[] a) {
        int s;
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++)
                if (a[j] < a[i]) {
                    s = a[i];
                    a[i] = a[j];
                    a[j] = s;
                }
        }

    }
}
