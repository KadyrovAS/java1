package ru.progwards.java2.lessons.tests.calc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.progwards.java2.lessons.tests.SimpleCalculator;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class SimpleCalculatorTestDiff {
    public int val1;
    public int val2;
    public int expected;

    public SimpleCalculatorTestDiff(int val1, int val2, int expected) {
        this.val1 = val1;
        this.val2 = val2;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "Тест {index}: ({0}) - ({1}) = ({2})")
    public static Iterable<Object[]> dataForTest() {
        return Arrays.asList(new Object[][]{
                {135, 25, 110},
                {25, 20, 5},
                {-10, -5, -5},
                {48, 8, 40},
                {-90, -100, 10}
        });
    }
    @Test
    public void testWithParams() {
        assertEquals(expected, SimpleCalculator.diff(val1, val2));
    }
}
