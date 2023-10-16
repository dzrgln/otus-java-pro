package ru.otus.hw6;

import ru.otus.hw6.cashmachine.CashMachine;
import ru.otus.hw6.cashmachine.CashMachineImpl;
import ru.otus.hw6.repository.BanknoteStorage;
import ru.otus.hw6.repository.InMemoryBanknoteStorageImpl;
import ru.otus.hw6.service.ATM;
import ru.otus.hw6.service.ATMImpl;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import static ru.otus.hw6.model.Banknote.*;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        CashMachine cashMachine = new CashMachineImpl();
        BanknoteStorage banknoteStorage = new InMemoryBanknoteStorageImpl(cashMachine);
        ATM atm = new ATMImpl(banknoteStorage);

        System.out.printf("До пополнения %s \n", atm.getAvailableSum());
        atm.getAvailableSum();

        atm.putMoney(Map.of(
                FIVE_THOUSAND, 2,
                ONE_HUNDRED, 1
        ));
        System.out.printf("После пополнения %s \n", atm.getAvailableSum());

        System.out.println("Снимаем 12550");
        atm.withdrawMoney(12550);

        System.out.printf("После снятия %s \n", atm.getAvailableSum());
        atm.getAvailableSum();

        TimeUnit.SECONDS.sleep(10);
        atm.withdrawMoney(1_000_000);

    }
}
