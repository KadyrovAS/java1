package ru.progwards.java1.lessons.interfaces;

public class ArraySort implements CompareWeight{
    private int weight;

    public ArraySort(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;
    }

    @Override
    public CompareResult compareWeight(CompareWeight smthHasWeigt) {
        int res;
        ArraySort smthFood;
        smthFood = (ArraySort) smthHasWeigt;
        res = Double.compare(this.getWeight(), smthFood.getWeight());
        if (res < 0) {
            return CompareResult.LESS;
        } else if (res == 0) return CompareResult.EQUAL;
        else return CompareResult.GREATER;
    }

    public static void sort(CompareWeight[] a) {
        //Я не совсем понял этот момент. CompareWeight - это метод, а не каласс.
        //И в интерфейсе он описан как метод. Как понять такой тип передаваемого аргумента?

        CompareWeight s;
        ArraySort ai, aj;
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                //Не нашел другого варианта переопределить массив
                //(ArraSort) a - не пропускает
                //(ArraySort) a[i] тоже ругается

                ai = (ArraySort) a[i];
                aj = (ArraySort) a[j];
                if (aj.compareWeight(ai) == CompareResult.LESS) {
                    s = a[i];
                    a[i] = a[j];
                    a[j] = s;
                }
            }
        }
    }

    public static void main(String[] args) {
        ArraySort[] arWeight = new ArraySort[10];
        for (int i = 0; i < 10; i++) {
            arWeight[i] = new ArraySort(100 - i);
        }
        sort(arWeight);
        for (int i = 0; i < 10; i++) {
            System.out.println(arWeight[i].weight);
        }

    }
}
