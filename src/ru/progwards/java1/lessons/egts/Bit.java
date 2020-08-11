package ru.progwards.java1.lessons.egts;

public class Bit {
    public boolean value;
    public Bit(){}

    public Bit(boolean value){
        this.value = value;
    }

    public String toString() {
        if(this.value) return "1";
        else return "0";
    }

    public static void main(String[] args) {
        Bit bit = new Bit(false);
        System.out.println(bit);
    }

}
