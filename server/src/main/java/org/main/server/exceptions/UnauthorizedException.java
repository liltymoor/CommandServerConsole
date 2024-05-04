package org.main.server.exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException() {
        super("You must be authorized to use this.");
    }
}
