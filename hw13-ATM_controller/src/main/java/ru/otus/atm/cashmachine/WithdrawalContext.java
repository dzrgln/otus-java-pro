package ru.otus.atm.cashmachine;


import ru.otus.atm.model.Banknote;

import java.util.HashMap;
import java.util.Map;

class WithdrawalContext {

    int requiredSum;
    Map<Banknote, Integer> result;
    Map<Banknote, Integer> reserve;
    boolean successfulWithdrawal;

    final boolean possibleWithdrawal;

    WithdrawalContext(int requiredSum, Map<Banknote, Integer> reserve) {
        this.requiredSum = requiredSum;
        this.result = new HashMap<>();
        this.reserve = reserve;

        possibleWithdrawal = getSumOfMoney(reserve) >= requiredSum;
    }

    private static int getSumOfMoney(Map<Banknote, Integer> mapOfMoney) {
        return mapOfMoney.entrySet().stream()
                .mapToInt(entry -> entry.getKey().getDenomination() * entry.getValue())
                .sum();
    }

}
