package model;

import exception.InsufficientBalanceException;

public class SavingsAccount extends Account {

    private static final long serialVersionUID = 1L;

    private static final double MINIMUM_BALANCE = 500.0;

    public SavingsAccount(String accountNumber, String customerId, double balance) {
        super(accountNumber, customerId, balance);
    }

    @Override
    public void withdraw(double amount) {

        if (balance - amount < MINIMUM_BALANCE) {
            throw new InsufficientBalanceException(
                    "Withdrawal denied. Savings account must maintain minimum balance of ₹"
                            + MINIMUM_BALANCE);
        }

        balance -= amount;
    }

    @Override
    public String getAccountType() {
        return "Savings";
    }
}