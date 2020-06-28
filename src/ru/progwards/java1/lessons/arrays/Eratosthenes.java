package ru.progwards.java1.lessons.arrays;

import java.util.Arrays;

public class Eratosthenes {
    private boolean[] sieve;
    public Eratosthenes(int N) {
        sieve = new boolean[N + 1]; //Чтобы индекс массива соответствовал запрашиваемому числу
        Arrays.fill(sieve, true);
        sift();
    }
    private void sift() {
        for (int i = 2; i < sieve.length - 1; i ++) {
            for (int j = i + 1; j < sieve.length; j++) {

                if (j % i == 0) {
                    sieve[j] = false;
                }
            }
        }
    }
    public boolean isSimple(int n) {
        // Можно проверить, что n не превышает размерность массива sieve
        return sieve[n];
    }

    public static void main(String[] args) {
        Eratosthenes eratosthenes = new Eratosthenes(10);
        System.out.println(eratosthenes.isSimple(10));
    }
}

