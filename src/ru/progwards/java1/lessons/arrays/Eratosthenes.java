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
        for (int i = 2; i < sieve.length -1; i ++) { //я бы скреативил так (int i = 2; i < sieve.length / 2; i ++)
            if (! sieve[i]) { //первое незачеркнутое число в списке
                break;
            }
            for (int j = 2; j < sieve.length - 1; j++) {
                if (i * j > sieve.length - 1) {
                    continue;
                }

                sieve[i * j] = false;
            }
        }
    }
    public boolean isSimple(int n) {
        // Можно проверить, что n не превышает размерность массива sieve
        return sieve[n];
    }

    public static void main(String[] args) {
        int n = 400;
        Eratosthenes er = new Eratosthenes(n);
        for (int i = 0; i <= n; i ++) {
            System.out.println(i + " " + er.isSimple(i));
        }

    }
}
