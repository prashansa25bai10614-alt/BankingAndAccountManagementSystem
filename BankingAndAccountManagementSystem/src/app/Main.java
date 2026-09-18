package app;

import model.Account;
import model.Customer;
import model.Transaction;
import service.AccountService;
import service.AuthenticationService;
import service.TransactionService;

import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);

    private static AccountService accountService;
    private static TransactionService transactionService;
    private static AuthenticationService authenticationService;

    public static void main(String[] args) {

        accountService = new AccountService();
        transactionService = new TransactionService(accountService);
        authenticationService = new AuthenticationService(accountService);

        System.out.println("========================================");
        System.out.println("   BANKING AND ACCOUNT MANAGEMENT SYSTEM");
        System.out.println("========================================");

        while (true) {

            System.out.println("\n1. Customer");
            System.out.println("2. Admin");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":
                    customerMenu();
                    break;

                case "2":
                    adminLogin();
                    break;

                case "3":
                    System.out.println("Thank you for using the Banking System.");
                    return;

                default:
                    System.out.println("Invalid choice.");
            }
        }
    }

    private static void customerMenu() {

        while (true) {

            System.out.println("\n========== CUSTOMER MENU ==========");
            System.out.println("1. Create Customer");
            System.out.println("2. Login");
            System.out.println("3. Create Bank Account");
            System.out.println("4. Check Balance");
            System.out.println("5. Deposit Money");
            System.out.println("6. Withdraw Money");
            System.out.println("7. Transfer Money");
            System.out.println("8. Transaction History");
            System.out.println("9. Update Customer Profile");
            System.out.println("10. Close Account");
            System.out.println("11. Back");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            try {

                switch (choice) {

                    case "1":
                        createCustomer();
                        break;

                    case "2":
                        customerLogin();
                        break;

                    case "3":
                        createAccount();
                        break;

                    case "4":
                        checkBalance();
                        break;

                    case "5":
                        deposit();
                        break;

                    case "6":
                        withdraw();
                        break;

                    case "7":
                        transfer();
                        break;

                    case "8":
                        transactionHistory();
                        break;

                    case "9":
                        updateCustomer();
                        break;

                    case "10":
                        closeAccount();
                        break;

                    case "11":
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    private static void createCustomer() {

        System.out.println("\n------ Create Customer ------");

        System.out.print("Enter Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Phone: ");
        String phone = scanner.nextLine();

        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter Address: ");
        String address = scanner.nextLine();

        Customer customer = accountService.createCustomer(
                name, phone, email, address);

        System.out.println("\nCustomer created successfully!");
        System.out.println("Your Customer ID: "
                + customer.getCustomerId());
    }

    private static void customerLogin() {

        System.out.println("\n------ Customer Login ------");

        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();

        Customer customer =
                authenticationService.login(customerId);

        System.out.println("\nLogin successful!");
        System.out.println("Welcome, " + customer.getName());
    }

    private static void createAccount() {

        System.out.println("\n------ Create Bank Account ------");

        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();

        System.out.print("Enter Account Type (Savings/Current): ");
        String accountType = scanner.nextLine();

        System.out.print("Enter Initial Deposit: ");
        double amount = Double.parseDouble(scanner.nextLine());

        Account account = accountService.createAccount(
                customerId, accountType, amount);

        System.out.println("\nAccount created successfully!");
        System.out.println("Account Number: "
                + account.getAccountNumber());
        System.out.println("Account Type: "
                + account.getAccountType());
        System.out.println("Balance: ₹"
                + String.format("%.2f", account.getBalance()));
    }

    private static void checkBalance() {

        System.out.println("\n------ Balance Inquiry ------");

        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        double balance =
                transactionService.checkBalance(accountNumber);

        System.out.println("Current Balance: ₹"
                + String.format("%.2f", balance));
    }

    private static void deposit() {

        System.out.println("\n------ Deposit Money ------");

        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Enter Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        transactionService.deposit(accountNumber, amount);

        System.out.println("Deposit successful!");
    }

    private static void withdraw() {

        System.out.println("\n------ Withdraw Money ------");

        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        System.out.print("Enter Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        transactionService.withdraw(accountNumber, amount);

        System.out.println("Withdrawal successful!");
    }

    private static void transfer() {

        System.out.println("\n------ Transfer Money ------");

        System.out.print("Enter Source Account Number: ");
        String fromAccount = scanner.nextLine();

        System.out.print("Enter Destination Account Number: ");
        String toAccount = scanner.nextLine();

        System.out.print("Enter Amount: ");
        double amount = Double.parseDouble(scanner.nextLine());

        transactionService.transfer(
                fromAccount, toAccount, amount);

        System.out.println("Transfer successful!");
    }

    private static void transactionHistory() {

        System.out.println("\n------ Transaction History ------");

        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        List<Transaction> history =
                transactionService.getTransactionHistory(accountNumber);

        if (history.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        for (Transaction transaction : history) {
            System.out.println(transaction);
        }
    }

    private static void updateCustomer() {

        System.out.println("\n------ Update Customer Profile ------");

        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();

        System.out.print("Enter New Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter New Phone: ");
        String phone = scanner.nextLine();

        System.out.print("Enter New Email: ");
        String email = scanner.nextLine();

        System.out.print("Enter New Address: ");
        String address = scanner.nextLine();

        accountService.updateCustomer(
                customerId, name, phone, email, address);

        System.out.println("Customer profile updated successfully!");
    }

    private static void closeAccount() {

        System.out.println("\n------ Close Account ------");

        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        accountService.closeAccount(accountNumber);

        System.out.println("Account closed successfully!");
    }

    private static void adminLogin() {

        System.out.println("\n========== ADMIN LOGIN ==========");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        if (authenticationService.authenticateAdmin(
                username, password)) {

            System.out.println("Admin login successful!");
            adminMenu();

        } else {
            System.out.println("Invalid username or password.");
        }
    }

    private static void adminMenu() {

        while (true) {

            System.out.println("\n========== ADMIN MENU ==========");
            System.out.println("1. View All Accounts");
            System.out.println("2. Search Account");
            System.out.println("3. Search Customer");
            System.out.println("4. View Account Details");
            System.out.println("5. View All Transaction Records");
            System.out.println("6. Logout");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            try {

                switch (choice) {

                    case "1":
                        viewAllAccounts();
                        break;

                    case "2":
                        searchAccount();
                        break;

                    case "3":
                        searchCustomer();
                        break;

                    case "4":
                        viewAccountDetails();
                        break;

                    case "5":
                        viewAllTransactions();
                        break;

                    case "6":
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
            }
        }
    }

    private static void viewAllAccounts() {

        List<Account> accounts =
                accountService.getAllAccounts();

        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }

        for (Account account : accounts) {
            System.out.println("\n" + account);
            System.out.println("--------------------------------");
        }
    }

    private static void searchAccount() {

        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        Account account =
                accountService.findAccount(accountNumber);

        System.out.println("\n" + account);
    }

    private static void searchCustomer() {

        System.out.print("Enter Customer ID: ");
        String customerId = scanner.nextLine();

        Customer customer =
                accountService.findCustomer(customerId);

        System.out.println("\n" + customer);
    }

    private static void viewAccountDetails() {

        System.out.print("Enter Account Number: ");
        String accountNumber = scanner.nextLine();

        Account account =
                accountService.findAccount(accountNumber);

        Customer customer =
                accountService.findCustomer(account.getCustomerId());

        System.out.println("\n========== ACCOUNT DETAILS ==========");
        System.out.println(account);
        System.out.println("\n========== CUSTOMER DETAILS ==========");
        System.out.println(customer);
    }

    private static void viewAllTransactions() {

        List<Transaction> transactions =
                transactionService.getAllTransactions();

        if (transactions.isEmpty()) {
            System.out.println("No transactions found.");
            return;
        }

        for (Transaction transaction : transactions) {
            System.out.println(transaction);
        }
    }
}