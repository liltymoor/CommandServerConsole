package org.main.server.commands.managers;

import org.main.server.commands.Command;
import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.exceptions.CommandNotFoundException;

/**
 * @author lil_timmie
 * Класс-исполнитель команд.
 */
public class CommandInvoker {
    CommandHost host;
    public CommandInvoker(CommandHost host) {
        this.host = host;
    }

    private Command fetchCommand(String commandName) throws CommandNotFoundException {
        Command command = host.getCommands().get(commandName);
        if (command == null)
            throw new CommandNotFoundException();
        return command;
    }

    public CommandResult invoke(String commandToInvoke , Object[] params) throws CommandNotFoundException {
        if (commandToInvoke == null)
            throw new CommandNotFoundException();
        Command command = fetchCommand(commandToInvoke);
        host.appendHistory(command);
        return command.action(params);
    }

    public CommandResult invoke(String commandToInvoke) {
        if (commandToInvoke == null)
            throw new CommandNotFoundException();
        Command command = fetchCommand(commandToInvoke);
        host.appendHistory(command);
        return command.action(new Object[] {});

    }

    public CommandResult invoke(Command commandToInvoke , Object[] params) throws CommandNotFoundException {
        if (commandToInvoke == null)
            throw new CommandNotFoundException();
        host.appendHistory(commandToInvoke);
        return commandToInvoke.action(params);
    }

    public CommandResult invoke(Command commandToInvoke) {
        if (commandToInvoke == null)
            throw new CommandNotFoundException();
        host.appendHistory(commandToInvoke);
        return commandToInvoke.action(new Object[] {});

    }
}
