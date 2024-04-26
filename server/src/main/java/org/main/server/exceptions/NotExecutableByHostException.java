package org.main.server.exceptions;

public class NotExecutableByHostException extends RuntimeException{
    public NotExecutableByHostException() {
        super("Can't execute by host");
    }

}
