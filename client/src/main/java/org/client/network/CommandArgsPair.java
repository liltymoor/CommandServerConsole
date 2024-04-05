package org.client.network;

import org.client.commands.Command;

public class CommandArgsPair {
    Class<? extends Command> commandClass;
    Object[] args;
    public CommandArgsPair(Class<? extends Command> commandClass, Object[] args) {
        this.commandClass = commandClass;
        this.args = args;
    }

    public Class<? extends Command> getCommandClass() {
        return commandClass;
    }

    public Object[] getArgs() {
        return args;
    }
}