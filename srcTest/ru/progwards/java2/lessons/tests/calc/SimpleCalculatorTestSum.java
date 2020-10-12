package ru.progwards.java2.lessons.tests.calc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.progwards.java2.lessons.tests.SimpleCalculator;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class SimpleCalculatorTestSum {
        public int val1;
        public int val2;
        public int expected;

        public SimpleCalculatorTestSum(int val1, int val2, int expected) {
            this.val1 = val1;
            this.val2 = val2;
            this.expected = expected;
        }

        @Parameterized.Parameters(name = "Тест {index}: ({0}) + ({1}) = ({2})")
        public static Iterable<Object[]> dataForTest() {
            return Arrays.asList(new Object[][]{
                    {0, 0, 0},
                    {5, 0, 5},
                    {-5, -5, -10},
                    {34, 55, 89},
                    {-34, -55, -89}
            });
        }
        @Test
        public void testWithParams() {
            assertEquals(expected, SimpleCalculator.sum(val1, val2));
        }
    }
