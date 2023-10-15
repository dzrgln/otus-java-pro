package ru.otus.hw6.repository;

import ru.otus.hw6.model.Banknote;

import java.util.Map;

public interface BanknoteStorage {
    int getAvailableSum();

    int getQuantity(Banknote banknote);

    void putMoney(Map<Banknote, Integer> money);

    Map<Banknote, Integer> withdrawMoney(int quantityOfMoney);
}
