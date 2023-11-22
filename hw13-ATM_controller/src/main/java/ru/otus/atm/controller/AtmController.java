package ru.otus.atm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.otus.atm.model.AvailableSum;
import ru.otus.atm.model.Banknote;
import ru.otus.atm.service.ATM;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/atm")
public class AtmController {

    private final ATM atm;

    @Autowired
    public AtmController(ATM atm) {
        this.atm = atm;
    }

    @GetMapping
    public AvailableSum getAvailableSum() {
        log.info("Получен запрос на баланс банкомата");
        return new AvailableSum(atm.getAvailableSum());
    }

    @GetMapping("/{quantity}")
    public Map<Banknote, Integer> withdrawMoney(@PathVariable int quantity) {
        log.info("Получен запрос на снятие {} рублей", quantity);
        return atm.withdrawMoney(quantity);
    }

    @PostMapping
    public Map<Banknote, Integer> putMoney(@RequestBody Map<Banknote, Integer> money) {
        log.info("Получен запрос на внесение {}", moneyAsString(money));
        return atm.putMoney(money);
    }

    private String moneyAsString(Map<Banknote, Integer> money) {
        return money.entrySet().stream()
                .map(es -> es.getKey().getDenomination() + "₽ - " + es.getValue() +"шт.")
                .collect(Collectors.joining(", "));
    }
}
