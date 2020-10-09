package ru.progwards.java2.lessons.generics;

public class ArraySort<T extends Comparable> {
    static <T extends Comparable> T[] sort(T[]ar){
        T elem;
        for (int i = 0; i < ar.length - 1; i ++)
            for (int k = i + 1; k < ar.length; k ++)
                if (ar[i].compareTo(ar[k]) > 0) {
                    elem = ar[i];
                    ar[i] = ar[k];
                    ar[k] = elem;
                }
        return ar;
    }

    public static void main(String[] args) {
        Integer[] ar = {4,7,2,5,9,1,8,12,3,11};
        ar = sort(ar);
        for (int elem: ar)
            System.out.println(elem);

    }
}
