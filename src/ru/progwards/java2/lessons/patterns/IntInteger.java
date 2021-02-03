package ru.progwards.java2.lessons.patterns;

public class IntInteger extends AbsInteger{
int value;
    public IntInteger(int value) {
        super(value);
        this.value = value;
    }

    @Override
    public String toString() {
        return "IntInteger{" +
                "value=" + value +
                '}';
    }
}
