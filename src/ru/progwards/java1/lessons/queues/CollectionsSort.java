package ru.progwards.java1.lessons.queues;

import javax.swing.*;
import java.util.*;

public class CollectionsSort {
    static final int ITERATIONS = 1_000;

    public static void mySort(Collection<Integer> data) {
        ArrayList<Integer> myArray = new ArrayList<Integer>(data);
        for (int i = 0; i < myArray.size() - 1; i++)
            for (int k = i + 1; k < myArray.size(); k++)
                if (myArray.get(i) > myArray.get(k)) Collections.swap(myArray, i, k);
        data.clear();
        data.addAll(myArray);
    }

    public static void minSort(Collection<Integer> data) {
        int minItem;
        ArrayList<Integer> myArray = new ArrayList<Integer>();
        while (!data.isEmpty()) {
            minItem = Collections.min(data);
            myArray.add(minItem);
            data.remove(minItem);
        }
        data.addAll(myArray);
    }

    static void collSort(Collection<Integer> data) {
        List<Integer> myList = new ArrayList<Integer>(data);
        Collections.sort(myList);
        data.clear();
        data.addAll(myList);
    }

    public static Collection<String> compareSort() {
        class Test {
            String functionName;
            long runtime;

            Test(String functionName, long runtime) {
                this.functionName = functionName;
                this.runtime = runtime;
            }
        }
        Collection<String> colResult = new ArrayList<String>();

        long start;
        List<Integer> col1 = new ArrayList<Integer>();
        List<Integer> col2 = new ArrayList<Integer>();
        List<Integer> col3 = new ArrayList<Integer>();
        for (int i = ITERATIONS; i > 0; i--) col1.add(i);
        Collections.shuffle(col1);
        col2.addAll(col1);
        col3.addAll(col1);
        TreeSet<Test> treeResult = new TreeSet<Test>(new Comparator<Test>() {
            @Override
            public int compare(Test o1, Test o2) {
                if (o1.runtime == o2.runtime)
                    return o1.functionName.compareTo(o2.functionName);
                return Long.compare(o1.runtime, o2.runtime);
            }
        });

        start = System.currentTimeMillis();
        mySort(col1);
        treeResult.add(new Test("mySort", System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        minSort(col2);
        treeResult.add(new Test("minSort", System.currentTimeMillis() - start));

        start = System.currentTimeMillis();
        collSort(col3);
        treeResult.add(new Test("collSort", System.currentTimeMillis() - start));

        for (Test test : treeResult) colResult.add(test.functionName);

        return colResult;
    }

    public static void main(String[] args) {
        Collection<Integer> col = new ArrayList<Integer>();
        Collections.addAll(col, 3, 1, 5, 4, 2);
//        System.out.println(col);
        System.out.println(compareSort());
//        System.out.println(col);
    }
}
