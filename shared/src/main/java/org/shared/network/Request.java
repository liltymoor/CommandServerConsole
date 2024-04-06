package org.shared.network;

import java.io.Serializable;

public class Request implements Serializable {
    public static final int REQUEST_SIZE = 2048;
    private String commandName;
    private Object[] args;
    public Request(String command, Object[] args) {
        commandName = command;
        this.args = args;
    }

    public String getCommandName() {
        return commandName;
    }
    public Object[] getArgs() {
        return args;
    }
}