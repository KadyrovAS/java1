package ru.progwards.java1.lessons.interfaces;

public class CalculateFibonacci {
    private static CacheInfo lastFibo;

    public static int fiboNumber(int n) {
        int fb1 = 1;
        int fb2 = 1;
        int fb3 = 1;
        if (lastFibo != null) {
            if (lastFibo.n == n) {
                return lastFibo.fibo;
            }
        }
        if (n < 3) return 1;
        else {
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
}
