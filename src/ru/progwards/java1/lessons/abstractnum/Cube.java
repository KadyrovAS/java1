package ru.progwards.java1.lessons.abstractnum;

public class Cube extends Figure3D {
    private Number segment;

    Cube(Number segment){
        this.segment = segment;
    }

    @Override
    public Number volume(){
        return this.segment.mul(this.segment).mul(this.segment);
    }

}
