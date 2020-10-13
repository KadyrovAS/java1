package ru.progwards.java2.lessons.tests.calc;

import org.junit.Test;
import ru.progwards.java2.lessons.tests.SimpleCalculator;

public class CheckExceptionTestDiff {
    @Test(expected = ArithmeticException.class)
    public void diffWithException() {
        SimpleCalculator.diff(Integer.MIN_VALUE,1);
    }
}
