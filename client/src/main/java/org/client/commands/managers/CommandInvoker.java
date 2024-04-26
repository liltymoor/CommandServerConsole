package org.client.commands.managers;

import org.client.commands.Command;
import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.exceptions.CommandNotFoundException;

/**
 * @author lil_timmie
 * Класс-исполнитель команд.
 */
public class CommandInvoker {
    CommandHost host;
    public CommandInvoker(CommandHost host) {
        this.host = host;
    }

    public CommandResult invoke(Command commandToInvoke , Object[] params) throws CommandNotFoundException {
        if (commandToInvoke == null)
            throw new CommandNotFoundException();
        host.appendHistory(commandToInvoke);
        CommandResult result = commandToInvoke.action(params);
        System.out.printf("[CLIENT] %s%n", result.toString());
        return result;
    }

    public CommandResult invoke(Command commandToInvoke) {
        if (commandToInvoke == null)
            throw new CommandNotFoundException();
        host.appendHistory(commandToInvoke);
        CommandResult result = commandToInvoke.action(new String[] {});
        System.out.printf("[CLIENT] %s%n", result.toString());
        return result;
    }
}
