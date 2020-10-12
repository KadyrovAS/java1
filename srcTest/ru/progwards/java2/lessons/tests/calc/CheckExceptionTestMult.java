package ru.progwards.java2.lessons.tests.calc;

import org.junit.Test;
import ru.progwards.java2.lessons.tests.SimpleCalculator;

public class CheckExceptionTestMult {
    @Test(expected = ArithmeticException.class)
    public void multWithException() {
        SimpleCalculator.mult(Integer.MAX_VALUE,Integer.MAX_VALUE);
    }
}
