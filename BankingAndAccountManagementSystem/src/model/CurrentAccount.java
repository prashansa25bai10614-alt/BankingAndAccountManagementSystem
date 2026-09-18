package model;

import exception.InsufficientBalanceException;

public class CurrentAccount extends Account {

    private static final long serialVersionUID = 1L;

    private static final double OVERDRAFT_LIMIT = 5000.0;

    public CurrentAccount(String accountNumber, String customerId, double balance) {
        super(accountNumber, customerId, balance);
    }

    @Override
    public void withdraw(double amount) {

        if (balance - amount < -OVERDRAFT_LIMIT) {
            throw new InsufficientBalanceException(
                    "Withdrawal denied. Overdraft limit of ₹"
                            + OVERDRAFT_LIMIT + " exceeded.");
        }

        balance -= amount;
    }

    @Override
    public String getAccountType() {
        return "Current";
    }
}