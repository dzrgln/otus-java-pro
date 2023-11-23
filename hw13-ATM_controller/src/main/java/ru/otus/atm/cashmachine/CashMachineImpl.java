package ru.otus.atm.cashmachine;

import org.springframework.stereotype.Service;
import ru.otus.atm.model.Banknote;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Map;

@Service
public class CashMachineImpl implements CashMachine {

    private final MoneyBox moneyBox;

    public CashMachineImpl() {
        LinkedList<Banknote> sortedBanknote = new LinkedList<>(Banknote.getBanknotesSortedByDenomination());
        moneyBox = initMoneyBox(sortedBanknote);
    }

    @Override
    public Map<Banknote, Integer> withdraw(int requiredSumOfMoney, Map<Banknote, Integer> reserve) {
        WithdrawalContext context = moneyBox.withdraw(new WithdrawalContext(requiredSumOfMoney, reserve));
        if (context.successfulWithdrawal) {
            return context.result;
        } else {
            return Collections.emptyMap();
        }
    }

    private MoneyBox initMoneyBox(LinkedList<Banknote> banknoteList) {
        Banknote first = banknoteList.poll();
        if (banknoteList.isEmpty()) {
            return new MoneyBox(null, first);
        }
        return new MoneyBox(initMoneyBox(banknoteList), first);
    }
}
