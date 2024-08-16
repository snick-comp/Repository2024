package com.projectmvc.validations;


public class Validation {

    public static void validateEmailAddress(final String emailAddress) {
        if (emailAddress == null || !isValidEmail(emailAddress)) {
            throw new ValidationException("Invalid email address format.");
        }
    }

    public static void validatePassword(final String password) {
        if (password == null || !isValidPassword(password)) {
            throw new ValidationException("Password must be at least 8 characters long and contain both letters and numbers.");
        }
    }

    private static boolean isValidEmail(final String email) {
        int atPosition = email.indexOf('@');
        int dotPosition = email.lastIndexOf('.');
        return atPosition > 0 && dotPosition > atPosition + 1 && dotPosition < email.length() - 1 && !email.contains(" ");
    }

    private static boolean isValidPassword(final String password) {
        if (password.length() < 8) {
            return false;
        }

        boolean hasLetter = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            }

            if (hasLetter && hasDigit) {
                return true;
            }
        }

        return false;
    }
}
