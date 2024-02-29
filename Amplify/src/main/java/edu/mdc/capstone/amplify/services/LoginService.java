package edu.mdc.capstone.amplify.services;

import java.util.HashMap;
import java.util.Map;

public class LoginService {
    private static final String validUsername = "user123";
    private static final String validPassword = "pass456";

    public static Map<String, String> validateLogin(String submittedUsername, String submittedPassword) {
        Map<String, String> validationErrors = new HashMap<>();

        if (submittedUsername == null || submittedUsername.trim().isEmpty()) {
            validationErrors.put("username", "Username is required");
        }

        if (submittedPassword == null || submittedPassword.trim().isEmpty()) {
            validationErrors.put("password", "Password is required");
        }

        if (!validUsername.equals(submittedUsername) || !validPassword.equals(submittedPassword)) {
            validationErrors.put("login", "Invalid username or password");
        }

        return validationErrors;
    }
}
