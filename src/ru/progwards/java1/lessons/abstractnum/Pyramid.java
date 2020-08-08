package ru.progwards.java1.lessons.abstractnum;

import javax.crypto.spec.PSource;

public class Pyramid extends Figure3D {
    private Number segment;
    Pyramid(Number segment) {
        this.segment = segment;
    }
    @Override
    public Number getSegment() {
        return this.segment;
    }

    @Override
    public Number volume(){
        Number sCube = this.getSegment().mul(this.getSegment());
        sCube = sCube.mul(this.getSegment());
        return sCube.div(new IntNumber(3));
    }

    public static void main(String[] args) {
        Pyramid pyramid = new Pyramid(new IntNumber(6));
        System.out.println(pyramid.volume());
    }

}
