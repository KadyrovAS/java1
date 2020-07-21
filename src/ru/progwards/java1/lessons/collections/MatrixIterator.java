package ru.progwards.java1.lessons.collections;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

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
            currentPosition1++;
        }
        return array[currentPosition1][currentPosition2++];
    }

    public static void main(String[] args) {
        List<Integer> linkedList = new LinkedList();
        for (int i = 0; i < 5; i++)
            linkedList.add(i);
//---------------------------------------

        for (ListIterator<Integer> listIterator = linkedList.listIterator(); listIterator.hasNext(); ) {
            Integer n = listIterator.next();
            if (n % 2 != 0)
                listIterator.set(n * 2);
        }
            for (int i = 0; i < linkedList.size(); i++) System.out.println(linkedList.get(i));

    }
}