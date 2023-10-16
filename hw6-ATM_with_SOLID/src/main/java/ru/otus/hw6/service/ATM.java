package ru.otus.hw6.service;

import ru.otus.hw6.model.Banknote;

import java.util.Map;

public interface ATM {
    int getAvailableSum();

    void putMoney(Map<Banknote, Integer> money);

    Map<Banknote, Integer> withdrawMoney(int quantityOfMoney);
}
