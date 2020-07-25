package ru.progwards.java1.lessons.maps;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class FiboMapCache {
    final static int N_ITERATE = 1000;
    private Map<Integer, BigDecimal> fiboCache = new HashMap();
    private boolean cacheOn;
    public FiboMapCache(boolean cacheOn) {this.cacheOn = cacheOn;}

    public BigDecimal fiboNumber(int n) {
        BigDecimal fiboResult;
        if (cacheOn == false) return fibo(n);
        if (fiboCache.get(n) == null) {
            fiboResult = fibo(n);
            fiboCache.put(n, fiboResult);
        } else  return fiboCache.get(n);

        return fiboResult;
    }

    public void clearCahe() {fiboCache.clear();}

    public static void test() {
        FiboMapCache testFiboMap = new FiboMapCache(true);
        for (int i = 1; i <= N_ITERATE; i ++) {
            testFiboMap.fiboNumber(i);
        }
        long start = System.currentTimeMillis();
        for (int i = 1; i <= N_ITERATE; i ++) {
            testFiboMap.fiboNumber(i);
        }
        System.out.println("fiboNumber cacheOn=true время выполнения " +
                (System.currentTimeMillis() - start));

        testFiboMap.cacheOn = false;
        start = System.currentTimeMillis();
        for (int i = 1; i <= N_ITERATE; i ++) {
            testFiboMap.fiboNumber(i);
        }
        System.out.println("fiboNumber cacheOn=false время выполнения " +
                (System.currentTimeMillis() - start));
        System.out.println();
    }

    private static BigDecimal fibo(int n) {
        BigDecimal value1 = BigDecimal.ONE;
        BigDecimal value2 = BigDecimal.ONE;
        BigDecimal value3;

        if (n < 3) return BigDecimal.ONE;
        for (int i = 3; i <= n; i ++) {
            value3 = value2.add(value1);
            value1 = value2;
            value2= value3;
        }
        return value2;
    }

    public static void main(String[] args) {
        test();
    }
}
