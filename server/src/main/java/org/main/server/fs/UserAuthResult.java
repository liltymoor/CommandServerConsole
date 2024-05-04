package org.main.server.fs;

public enum UserAuthResult {
    EMPTY_FIELDS("Given fields are empty."),
    NOT_EXISTS("Given username doesn't exist."),
    WRONG_PASSWORD("Wrong password."),
    SUCCESS("Success."),
    UNKNOWN_ERROR("Unknown error.");

    private final String resultDescription;
    UserAuthResult(String description) {
        resultDescription = description;
    }

    public String getDescription() {
        return resultDescription;
    }
}
