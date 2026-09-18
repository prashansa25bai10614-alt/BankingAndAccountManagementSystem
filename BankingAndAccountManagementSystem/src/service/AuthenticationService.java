package service;

import model.Customer;

public class AuthenticationService {

    private AccountService accountService;

    public AuthenticationService(AccountService accountService) {
        this.accountService = accountService;
    }

    public Customer login(String customerId) {

        if (customerId == null || customerId.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "Customer ID cannot be empty.");
        }

        return accountService.findCustomer(customerId);
    }

    public boolean authenticateAdmin(String username, String password) {

        return username.equals("admin") &&
               password.equals("admin123");
    }

    public boolean customerExists(String customerId) {

        try {
            accountService.findCustomer(customerId);
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}