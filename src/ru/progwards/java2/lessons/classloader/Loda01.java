package ru.progwards.java2.lessons.classloader;

public class Loda01 implements Target {
    @Override
    public String process(String message) {
        return "Я Loda01. Получил message "+ message;
    }

}
