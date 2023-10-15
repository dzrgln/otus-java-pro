package ru.otus.hw6.service;

import ru.otus.hw6.model.Banknote;

import java.util.Map;

public interface ATM {
    void getAvailableSum();

    void putMoney(Map<Banknote, Integer> money);

    void withdrawMoney(int quantityOfMoney);
}
