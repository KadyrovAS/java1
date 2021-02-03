package ru.progwards.java2.lessons.patterns;

public class ByteInteger extends AbsInteger{
    byte value;

    public ByteInteger(int value) {
        super(value);
        this.value = (byte) value;
    }

    @Override
    public String toString() {
        return "ByteInteger{" +
                "value=" + value +
                '}';
    }
}
