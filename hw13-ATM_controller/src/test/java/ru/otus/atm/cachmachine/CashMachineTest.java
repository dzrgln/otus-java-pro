package ru.otus.atm.cachmachine;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.atm.cashmachine.CashMachine;
import ru.otus.atm.cashmachine.CashMachineImpl;
import ru.otus.atm.model.Banknote;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.otus.atm.model.Banknote.*;


public class CashMachineTest {
    CashMachine cashMachine = new CashMachineImpl();
    Map<Banknote, Integer> reserve = new HashMap<>();

    @BeforeEach
    public void initReserve() {
        reserve.put(FIVE_THOUSAND, 0);
        reserve.put(TWO_THOUSAND, 4);
        reserve.put(ONE_THOUSAND, 3);
        reserve.put(FIVE_HUNDRED, 2);
        reserve.put(TWO_HUNDRED, 2);
        reserve.put(ONE_HUNDRED, 1);
        reserve.put(FIFTY, 1);
    }

    @AfterEach
    public void clearReserve() {
        reserve.clear();
    }

    @Test
    public void withdraw4200() {
        Map<Banknote, Integer> expected = Map.of(
                TWO_THOUSAND, 2,
                TWO_HUNDRED, 1
        );
        Map<Banknote, Integer> result = cashMachine.withdraw(4200, reserve);
        assertEquals(expected, result);
    }

    @Test
    public void withdraw5750() {
        Map<Banknote, Integer> expected = Map.of(
                TWO_THOUSAND, 2,
                ONE_THOUSAND, 1,
                FIVE_HUNDRED, 1,
                TWO_HUNDRED, 1,
                FIFTY, 1
        );
        Map<Banknote, Integer> result = cashMachine.withdraw(5750, reserve);
        assertEquals(expected, result);
    }

    @Test
    public void withdraw10000() {
        Map<Banknote, Integer> expected = Map.of(
                TWO_THOUSAND, 4,
                ONE_THOUSAND, 2
        );
        Map<Banknote, Integer> result = cashMachine.withdraw(10000, reserve);
        assertEquals(expected, result);
    }


    @Test
    public void withdraw1000000() {
        Map<Banknote, Integer> expected = Collections.emptyMap();
        Map<Banknote, Integer> result = cashMachine.withdraw(1_000_000, reserve);
        assertEquals(expected, result);
    }
}
