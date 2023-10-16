package ru.otus.hw6.service;

import ru.otus.hw6.model.Banknote;
import ru.otus.hw6.repository.BanknoteStorage;

import java.util.Map;

public class ATMImpl implements ATM{
    private final BanknoteStorage banknoteStorage;

    public ATMImpl(BanknoteStorage banknoteStorage) {
        this.banknoteStorage = banknoteStorage;
    }

    @Override
    public int getAvailableSum() {
        return banknoteStorage.getAvailableSum();
    }

    @Override
    public void putMoney(Map<Banknote, Integer> money) {
        System.out.println("Деньги положены в банкомат");
        banknoteStorage.putMoney(money);
    }

    @Override
    public Map<Banknote, Integer> withdrawMoney(int quantityOfMoney) {
        Map<Banknote, Integer> result = banknoteStorage.withdrawMoney(quantityOfMoney);
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Запрашиваемая сумма больше доступной");
        } else {
            return result;
        }
    }
}
