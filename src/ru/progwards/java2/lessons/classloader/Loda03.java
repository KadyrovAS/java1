package ru.progwards.java2.lessons.classloader;

public class Loda03 implements Target {
    @Override
    public String process(String message) {
        return "Я Loda03. Получил message "+ message;
    }

}
