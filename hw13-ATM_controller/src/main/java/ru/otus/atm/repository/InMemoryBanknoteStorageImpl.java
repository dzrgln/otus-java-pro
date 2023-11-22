package ru.otus.atm.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.otus.atm.cashmachine.CashMachine;
import ru.otus.atm.model.Banknote;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryBanknoteStorageImpl implements BanknoteStorage {
    private final CashMachine cashMachine;
    private final Map<Banknote, Integer> banknoteStorage;

    @Autowired
    public InMemoryBanknoteStorageImpl(CashMachine cashMachine) {
        this.cashMachine = cashMachine;
        banknoteStorage = createStorage();
    }

    @Override
    public int getAvailableSum() {
        int res = 0;
        for (Map.Entry<Banknote, Integer> entry : banknoteStorage.entrySet()) {
            res += entry.getKey().getDenomination() * entry.getValue();
        }
        return res;
    }

    @Override
    public int getQuantity(Banknote banknote) {
        return banknoteStorage.get(banknote);
    }

    @Override
    public void putMoney(Map<Banknote, Integer> money) {
        for(Map.Entry<Banknote, Integer> entry: money.entrySet()) {
            banknoteStorage.computeIfPresent(entry.getKey(), (K,v) -> v + entry.getValue());
        }
    }

    @Override
    public Map<Banknote, Integer> withdrawMoney(int quantityOfMoney) {
        return cashMachine.withdraw(quantityOfMoney, banknoteStorage);
    }

    private Map<Banknote, Integer> createStorage() {
        Map<Banknote, Integer> res = new HashMap<>();
        for (Banknote banknote : Banknote.values()) {
            res.put(banknote, 100);
        }
        return res;
    }
}
