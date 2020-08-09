package ru.progwards.java1.lessons.abstractnum;

import javax.crypto.spec.PSource;

public class Pyramid extends Figure3D {
    private Number segment;
    Pyramid(Number segment) {
        this.segment = segment;
    }

    @Override
    public Number volume(){
        return this.segment.mul(this.segment).mul(this.segment).div(new IntNumber(3));
    }

    public static void main(String[] args) {
        Pyramid pyramid = new Pyramid(new IntNumber(3));
        System.out.println(pyramid.volume());
    }

}
