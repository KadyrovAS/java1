package ru.progwards.java1.lessons.collections;

import java.util.Iterator;

public class MatrixIterator<T> implements Iterator<T> {

    private T[][] array;
    private int currentPosition1; //текущая позиция в 1 измерении
    private int currentPosition2; //текущая позиция во 2 измерении

    MatrixIterator(T[][] array) {
        this.array = array;
        currentPosition1 = 0;
        currentPosition2 = 0;
    }

    @Override
    public boolean hasNext() {
        // TODO Auto-generated method stub
        if (currentPosition2 == array[currentPosition1].length &&
           currentPosition1 == array.length - 1) return false;
        return true;
    }

    @Override
    public T next() {
        // TODO Auto-generated method stub
        if (currentPosition2 == array[currentPosition1].length &&
                currentPosition1 == array.length) return null;

        if (currentPosition2 == array[currentPosition1].length) {
            currentPosition2 = 0;
            currentPosition1 ++;
        }
        return array[currentPosition1][currentPosition2++];
    }

    public static void main(String[] args) {
        Integer[][] myArray = new Integer[4][6];
        for (int i = 0; i < 4; i ++)
            for (int k = 0; k < 6; k ++)
                myArray[i][k] = i * 10 + k;

        MatrixIterator ar = new MatrixIterator(myArray);

        while (ar.hasNext()) {
            System.out.println(ar.next());
        }
    }
}
