package ru.progwards.java1.lessons.basics;

public class AccuracyDoubleFloat {
    final static double pi = 3.14;

    //объём шара с радиусом radius double
    public static double volumeBallDouble(double radius) {
        double Vd = pi * radius * radius * radius * 4 / 3;
        return Vd;
    }

    //объём шара с радиусом radius float
    public static float volumeBallFloat(float radius) {
        //final double pi = 3.14;
        float Vf = (float) pi * radius * radius * radius * 4 / 3;
        return Vf;
    }

    public static double calculateAccuracy(double radius) {
         return volumeBallDouble(radius) - volumeBallFloat((float) radius);
    }

    public static void main(String[] args) {
        final double r = 6371.2;

        System.out.println(calculateAccuracy(r));
    }
}
