package ru.progwards.java1.lessons.abstractnum;

public class Figure3D {
    Number segment;
    public Figure3D() {
        this.segment = new Number(0);
    }
    public Figure3D(Number segment) {
        this.segment = segment;
    }
    public Number volume() {
        return this.segment.mul(this.segment);
    }

    public static void main(String[] args) {
        Figure3D figure3D = new Figure3D(new Number(5));
        System.out.println(figure3D.volume());
    }
}
