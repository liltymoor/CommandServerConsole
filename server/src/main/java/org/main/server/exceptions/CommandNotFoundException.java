package org.main.server.exceptions;

public class CommandNotFoundException extends RuntimeException {
    public CommandNotFoundException() {
        super("Command wasn't found. Type 'help' to get list of all avaliable commands.");
    }
}
