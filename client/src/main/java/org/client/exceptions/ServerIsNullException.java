package org.client.exceptions;

public class ServerIsNullException extends RuntimeException{
    public ServerIsNullException() {
        super("Server was null");
    }
}
