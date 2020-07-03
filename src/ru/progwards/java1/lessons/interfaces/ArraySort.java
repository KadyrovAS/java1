package ru.progwards.java1.lessons.interfaces;

public class ArraySort{
    public static void sort(CompareWeight[] a) {
        CompareWeight s;
        Animal ai, aj;
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                ai = (Animal) a[i];
                aj = (Animal) a[j];
                if (aj.getWeight() < ai.getWeight()) {
                    s = a[i];
                    a[i] = a[j];
                    a[j] = s;
                }
            }
        }
    }

}
