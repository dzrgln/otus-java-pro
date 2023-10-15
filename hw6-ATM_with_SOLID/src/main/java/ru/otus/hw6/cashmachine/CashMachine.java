package ru.otus.hw6.cashmachine;

import ru.otus.hw6.model.Banknote;

import java.util.Map;

public interface CashMachine {
    Map<Banknote, Integer> withdraw(int requiredSumOfMoney, Map<Banknote, Integer> reserve);
}
