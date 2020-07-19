package ru.progwards.java1.lessons.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

public class Creator {
    public static Collection<Integer> fillEven(int n) {
        Collection<Integer> collection = new ArrayList<Integer>();
        int count = 0;
        while (count < n) collection.add(++count * 2 );
        return collection;
    }

    public static Collection<Integer> fillOdd(int n) {
        LinkedList<Integer> collection = new LinkedList<Integer>();
        int count = 1;
        while (count <= n) collection.push(count++ * 2 - 1);
        return collection;
    }

    public static Collection<Integer> fill3(int n) {
        Collection<Integer> collection = new ArrayList<Integer>();
        int count = 0;
        while (count < n * 3) {
            collection.add(count);
            collection.add(count * count);
            collection.add(count * count * count);
            count += 3;
        }
        return collection;
    }

    public static void main(String[] args) {
        Collection<Integer> collection = new ArrayList<Integer>();
        collection = fill3(10);
        for (Integer Item : collection) System.out.println(Item);
    }
}
