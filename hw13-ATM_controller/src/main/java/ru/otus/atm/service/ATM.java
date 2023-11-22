package ru.otus.atm.service;



import ru.otus.atm.model.Banknote;

import java.util.Map;

public interface ATM {
    int getAvailableSum();

    Map<Banknote, Integer> putMoney(Map<Banknote, Integer> money);

    Map<Banknote, Integer> withdrawMoney(int quantityOfMoney);
}
