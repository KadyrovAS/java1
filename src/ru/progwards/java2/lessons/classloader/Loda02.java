package ru.progwards.java2.lessons.classloader;

public class Loda02 implements Target {
    @Override
    public String process(String message) {
        return "Я Loda02. Получил message "+ message;
    }

}
