package ru.progwards.java1.lessons.interfaces;

import java.util.Objects;

public class Animal implements FoodCompare {
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
}