package service;

import exception.AccountNotFoundException;
import model.Account;
import model.Transaction;
import repository.FileRepository;
import util.InputValidator;

import java.util.ArrayList;
import java.util.List;

public class TransactionService {

    private static final String TRANSACTION_FILE = "data/transactions.dat";

    private List<Transaction> transactions;
    private AccountService accountService;

    public TransactionService(AccountService accountService) {
        this.accountService = accountService;
        transactions = FileRepository.loadData(TRANSACTION_FILE);
    }

    public void deposit(String accountNumber, double amount) {

        InputValidator.validateAmount(amount);

        Account account = accountService.findAccount(accountNumber);

        if (!account.getStatus().equalsIgnoreCase("ACTIVE")) {
            throw new IllegalStateException(
                    "Transaction denied. Account is closed.");
        }

        account.deposit(amount);

        Transaction transaction = new Transaction(
                generateTransactionId(),
                accountNumber,
                "DEPOSIT",
                amount,
                "Amount deposited");

        transactions.add(transaction);

        saveTransactions();
        saveAccounts();
    }

    public void withdraw(String accountNumber, double amount) {

        InputValidator.validateAmount(amount);

        Account account = accountService.findAccount(accountNumber);

        if (!account.getStatus().equalsIgnoreCase("ACTIVE")) {
            throw new IllegalStateException(
                    "Transaction denied. Account is closed.");
        }

        account.withdraw(amount);

        Transaction transaction = new Transaction(
                generateTransactionId(),
                accountNumber,
                "WITHDRAWAL",
                amount,
                "Amount withdrawn");

        transactions.add(transaction);

        saveTransactions();
        saveAccounts();
    }

    public void transfer(String fromAccountNumber,
                         String toAccountNumber,
                         double amount) {

        InputValidator.validateAmount(amount);

        if (fromAccountNumber.equalsIgnoreCase(toAccountNumber)) {
            throw new IllegalArgumentException(
                    "Source and destination accounts cannot be the same.");
        }

        Account fromAccount =
                accountService.findAccount(fromAccountNumber);

        Account toAccount =
                accountService.findAccount(toAccountNumber);

        if (!fromAccount.getStatus().equalsIgnoreCase("ACTIVE") ||
            !toAccount.getStatus().equalsIgnoreCase("ACTIVE")) {

            throw new IllegalStateException(
                    "Transfer denied. Both accounts must be active.");
        }

        fromAccount.withdraw(amount);
        toAccount.deposit(amount);

        String transactionId = generateTransactionId();

        Transaction senderTransaction = new Transaction(
                transactionId,
                fromAccountNumber,
                "TRANSFER",
                amount,
                "Transferred to account " + toAccountNumber);

        Transaction receiverTransaction = new Transaction(
                generateTransactionId(),
                toAccountNumber,
                "TRANSFER",
                amount,
                "Received from account " + fromAccountNumber);

        transactions.add(senderTransaction);
        transactions.add(receiverTransaction);

        saveTransactions();
        saveAccounts();
    }

    public double checkBalance(String accountNumber) {

        Account account =
                accountService.findAccount(accountNumber);

        return account.getBalance();
    }

    public List<Transaction> getTransactionHistory(String accountNumber) {

        accountService.findAccount(accountNumber);

        List<Transaction> history = new ArrayList<>();

        for (Transaction transaction : transactions) {

            if (transaction.getAccountNumber()
                    .equalsIgnoreCase(accountNumber)) {

                history.add(transaction);
            }
        }

        return history;
    }

    public List<Transaction> getAllTransactions() {
        return new ArrayList<>(transactions);
    }

    private String generateTransactionId() {

        int maxId = 0;

        for (Transaction transaction : transactions) {

            String id = transaction.getTransactionId();

            try {
                int number = Integer.parseInt(
                        id.substring(4));

                if (number > maxId) {
                    maxId = number;
                }

            } catch (Exception ignored) {
                // Ignore invalid stored transaction IDs
            }
        }

        return String.format("TXN-%04d", maxId + 1);
    }

    private void saveTransactions() {
        FileRepository.saveData(
                TRANSACTION_FILE, transactions);
    }

    private void saveAccounts() {

        FileRepository.saveData(
                "data/accounts.dat",
                accountService.getAllAccounts());
    }
}