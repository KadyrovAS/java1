package ru.progwards.java2.lessons.patterns;

public enum ShortFactory implements AbsIntegerFactory{
        INSTANCE;

        @Override
        public AbsInteger createAbsInteger(int value) {
            return new ShortInteger(value);
        }
}
