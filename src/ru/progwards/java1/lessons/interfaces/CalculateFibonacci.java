package ru.progwards.java1.lessons.interfaces;

public class CalculateFibonacci {
    private static CacheInfo lastFibo = new CacheInfo();

    public static int fiboNumber(int n) {
        int fb1 = 1;
        int fb2 = 1;
        int fb3 = 1;
        if (lastFibo == null)
            lastFibo = new CacheInfo();
        if (lastFibo.n == n) {
                return lastFibo.fibo;
            }

        if (n < 3) {
          lastFibo.n = n;
          lastFibo.fibo = 1;
        } else {
            for (int i = 3; i <= n; i++) {
                fb3 = fb1 + fb2;
                fb1 = fb2;
                fb2 = fb3;
            }
        }
        lastFibo.n = n;
        lastFibo.fibo = fb2;
        return fb2;
    }

    public static class CacheInfo {
        public int n, fibo;
    }

    public static CacheInfo getLastFibo() {
        return lastFibo;
    }

    public static void clearLastFibo() {
        lastFibo = null;
    }

    public static void main(String[] args) {

        System.out.println(fiboNumber(1));
        System.out.println(getLastFibo().n + " " + getLastFibo().fibo);
        clearLastFibo();
        System.out.println(getLastFibo());
        System.out.println(fiboNumber(10));
    }
}
