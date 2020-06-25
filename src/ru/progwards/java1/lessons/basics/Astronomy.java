package ru.progwards.java1.lessons.basics;

public class Astronomy {
    // Площадь поверхности сферы
    public static Double sphereSquare(Double r) {
        final double pi = 3.14;
        Double S = 4 * pi * r * r;
        return S;
    }
    //Площадь поверхности Земли
    public static Double earthSquare() {
        final Double r = 6371.2; //Радиус Земли
        Double S = sphereSquare(r);
        return S;
    }

    //площадь поверхности Меркурия
    public static Double mercurySquare() {
        final Double r = 2439.7;
        Double S = sphereSquare(r);
        return S;
    }
    //площадь поверхности Юпитера
    public static Double jupiterSquare() {
        final Double r = 71492d;
        Double S = sphereSquare(r);
        return S;
    }

    //отношение площади поверхности Земли к площади поверхности Меркурия
    public static Double earthVsMercury() {
        Double S = earthSquare() / mercurySquare();
        return S;
    }

    public static Double earthVsJupiter() {
        Double S = earthSquare() / jupiterSquare();
        return S;
    }
    public static void main(String[] args) {
        System.out.println(earthVsMercury());
        System.out.println(earthVsJupiter());
    }
}
