package ru.progwards.java2.lessons.patterns;

public enum ByteFactory implements AbsIntegerFactory{
    INSTANCE;

    @Override
    public AbsInteger createAbsInteger(int value) {
        return new ByteInteger(value);
    }
}
