package ru.progwards.java1.lessons.compare_if_cycles;

public class TriangleInfo {
    public static boolean isTriangle(int a, int b, int c) {
        /*
        Возвращает true, если по данным трём сторонам (a, b, c) можно построить треугольник.
        Из геометрии известно, что в треугольнике длина каждой из сторон меньше суммы длин двух других сторон.
         */
        if (a<b+c && b<a+c && c<a+b) return true;
        else return false;
}
    public static boolean isRightTriangle(int a, int b, int c) {
        /*
        Возвращает true, если треугольник со сторонами a, b, c является прямоугольным.
        Из геометрии известно, что для прямоугольного треугольника выполняется теорема Пифагора
        (сумма квадратов катетов равна квадрату гипотенузы).
         */
        int ln1 = a * a;
        int ln2 = b * b;
        int ln3 = c * c;
        if (ln1 == ln2 + ln3 || ln2 == ln1 + ln3 || ln3 == ln1 + ln2) return true;
        else return false;
    }
    public static boolean isIsoscelesTriangle(int a, int b, int c) {
        /*
        Возвращает true, если треугольник со сторонами a, b, c является равнобедренным.
        Из геометрии известно, что в равнобедренном треугольнике есть две равные стороны.
         */
        if (a == b || b == c || a == c) return true;
        else return false;
}
    public static void main(String[] args) {

    }
}