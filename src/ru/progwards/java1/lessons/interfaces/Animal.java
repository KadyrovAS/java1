package ru.progwards.java1.lessons.interfaces;

import java.util.Objects;

public class Animal implements FoodCompare, CompareWeight {
    public double weight;
    public Animal(double weight) {
        this.weight = weight;
    }
    public enum AnimalKind {ANIMAL, COW, HAMSTER, DUCK}
    public enum FoodKind {UNKNOWN, HAY, CORN}
    public AnimalKind getKind() {
        return AnimalKind.ANIMAL;
    }
    public FoodKind getFoodKind() {return FoodKind.UNKNOWN;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Double.compare(animal.weight, weight) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight);
    }

    public double getFood1kgPrice() { //возвращает информацию о цене 1 кг еды
        return switch (getFoodKind()) {
            case UNKNOWN -> 0;
            case HAY -> 20;
            case CORN -> 50;
        };
    }

    public double getFoodPrice() { //возвращает информацию о цене еды для конкретного животного
        return calculateFoodWeight() * getFood1kgPrice();
    }

    @Override
    public String toString() {
        return "I am " + getKind() + ", eat " + getFoodKind();
    }
    public double getWeight() {return weight;}
    public double getFoodCoeff() {return 0.02;}
    public double calculateFoodWeight() {return getWeight() * getFoodCoeff();}
    public String toStringFull() {return "I am " + getKind() + ", eat " + getFoodKind() + " " + calculateFoodWeight();}
    @Override
    public int compareFoodPrice(Animal aminal) {
        return Double.compare(this.getFoodPrice(), aminal.getFoodPrice());
    }

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

    public static void sort(CompareWeight[] a) {
        CompareWeight s;
        Animal ai, aj;
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                ai = (Animal) a[i];
                aj = (Animal) a[j];
                if (aj.getWeight() < ai.getWeight()) {
                    s = a[i];
                    a[i] = a[j];
                    a[j] = s;
                }
            }
        }
    }
}