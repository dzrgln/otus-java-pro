package ru.otus.hw2.generics;

import java.util.ArrayList;
import java.util.List;

public class Box<T extends Fruit>{
    private final List<T> fruits = new ArrayList<>();

    public int size() {
        return fruits.size();
    }

    public void clear(){
        fruits.clear();
    }

    public void add(T newFruit) {
        fruits.add(newFruit);
    }

    public double weight() {
        return fruits.stream().mapToDouble(Fruit::getWeight).sum();
    }

    public void pour(Box<T> anotherBox) {
        fruits.addAll(anotherBox.fruits);
        anotherBox.clear();
    }

    public boolean compare(Box<? extends Fruit> anotherBox) {
        return weight() == anotherBox.weight();
    }
}
