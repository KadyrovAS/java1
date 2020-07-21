package ru.progwards.java1.lessons.sets;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class SetOperations {
    public static Set<Integer> union(Set<Integer> set1, Set<Integer> set2) {
        //Объединение множеств
        HashSet <Integer>resultSet = new HashSet(set1);
        resultSet.addAll(set2);
        return resultSet;
    }
    public static Set<Integer> intersection(Set<Integer> set1, Set<Integer> set2) {
        //Пересечение множеств
        HashSet <Integer>resultSet = new HashSet(set1);
        resultSet.retainAll(set2);
        return resultSet;
    }
    public static Set<Integer> difference(Set<Integer> set1, Set<Integer> set2) {
        //Разница множеств
        HashSet <Integer>resultSet = new HashSet();
        Iterator <Integer> iterator = set1.iterator();
        Integer currentItem;
        while (iterator.hasNext()) {
            currentItem = iterator.next();
            if (!set2.contains(currentItem)) resultSet.add(currentItem);
        }
        return resultSet;
    }
    public static Set<Integer> symDifference(Set<Integer> set1, Set<Integer> set2) {
        //Симметрическая разница
        return difference(union(set1, set2), intersection(set1, set2));
    }


    public static void main(String[] args) {
        Set<Integer> set1 = new HashSet<Integer>();
        Set<Integer> set2 = new HashSet<Integer>();
        set1.add(1);
        set1.add(2);
        set1.add(3);
        set1.add(4);
        set1.add(5);

        set2.add(4);
        set2.add(5);
        set2.add(6);
        set2.add(7);
        System.out.println(symDifference(set1, set2));
    }
}
