package org.main.server.fs;

public enum RegistrationResult {
    USERNAME_EXISTS("This username already exists, try another one."),
    UNKNOWN_ERROR("Unknown error"),
    SUCCESS("Registration success");

    private final String resultDescription;
    RegistrationResult(String description) {
        resultDescription = description;
    }

    public String getDescription() {
        return resultDescription;
    }
}
