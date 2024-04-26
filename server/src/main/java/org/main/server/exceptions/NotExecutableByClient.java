package org.main.server.exceptions;

public class NotExecutableByClient extends RuntimeException{
    public NotExecutableByClient() {
        super("Can't execute by client");
    }
}
