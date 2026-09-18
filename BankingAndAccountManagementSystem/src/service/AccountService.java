package service;

import exception.AccountNotFoundException;
import model.Account;
import model.CurrentAccount;
import model.SavingsAccount;
import model.Customer;
import repository.FileRepository;
import util.InputValidator;

import java.util.ArrayList;
import java.util.List;

public class AccountService {

    private static final String ACCOUNT_FILE = "data/accounts.dat";
    private static final String CUSTOMER_FILE = "data/customers.dat";

    private List<Account> accounts;
    private List<Customer> customers;

    public AccountService() {
        accounts = FileRepository.loadData(ACCOUNT_FILE);
        customers = FileRepository.loadData(CUSTOMER_FILE);
    }

    public Customer createCustomer(String name, String phone,
                                   String email, String address) {

        InputValidator.validateRequiredField(name, "Name");
        InputValidator.validateRequiredField(phone, "Phone");
        InputValidator.validateRequiredField(email, "Email");
        InputValidator.validateRequiredField(address, "Address");

        if (!InputValidator.isValidPhone(phone)) {
            throw new IllegalArgumentException(
                    "Phone number must contain exactly 10 digits.");
        }

        if (!InputValidator.isValidEmail(email)) {
            throw new IllegalArgumentException(
                    "Invalid email address.");
        }

        String customerId = generateCustomerId();

        Customer customer = new Customer(
                customerId, name, phone, email, address);

        customers.add(customer);
        saveCustomers();

        return customer;
    }

    public Account createAccount(String customerId,
                                 String accountType,
                                 double initialDeposit) {

        InputValidator.validateRequiredField(customerId, "Customer ID");
        InputValidator.validateRequiredField(accountType, "Account Type");
        InputValidator.validateAmount(initialDeposit);

        findCustomer(customerId);

        String accountNumber = generateAccountNumber();

        Account account;

        if (accountType.equalsIgnoreCase("savings")) {

            if (initialDeposit < 500) {
                throw new IllegalArgumentException(
                        "Savings account requires minimum initial deposit of ₹500.");
            }

            account = new SavingsAccount(
                    accountNumber, customerId, initialDeposit);

        } else if (accountType.equalsIgnoreCase("current")) {

            account = new CurrentAccount(
                    accountNumber, customerId, initialDeposit);

        } else {
            throw new IllegalArgumentException(
                    "Invalid account type. Choose Savings or Current.");
        }

        accounts.add(account);
        saveAccounts();

        return account;
    }

    public Account findAccount(String accountNumber) {

        for (Account account : accounts) {

            if (account.getAccountNumber()
                    .equalsIgnoreCase(accountNumber)) {

                return account;
            }
        }

        throw new AccountNotFoundException(
                "Account not found: " + accountNumber);
    }

    public Customer findCustomer(String customerId) {

        for (Customer customer : customers) {

            if (customer.getCustomerId()
                    .equalsIgnoreCase(customerId)) {

                return customer;
            }
        }

        throw new AccountNotFoundException(
                "Customer not found: " + customerId);
    }

    public List<Account> getAllAccounts() {
        return new ArrayList<>(accounts);
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers);
    }

    public void updateCustomer(String customerId,
                               String name,
                               String phone,
                               String email,
                               String address) {

        Customer customer = findCustomer(customerId);

        InputValidator.validateRequiredField(name, "Name");
        InputValidator.validateRequiredField(phone, "Phone");
        InputValidator.validateRequiredField(email, "Email");
        InputValidator.validateRequiredField(address, "Address");

        if (!InputValidator.isValidPhone(phone)) {
            throw new IllegalArgumentException(
                    "Phone number must contain exactly 10 digits.");
        }

        if (!InputValidator.isValidEmail(email)) {
            throw new IllegalArgumentException(
                    "Invalid email address.");
        }

        customer.setName(name);
        customer.setPhone(phone);
        customer.setEmail(email);
        customer.setAddress(address);

        saveCustomers();
    }

    public void closeAccount(String accountNumber) {

        Account account = findAccount(accountNumber);

        if (account.getBalance() != 0) {
            throw new IllegalStateException(
                    "Account can only be closed when balance is ₹0.");
        }

        account.setStatus("CLOSED");
        saveAccounts();
    }

    private String generateCustomerId() {

        int maxId = 0;

        for (Customer customer : customers) {

            String id = customer.getCustomerId();

            try {
                int number = Integer.parseInt(
                        id.substring(4));

                if (number > maxId) {
                    maxId = number;
                }

            } catch (Exception ignored) {
                // Ignore invalid stored IDs
            }
        }

        return String.format("CUS-%04d", maxId + 1);
    }

    private String generateAccountNumber() {

        long maxNumber = 1000000000L;

        for (Account account : accounts) {

            try {
                long number = Long.parseLong(
                        account.getAccountNumber());

                if (number > maxNumber) {
                    maxNumber = number;
                }

            } catch (Exception ignored) {
                // Ignore invalid stored account numbers
            }
        }

        return String.valueOf(maxNumber + 1);
    }

    private void saveAccounts() {
        FileRepository.saveData(ACCOUNT_FILE, accounts);
    }

    private void saveCustomers() {
        FileRepository.saveData(CUSTOMER_FILE, customers);
    }
}