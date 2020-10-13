package ru.progwards.java2.lessons.tests.calc;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        SimpleCalculatorTestSum.class,
        CheckExceptionTestSum.class,
        SimpleCalculatorTestDiff.class,
        CheckExceptionTestDiff.class,
        SimpleCalculatorTestMult.class,
        CheckExceptionTestMult.class,
        SimpleCalculatorTestDiv.class,
        CheckExceptionTestDiv.class

})
public class RunAllRests {

}
