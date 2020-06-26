package ru.progwards.java1.lessons.compare_if_cycles;

public class TriangleSimpleInfo {
    public static int maxSide(int a, int b, int c) {
        int max = a;
        if (b>max) max = b;
        if (c>max) max = c;
        return max;
    }
    public static int minSide(int a, int b, int c) {
        int min = a;
        if (b<min) min = b;
        if (c<min) min = c;
        return min;
    }
    public static boolean isEquilateralTriangle(int a, int b, int c) {
        if (a == b && b == c) return true;
        else return false;
    }
    public static void main(String[] args) {
        System.out.println(isEquilateralTriangle(3,3,3));
    }
}
