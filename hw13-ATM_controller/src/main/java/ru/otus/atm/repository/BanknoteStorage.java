package ru.otus.atm.repository;



import ru.otus.atm.model.Banknote;

import java.util.Map;

public interface BanknoteStorage {
    int getAvailableSum();

    int getQuantity(Banknote banknote);

    void putMoney(Map<Banknote, Integer> money);

    Map<Banknote, Integer> withdrawMoney(int quantityOfMoney);
}
