package ru.otus.hw6.model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum Banknote {
    FIFTY(50),
    ONE_HUNDRED(100),
    TWO_HUNDRED(200),
    FIVE_HUNDRED(500),
    ONE_THOUSAND(1000),
    TWO_THOUSAND(2000),
    FIVE_THOUSAND(5000);

    private final int denomination;

    Banknote(int denomination) {
        this.denomination = denomination;
    }

    public int getDenomination() {
        return denomination;
    }

    public static List<Banknote> getBanknotesSortedByDenomination() {
        return Stream.of(Banknote.values())
                .sorted((n1, n2) -> n2.getDenomination() - n1.getDenomination())
                .collect(Collectors.toCollection(LinkedList::new));
    }
}
