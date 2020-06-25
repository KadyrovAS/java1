package ru.progwards.java1.lessons.basics;

public class ReverseDigits {
    public static int reverseDigits(int number) {
       int i1 = number / 100; //Рассчитываем сотни
       int i2 = (number - i1 * 100) / 10; //Рассчитываем десятки
       int i3 = number - i1 * 100 - i2 * 10; //Рассчитываем единицы
       return i3 * 100 + i2 * 10 + i1; //Меняем сотни и единицы местами
    }

    public static void main(String[] args) {
        System.out.println(reverseDigits(123));
    }
}