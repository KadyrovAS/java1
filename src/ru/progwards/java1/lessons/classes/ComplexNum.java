package ru.progwards.java1.lessons.classes;

public class ComplexNum {
    int a, b;
    public ComplexNum(int a, int b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public String toString() {return a + "+" + b +"i";}

    public ComplexNum add(ComplexNum num) {
        this.a += num.a;
        this.b += num.b;
        ComplexNum res = new ComplexNum(this.a,this.b);
        return res;
    }

    public ComplexNum sub(ComplexNum num) {
        this.a -= num.a;
        this.b -= num.b;
        ComplexNum res = new ComplexNum(this.a,this.b);
        return res;
    }

    public ComplexNum mul(ComplexNum num) {
        ComplexNum res = new ComplexNum(this.a,this.b);
        res.a = this.a * num.a - this.b * num.b;
        res.b = this.b * num.a + this.a * num.b;
        this.a = res.a;
        this.b = res.b;
        return res;
    }

    public ComplexNum div(ComplexNum num) {
        ComplexNum res = new ComplexNum(this.a,this.b);
        res.a = (this.a * num.a + this.b * num.b) / (num.a * num.a + num.b * num.b);
        res.b = (this.b * num.a - this.a * num.b) / (num.a * num.a + num.b * num.b);
        this.a = res.a;
        this.b = res.b;
        return res;
    }
}
