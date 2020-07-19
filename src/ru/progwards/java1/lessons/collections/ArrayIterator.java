package ru.progwards.java1.lessons.collections;

import java.util.Iterator;

public class ArrayIterator<T> implements Iterator<T> {

    private T[] array;
    private int currentPosition; //текущая позиция в массиве

    ArrayIterator(T[] array) {
        this.array = array;
        currentPosition = 0;
    }

    @Override
    public boolean hasNext() {
        // TODO Auto-generated method stub
        if (currentPosition == array.length - 1) return false;
        return true;
    }

    @Override
    public T next() {
        // TODO Auto-generated method stub
        if (currentPosition == array.length - 1) return null;
        currentPosition++;
        return array[currentPosition];
    }

    public static void main(String[] args) {
        int[][] ar = new int[4][6];
        for (int i = 0; i < 4; i ++)
            for (int k = 0; k < 6; k ++)
                ar[i][k] = i * 10 + k;

        for (int i = 0; i < 4; i ++) {
            for (int k = 0; k < 6; k++) {
                System.out.print("i = " + i + "; ");
                System.out.print("k = " + k + "; ");
                System.out.println(ar[i][k]);
            }
        }
        System.out.println(ar.length);
        System.out.println(ar[1].length);
    }
}

