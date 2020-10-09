package ru.progwards.java2.lessons.generics;
//Подскажите пожалуйста, как правильно реализовать этот класс, чтобы
//в коробку можно было класть либо яблоки либо апельсины
//У меня не получилось. Застрял.

import java.util.ArrayList;

public class FruitBox<V extends Fruit> extends ArrayList  implements Comparable{

    @Override
    public int compareTo(Object o) {
        if (this.getWeight() == ((FruitBox) o).getWeight()) return 0;
        else if (this.getWeight() > ((FruitBox) o).getWeight()) return 1;
        else return -1;
    }

    public double getWeight() {
        double weight = 0;
        for (int i = 0; i < this.size(); i ++) {
            Fruit fruit = (Fruit) get(i);
            weight += fruit.getWeightFruit();
        }
            return weight;
    }

    public void moveTo(FruitBox<V> box){
        this.forEach(value->box.add(value));
        this.clear();
    }

    public static void main(String[] args) {
        Apple apple = new Apple();
        Orange orange = new Orange();
        FruitBox fruitBox1 = new FruitBox();
        fruitBox1.add(apple);
        fruitBox1.add(orange);
        fruitBox1.add(apple);

        FruitBox fruitBox2 = new FruitBox();
        fruitBox2.add(apple);
        fruitBox2.add(orange);
        fruitBox2.add(apple);

        System.out.println("fritBox1.size()=" + fruitBox1.size());
        System.out.println("fritBox2.size()=" + fruitBox2.size());

        fruitBox1.moveTo(fruitBox2);
        System.out.println("fritBox1.size()=" + fruitBox1.size());
        System.out.println("fritBox2.size()=" + fruitBox2.size());
    }
}
