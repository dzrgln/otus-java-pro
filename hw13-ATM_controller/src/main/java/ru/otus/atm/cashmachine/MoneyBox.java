package ru.otus.atm.cashmachine;


import ru.otus.atm.model.Banknote;

public class MoneyBox {
    private final Banknote banknote;
    MoneyBox next;

    public MoneyBox(MoneyBox next, Banknote banknote) {
        this.banknote = banknote;
        this.next = next;
    }

    public WithdrawalContext withdraw(WithdrawalContext context) {
        if (context.possibleWithdrawal) {
            withdrawSpecificBanknote(context);
            if (context.successfulWithdrawal) {
                return context;
            } else {
                return next.withdraw(context);
            }
        } else {
            return context;
        }
    }

    private void withdrawSpecificBanknote(WithdrawalContext context) {
        int reserveSumOfBanknote = context.reserve.get(banknote);
        if (reserveSumOfBanknote != 0) {
            int availableSumOfMoney = reserveSumOfBanknote * banknote.getDenomination();
            int withdrawingQuantity = Math.min(context.requiredSum, availableSumOfMoney) / banknote.getDenomination();
            if (withdrawingQuantity != 0) {
                modifyContext(context, withdrawingQuantity);
            }
        } else {
            return;
        }
        if (context.requiredSum == 0) {
            context.successfulWithdrawal = true;
        }
    }

    private void modifyContext(WithdrawalContext context, int withdrawingQuantity) {
        context.reserve.computeIfPresent(banknote, (k, v) -> v - withdrawingQuantity);
        context.result.put(banknote, withdrawingQuantity);
        context.requiredSum -= withdrawingQuantity * banknote.getDenomination();
    }
}

