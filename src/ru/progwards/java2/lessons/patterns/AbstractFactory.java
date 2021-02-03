package ru.progwards.java2.lessons.patterns;

public enum AbstractFactory {
    INSTANCE;
    public AbsInteger getAbsInteger(int value) {
        AbsIntegerFactory factory = null;
        if (value >= -128 && value <= 127)
            factory = ByteFactory.INSTANCE;
        else if (value >= -32768 && value <= 32767)
            factory = ShortFactory.INSTANCE;
        else
            factory = IntegerFactory.INSTANCE;

        return factory.createAbsInteger(value);
    }
}