package util;

import exception.InvalidAmountException;

public class InputValidator {

    private InputValidator() {
        // Utility class
    }

    public static void validateAmount(double amount) {
        if (amount <= 0) {
            throw new InvalidAmountException(
                    "Amount must be greater than zero.");
        }
    }

    public static void validateRequiredField(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(
                    fieldName + " cannot be empty.");
        }
    }

    public static boolean isValidPhone(String phone) {
        return phone != null && phone.matches("\\d{10}");
    }

    public static boolean isValidEmail(String email) {
        return email != null &&
                email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }
}