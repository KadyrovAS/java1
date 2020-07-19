package ru.progwards.java1.lessons.collections;

import java.util.*;

public class Finder {
    public static Collection<Integer> findMinSumPair(Collection<Integer> numbers) {
        Collection <Integer>resultCollection = new ArrayList <Integer>();
        int currentPosition = 0;
        int previosItem = 0, currentItem = 0;
        int minSumma = 0;
        int resultPosition1 = 0, resultPosition2 = 0;
        for (int item: numbers) {
            if (currentPosition == 0) {
                currentItem = item;
            } else {
                previosItem = currentItem;
                currentItem = item;
                if (currentPosition == 1) {
                    minSumma = currentItem + previosItem;
                    resultPosition1 = currentPosition - 1;
                    resultPosition2 = currentPosition;
                }
                else  if (currentItem + previosItem < minSumma) {
                    minSumma = currentItem + previosItem;
                    resultPosition1 = currentPosition - 1;
                    resultPosition2 = currentPosition;
                }
                } currentPosition ++;
        }
        resultCollection.add(resultPosition1);
        resultCollection.add(resultPosition2);
        return resultCollection;
    }

    private static boolean localMax(Collection<Integer> numbers) {
        ArrayList <Integer> arrayList = new ArrayList<Integer>(numbers);
        if (arrayList.get(1) > arrayList.get(0) &
            arrayList.get(1) > arrayList.get(2)) return true;
        return false;
    }

    public static Collection<Integer> findLocalMax(Collection<Integer> numbers) {
        ArrayList <Integer> collection = new <Integer> ArrayList(numbers);
        Collection <Integer> result = new <Integer> ArrayList();
        for (int i = 1; i < collection.size() - 1; i ++)
            if (localMax(collection.subList(i - 1, i + 2))) result.add(collection.get(i));
            return result;
    }

    public static boolean findSequence(Collection<Integer> numbers) {
        ArrayList<Integer> arrayList = new ArrayList(numbers);
        arrayList.sort(null);
        for (int i = 0; i < arrayList.size(); i ++)
            if (i + 1 != arrayList.get(i)) return false;
            return true;
    }

    public static String findSimilar(Collection<String> names) {
        String resultSimilar = "";
        String currentItem = "";
        String previosItem = "";
        int resultCount = 0;
        int countSimilar = 0;
        LinkedList <String> linkedList = new LinkedList<String>(names);
        Iterator <String> iterator = linkedList.iterator();
        while (iterator.hasNext()) {
            currentItem = iterator.next();
            if (currentItem.compareTo(previosItem) == 0) countSimilar ++;
            else if (countSimilar == 0) {
                countSimilar ++;
                previosItem = currentItem;
            } else if (countSimilar > resultCount) {
                resultCount = countSimilar;
                countSimilar = 1;
                resultSimilar = previosItem;
                previosItem = currentItem;
            } else countSimilar = 1;

        }
        return resultSimilar + ":" +Integer.toString(resultCount);
    }

    public static void main(String[] args) {
        Collection<Integer> collection = new ArrayList<Integer>();
        Integer[] ar = new Integer[]{78, 3, 1,12,24,36,48,69};
        for (int item: ar) collection.add(item);
        System.out.println(findMinSumPair(collection));

        }
    }
