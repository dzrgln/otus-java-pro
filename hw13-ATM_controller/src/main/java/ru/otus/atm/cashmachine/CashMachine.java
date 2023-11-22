package ru.otus.atm.cashmachine;


import ru.otus.atm.model.Banknote;

import java.util.Map;

public interface CashMachine {
    Map<Banknote, Integer> withdraw(int requiredSumOfMoney, Map<Banknote, Integer> reserve);
}
