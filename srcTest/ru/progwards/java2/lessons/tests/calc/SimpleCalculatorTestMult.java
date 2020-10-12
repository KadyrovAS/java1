package ru.progwards.java2.lessons.tests.calc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.progwards.java2.lessons.tests.SimpleCalculator;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class SimpleCalculatorTestMult {
    public int val1;
    public int val2;
    public int expected;

    public SimpleCalculatorTestMult(int val1, int val2, int expected) {
        this.val1 = val1;
        this.val2 = val2;
        this.expected = expected;
    }

    @Parameterized.Parameters(name = "Тест {index}: ({0}) X ({1}) = ({2})")
    public static Iterable<Object[]> dataForTest() {
        return Arrays.asList(new Object[][]{
                {0, 0, 0},
                {5, 6, 30},
                {-5, -5, 25},
                {25, 25, 625},
                {12, -12, -144}
        });
    }
    @Test
    public void testWithParams() {
        assertEquals(expected, SimpleCalculator.mult(val1, val2));
    }
}
