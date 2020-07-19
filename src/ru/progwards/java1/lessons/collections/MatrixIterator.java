package ru.progwards.java1.lessons.collections;

import java.util.Iterator;

public class MatrixIterator<T> implements Iterator<T> {

    private T[][] array;
    private int currentPosition1; //текущая позиция в 1 измерении
    private int currentPosition2; //текущая позиция во 2 измерении

    ArrayIterator(T[][] array) {
        this.array = array;
        currentPosition1 = 0;
        currentPosition2 = 0;
    }

    @Override
    public boolean hasNext() {
        // TODO Auto-generated method stub
        if (currentPosition2 == array[currentPosition1].length - 1 &&
           currentPosition1 == array.length - 1) return false;
        return true;
    }

    @Override
    public T next() {
        // TODO Auto-generated method stub
        if (currentPosition2 == array[currentPosition1].length - 1 &&
                currentPosition1 == array.length - 1) return null;
        currentPosition2 ++;
        if (currentPosition2 == array[currentPosition1].length) {
            currentPosition2 = 0;
            currentPosition1 ++;
        }
        return array[currentPosition1][currentPosition2];
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
