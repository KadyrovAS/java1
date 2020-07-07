package ru.progwards.java1.lessons.bigints;

public class IntInteger extends AbsInteger {
    public int value;
    public IntInteger(int value) {super(value); this.value = (int) value;}

    @Override
    public String toString() {
        return "IntInteger{" +
                "value=" + value +
                '}';
    }

}
