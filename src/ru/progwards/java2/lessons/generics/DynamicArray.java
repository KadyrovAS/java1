package ru.progwards.java2.lessons.generics;

public class DynamicArray<T> {
    private T[] array = (T[]) new Object[1];
    private int arraySize;

    public <T> DynamicArray() {
        this.arraySize = 0;
    }

    public void add(T elem) {
        arraySize ++;
        if (this.array.length >= this.arraySize) {
            array[arraySize-1] = elem;
            return;
        }
        T[] arrayCopy = (T[]) new Object[(arraySize - 1) * 2];
        for (int i = 0; i < arraySize - 1; i ++)
            arrayCopy[i] = array[i];
        arrayCopy[arraySize-1] = elem;
        array = arrayCopy;
    }

    public void insert(T elem) {
        arraySize ++;
        if (this.array.length >= this.arraySize)
        {
            for (int i = this.array.length - 2; i >= 0; i --)
                this.array[i + 1] = this.array[i];
            this.array[0] = elem;
            return;
        }
        T[] arrayCopy = (T[]) new Object[(arraySize - 1) * 2];
        arrayCopy[0] = elem;
        for (int i = 0; i < arraySize - 1; i++)
            arrayCopy[i + 1] = array[i];
        this.array = arrayCopy;
    }

    public void remove(int pos) {
        if (pos >= this.array.length) return;
        int delta = 0;
        for (int i = 0; i < this.arraySize; i ++)
            if (i == pos) delta = -1;
            else array[i + delta] = array[i];
        this.arraySize --;
        array[arraySize] = null;
    }

    public T get(int pos) {
        if (pos >= this.array.length)
            return null;
        return this.array[pos];
    }

    public int size() {
        return array.length;
    }

    public static void main(String[] args) {
        DynamicArray dar = new DynamicArray();
        dar.insert("12");
        dar.add("5");
        dar.add("6");
        dar.add("8");
        dar.add("24");
        dar.add("25");
        dar.add(36);
        dar.add(10);
        dar.insert(777);
        dar.remove(5);
        for (int i = 0; i < dar.size(); i++)
            System.out.println(i + "  " + dar.get(i));
        System.out.println("arraySize = " + dar.arraySize);
    }
}
