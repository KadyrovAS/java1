package ru.progwards.java1.lessons.abstractnum;

public class Test {
    public static void main(String[] args) {
        //Объем куба в целых числах, со стороной 3
        Cube cube = new Cube(new IntNumber(3));
        System.out.println(cube.volume());
        //Объем куба в double, со стороной 3
        cube = new Cube(new DoubleNumber(3));
        System.out.println(cube.volume());
        //Объем пирамиды в целых числах, со стороной 3
        Pyramid pyramid = new Pyramid(new IntNumber(3));
        System.out.println(pyramid.volume());
        //Объем пирамиды в double, со стороной 3
        pyramid = new Pyramid(new DoubleNumber(3));
        System.out.println(pyramid.volume());
    }
}
