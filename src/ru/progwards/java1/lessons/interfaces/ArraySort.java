package ru.progwards.java1.lessons.interfaces;

public class ArraySort implements CompareWeight{

    public CompareResult compareWeight(CompareWeight smthHasWeigt) {
            Animal smthAnimal = (Animal) smthHasWeigt;
            CompareWeight smthObj = this;
            Animal currentAnimal = (Animal) smthObj;
            if (currentAnimal.getWeight() < smthAnimal.getWeight()) return CompareResult.LESS;
            else return CompareResult.GREATER;
    }

    public static void sort(CompareWeight[] a) {
        CompareWeight s;

        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {

                if (a[j].compareWeight(a[i]) == CompareResult.LESS) {
                    s = a[i];
                    a[i] = a[j];
                    a[j] = s;
                }
            }
        }
    }

    public static void main(String[] args) {
        CompareWeight[] ar = new CompareWeight[10];
        Animal ai;
        for (int i = 0; i < 10; i++) {
            ar[i] = new Animal(200 - i);
        }
        sort(ar);
        for (int i = 0; i < 10; i ++) {
            ai = (Animal) ar[i];
            System.out.println(ai.weight);
        }
    }
    }
