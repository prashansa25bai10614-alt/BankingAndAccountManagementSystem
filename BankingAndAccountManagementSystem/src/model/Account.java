package model;

import java.io.Serializable;

public abstract class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String accountNumber;
    protected String customerId;
    protected double balance;
    protected String status;

    public Account(String accountNumber, String customerId, double balance) {
        this.accountNumber = accountNumber;
        this.customerId = customerId;
        this.balance = balance;
        this.status = "ACTIVE";
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getCustomerId() {
        return customerId;
    }

    public double getBalance() {
        return balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public abstract void withdraw(double amount);

    public abstract String getAccountType();

    @Override
    public String toString() {
        return "Account Number: " + accountNumber +
                "\nCustomer ID: " + customerId +
                "\nAccount Type: " + getAccountType() +
                "\nBalance: ₹" + String.format("%.2f", balance) +
                "\nStatus: " + status;
    }
}