package ru.progwards.java2.lessons.tests;

public class SimpleCalculator {
    public static int sum(int val1, int val2) throws ArithmeticException {
        long res = (long) val1 + val2;
        if (res > Integer.MAX_VALUE || res < Integer.MIN_VALUE)
            throw new ArithmeticException("Произошло переполнение на операции sum");
        return val1 + val2;
    }

    public static int diff(int val1, int val2){
        long res = (long)val1 - val2;
        if (res > Integer.MAX_VALUE || res < Integer.MIN_VALUE)
            throw new ArithmeticException("Произошло переполнение на операции diff");
        return (int) res;
    }

    public static int mult(int val1, int val2) throws ArithmeticException{
        long res = (long) val1 * val2;
        if (res > Integer.MAX_VALUE || res < Integer.MIN_VALUE)
            throw new ArithmeticException("Произошло переполнение на операции mult");
        return (int) res;
    }

    public static int div (int val1, int val2) throws ArithmeticException{
        if (val2 == 0)
            throw new ArithmeticException("Произошло деление на 0 в div");
        return val1 / val2;
    }
}
