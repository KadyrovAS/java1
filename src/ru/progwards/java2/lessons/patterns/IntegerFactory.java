package ru.progwards.java2.lessons.patterns;

public enum IntegerFactory implements AbsIntegerFactory{
    INSTANCE;

    @Override
    public AbsInteger createAbsInteger(int value) {
        return new IntInteger(value);
    }
}
