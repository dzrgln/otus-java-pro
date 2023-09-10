package ru.otus.hw3.generics;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Box<T extends Fruit> {
    private final List<T> fruits = new ArrayList<>();

    public int size() {
        return fruits.size();
    }

    public void clear() {
        fruits.clear();
    }

    public void add(T newFruit) {
        fruits.add(newFruit);
    }

    public double weight() {
        return fruits.stream().mapToDouble(Fruit::getWeight).sum();
    }

    public void pour(@NotNull Box<T> anotherBox) {
        if (anotherBox.equals(this)) {
            throw new IllegalArgumentException();
        }
        fruits.addAll(anotherBox.fruits);
        anotherBox.clear();
    }

    public boolean compare(@NotNull Box<? extends Fruit> anotherBox) {
        return Math.abs(weight() - anotherBox.weight()) < 0.001;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
