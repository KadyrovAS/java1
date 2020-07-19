package ru.progwards.java1.lessons.collections;

import java.util.Iterator;

public class ArrayIterator<T> implements Iterator<T> {

    private T[] array;
    private int currentPosition; //текущая позиция в массиве

    ArrayIterator(T[] array) {
        this.array = array;
        currentPosition = 0;
        for (T item: this.array) System.out.println(item);
    }

    @Override
    public boolean hasNext() {
        // TODO Auto-generated method stub
        if (currentPosition == array.length) return false;
        return true;
    }

    @Override
    public T next() {
        // TODO Auto-generated method stub
        if (currentPosition == array.length) return null;
        return array[currentPosition++];
    }

    public static void main(String[] args) {
        String [] myArray = new String[5];
        myArray[0] = "Один";
        myArray[1] = "Два";
        myArray[2] = "Три";
        myArray[3] = "Четыре";
        myArray[4] = "Пять";

        ArrayIterator ar = new ArrayIterator(myArray);

        System.out.println("After all");
        while (ar.hasNext()) {
            System.out.println(ar.next());
        }
    }
}

