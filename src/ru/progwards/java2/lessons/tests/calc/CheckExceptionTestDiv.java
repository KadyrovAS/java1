package ru.progwards.java2.lessons.tests.calc;

import org.junit.Test;
import ru.progwards.java2.lessons.tests.SimpleCalculator;

public class CheckExceptionTestDiv {
    @Test(expected = ArithmeticException.class)
    public void divWithException() {
        SimpleCalculator.div(10,0);
    }
}
