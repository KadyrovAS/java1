package ru.progwards.java2.lessons.tests.calc;

import org.junit.Test;
import ru.progwards.java2.lessons.tests.SimpleCalculator;

public class CheckExceptionTestSum {
        @Test(expected = ArithmeticException.class)
        public void sumWithException() {
            SimpleCalculator.sum(Integer.MAX_VALUE,1);
        }
}
