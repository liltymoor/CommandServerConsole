package org.client.exceptions;

public class WrongArgException extends RuntimeException {
    public WrongArgException() {
        super("Wrong argument(s) was/were passed.");
    }
}
