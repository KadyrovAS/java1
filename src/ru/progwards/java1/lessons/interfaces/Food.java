package ru.progwards.java1.lessons.interfaces;

public class Food implements CompareWeight{
    private int weight;
    public Food(int weight) {this.weight = weight;}
    public int getWeight() {return this.weight;}

    @Override
    public CompareResult compareWeight(CompareWeight smthHasWeigt) {
        int res;
        Food smthFood;
        smthFood = (Food) smthHasWeigt;
        res = Double.compare(this.getWeight(), smthFood.getWeight());
        if (res < 0) {
            return CompareResult.LESS;
        } else if (res == 0) return CompareResult.EQUAL;
        else return CompareResult.GREATER;
    }

    public static void main(String[] args) {
        Food fd1 = new Food(100);
        Food fd2 = new Food(200);
        System.out.println(fd1.compareWeight(fd2));
    }
}
