package ru.progwards.java1.lessons.interfaces;

public class Food implements CompareWeight{
    private int weight;
    public Food(int weight) {this.weight = weight;}
    public int getWeight() {return this.weight;}

    @Override
    public CompareResult compareWeight(CompareWeight smthHasWeigt) {
        int res;
        Animal smthAnimal = (Animal) smthHasWeigt;
        res = Double.compare(this.getWeight(), smthAnimal.getWeight());
        if (res < 0) {
            return CompareResult.LESS;
        } else if (res == 0) return CompareResult.EQUAL;
        else return CompareResult.GREATER;
    }

}
