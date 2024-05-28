package org.main.server.commands.managers;

import org.main.server.commands.types.Command;
import org.main.server.commands.properties.*;
import org.main.server.exceptions.CommandNotFoundException;
import org.main.server.exceptions.NotExecutableByClient;
import org.main.server.exceptions.NotExecutableByHostException;

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

    public CommandResult invoke(String commandToInvoke, Object[] params, String username) {
        if (commandToInvoke == null)
            return new CommandResult(ActionCode.ERROR, new CommandNotFoundException().getMessage());

        ClientActionable command;
        try {
            command = (ClientActionable) fetchCommand(commandToInvoke);
        } catch (ClassCastException ex) {
            return new CommandResult(ActionCode.ERROR, new NotExecutableByClient().getMessage());
        } catch (CommandNotFoundException ex) {
            return new CommandResult(ActionCode.ERROR, ex.getMessage());
        }
        host.appendHistory(command);
        if (username != null)
            return ((UserClientActionable) command).action(params, username);

        return command.action(params);
    }

    public CommandResult invoke(String commandToInvoke, String username) throws CommandNotFoundException {
        if (commandToInvoke == null)
            return new CommandResult(ActionCode.ERROR, new CommandNotFoundException().getMessage());
        ClientActionable command;
        try {
            command = (ClientActionable) fetchCommand(commandToInvoke);
        } catch (Exception ex) {
            return new CommandResult(ActionCode.ERROR, new NotExecutableByClient().getMessage());
        }
        host.appendHistory(command);

        if (username != null)
            return ((UserClientActionable) command).action(new Object[]{}, username);
        return command.action(new Object[]{});
    }

    public CommandResult invokeHost(String commandToInvoke) {
        try {
            return invokeHost((HostActionable) fetchCommand(commandToInvoke));
        } catch (ClassCastException ex) {
            return new CommandResult(ActionCode.ERROR, new NotExecutableByHostException().getMessage());
        }
    }

    public CommandResult invokeHost(String commandToInvoke, String[] params) {
        try {
            return invokeHost((HostActionable) fetchCommand(commandToInvoke), params);
        } catch (ClassCastException ex) {
            return new CommandResult(ActionCode.ERROR, new NotExecutableByHostException().getMessage());
        }
    }

    public CommandResult invokeHost(String commandToInvoke, Object[] params) {
        try {
            return invokeHost((HostActionable) fetchCommand(commandToInvoke), params);
        } catch (ClassCastException ex) {
            return new CommandResult(ActionCode.ERROR, new NotExecutableByHostException().getMessage());
        }
    }

    public CommandResult invokeHost(HostActionable commandToInvoke) throws NotExecutableByHostException {
        if (commandToInvoke == null)
            return new CommandResult(ActionCode.ERROR, new CommandNotFoundException().getMessage());
        host.appendHistory(commandToInvoke);
        return commandToInvoke.hostAction(new String[]{});
    }

    public CommandResult invokeHost(HostActionable commandToInvoke, String[] params) throws NotExecutableByHostException {
        if (commandToInvoke == null)
            return new CommandResult(ActionCode.ERROR, new CommandNotFoundException().getMessage());
        host.appendHistory(commandToInvoke);
        return commandToInvoke.hostAction(params);
    }

    public CommandResult invokeHost(HostActionable commandToInvoke, Object[] params) throws NotExecutableByHostException {
        if (commandToInvoke == null)
            return new CommandResult(ActionCode.ERROR, new CommandNotFoundException().getMessage());
        host.appendHistory(commandToInvoke);
        return commandToInvoke.hostAction(params);
    }
}
