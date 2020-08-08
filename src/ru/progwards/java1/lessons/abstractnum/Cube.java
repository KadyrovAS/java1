package ru.progwards.java1.lessons.abstractnum;

public class Cube extends Figure3D {
    private Number segment;
    public Cube(Number segment){
        this.segment = segment;
    }

    @Override
    public Number volume(){
        Number sCube = this.getSegment().mul(this.getSegment());
        return sCube.mul(this.getSegment());
    }

    @Override
    public Number getSegment() {
        return this.segment;
    }

    public static void main(String[] args) {
        Cube cube = new Cube(new IntNumber(3));
        System.out.println(cube.volume());
    }
}
