package ru.otus.hw1.gradle;

import com.google.common.collect.Lists;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> names = Lists.newArrayList("Max", "Davis", "Mark");
        System.out.println("Before reversed " + names);
        List<String> reversedNames = Lists.reverse(names);
        System.out.println("After reversed " + reversedNames);
    }
}
