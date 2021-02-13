package ru.progwards.java1.lessons.sort;

public class EntryHeap implements Comparable<EntryHeap> {
    int key;
    int value;
    EntryHeap(int key, int value){
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return "key=" + key + ", value=" + value;
    }

    @Override
    public int compareTo(EntryHeap o) {
        if (this.value > o.value)
            return 1;
        else
            return -1;
    }
}
