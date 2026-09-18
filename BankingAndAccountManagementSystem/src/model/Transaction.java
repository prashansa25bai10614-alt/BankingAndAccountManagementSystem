package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction implements Serializable {

    private static final long serialVersionUID = 1L;

    private String transactionId;
    private String accountNumber;
    private String type;
    private double amount;
    private String description;
    private String dateTime;

    public Transaction(String transactionId, String accountNumber,
                       String type, double amount, String description) {

        this.transactionId = transactionId;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.description = description;

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        this.dateTime = LocalDateTime.now().format(formatter);
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getDateTime() {
        return dateTime;
    }

    @Override
    public String toString() {
        return "Transaction ID: " + transactionId +
                " | Account: " + accountNumber +
                " | Type: " + type +
                " | Amount: ₹" + String.format("%.2f", amount) +
                " | Date: " + dateTime +
                " | " + description;
    }
}