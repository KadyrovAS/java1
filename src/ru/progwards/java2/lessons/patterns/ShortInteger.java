package ru.progwards.java2.lessons.patterns;

public class ShortInteger extends AbsInteger {
short value;

    public ShortInteger(int value) {
        super(value);
        this.value = (short) value;
    }

    @Override
    public String toString() {
        return "ShortInteger{" +
                "value=" + value +
                '}';
    }
}