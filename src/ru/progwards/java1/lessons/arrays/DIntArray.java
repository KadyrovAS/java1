package ru.progwards.java1.lessons.arrays;

public class DIntArray {
    private int[] ar;
    public DIntArray() {
        ar = new int[0];
    }

    public void add(int num) {
        int n;
        int[] arCopy = new int[ar.length + 1];

        System.arraycopy(ar,0,arCopy,0,ar.length);
        arCopy[ar.length] = num;
        ar=arCopy;
    }

    public void atInsert(int pos, int num) {
        //предполагаем, что номер позиции задан корректно
        int[] arCopy = new int[ar.length+1];
        if (pos > 0) {
            System.arraycopy(ar,0,arCopy,0,pos);
        }
        arCopy[pos] = num;
        System.arraycopy(ar, pos, arCopy,pos + 1, ar.length - pos);
        ar = arCopy;
    }

    public void atDelete(int pos) {
        //предполагаем, что номер позиции задан корректно
        int[] arCopy = new int[ar.length - 1];
        if (pos > 0) {
            System.arraycopy(ar, 0, arCopy,0, pos);
        }
        System.arraycopy(ar, pos + 1, arCopy, pos, ar.length - pos - 1);
        ar = arCopy;
    }

    public int at(int pos) {
        return ar[pos];
    }

    public static void main(String[] args) {
        DIntArray arObj = new DIntArray();
        arObj.add(1);
        arObj.add(2);
        arObj.add(3);
        arObj.add(4);
        arObj.add(5);
        arObj.atInsert(2,30);
        arObj.atDelete(2);
        System.out.println(arObj.at(3));
    }
}
