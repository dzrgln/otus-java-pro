package ru.otus.atm.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.otus.atm.model.Banknote;
import ru.otus.atm.repository.BanknoteStorage;

import java.util.Map;

@Slf4j
@Service
public class ATMImpl implements ATM {
    private final BanknoteStorage banknoteStorage;

    @Autowired
    public ATMImpl(BanknoteStorage banknoteStorage) {
        this.banknoteStorage = banknoteStorage;
    }

    @Override
    public int getAvailableSum() {
        int availableSum = banknoteStorage.getAvailableSum();
        log.info("Текущий баланс составляет {}", availableSum);
        return availableSum;
    }

    @Override
    public Map<Banknote, Integer> putMoney(Map<Banknote, Integer> money) {
        log.info("Деньги {} положены в банкомат", money);
        banknoteStorage.putMoney(money);
        return money;
    }  

    @Override
    public Map<Banknote, Integer> withdrawMoney(int quantityOfMoney) {
        Map<Banknote, Integer> result = banknoteStorage.withdrawMoney(quantityOfMoney);
        if (result.isEmpty()) {
            throw new IllegalArgumentException("Запрашиваемая сумма больше доступной");
        } else {
            log.info("Запрашиваемая сумма {} выдана {}", quantityOfMoney, result);
            return result;
        }
    }
}
