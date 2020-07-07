package ru.progwards.java1.lessons.bigints;

public class ByteInteger extends AbsInteger {
    public byte value;
    public ByteInteger(byte value) {super(value); this.value = (byte) value;}

    @Override
    public String toString() {
        return String.valueOf(value);
    }


}
