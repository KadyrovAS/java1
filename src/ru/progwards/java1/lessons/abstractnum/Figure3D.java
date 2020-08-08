package ru.progwards.java1.lessons.abstractnum;

public class Figure3D {
    private Number segment;
    public Figure3D() {
        this.segment = new Number(0);
    }
    public Figure3D(Number segment) {
        this.segment = segment;
    }
    public Number volume() {
        if (this.getClass().getName().contains("Figure3D")) return null;
        Number sNumber = this.getSegment().mul(this.getSegment());
        return sNumber.mul(this.getSegment());
    }

    public Number getSegment() {
        return this.segment;
    }
    public static void main(String[] args) {
        Figure3D figure3D = new Figure3D(new IntNumber(5));
        System.out.println(figure3D.volume());
    }
}
