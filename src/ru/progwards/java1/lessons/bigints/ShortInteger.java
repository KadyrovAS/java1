package ru.progwards.java1.lessons.bigints;

public class ShortInteger extends AbsInteger {
    public short value;
    public ShortInteger(short value) {super(value); this.value = (short) value;}

    @Override
    public String toString() {
        return String.valueOf(value);
    }

}
