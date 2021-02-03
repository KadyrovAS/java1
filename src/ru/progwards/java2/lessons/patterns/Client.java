package ru.progwards.java2.lessons.patterns;

public class Client{
    public static void main(String[] args) {
        System.out.println(AbstractFactory.INSTANCE.getAbsInteger(125));
        System.out.println(AbstractFactory.INSTANCE.getAbsInteger(1250));
        System.out.println(AbstractFactory.INSTANCE.getAbsInteger(125000));
    }
}
