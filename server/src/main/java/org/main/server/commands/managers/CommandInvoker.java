package org.main.server.commands.managers;

import org.main.server.commands.ClientCommand;
import org.main.server.commands.Command;
import org.main.server.commands.HostCommand;
import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.ClientActionable;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.properties.HostActionable;
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

    public CommandResult invoke(String commandToInvoke , Object[] params) {
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
        return command.action(params);
    }

    public CommandResult invoke(String commandToInvoke) throws CommandNotFoundException {
        if (commandToInvoke == null)
            return new CommandResult(ActionCode.ERROR, new CommandNotFoundException().getMessage());
        ClientActionable command;
        try {
            command = (ClientActionable) fetchCommand(commandToInvoke);
        } catch (Exception ex) {
            return new CommandResult(ActionCode.ERROR, new NotExecutableByClient().getMessage());
        }
        host.appendHistory(command);
        return command.action(new Object[] {});

    }

    public CommandResult invoke(ClientActionable commandToInvoke , Object[] params) throws CommandNotFoundException {
        if (commandToInvoke == null)
            return new CommandResult(ActionCode.ERROR, new CommandNotFoundException().getMessage());
        host.appendHistory(commandToInvoke);
        return commandToInvoke.action(params);
    }

    public CommandResult invoke(ClientActionable commandToInvoke) {
        if (commandToInvoke == null)
            return new CommandResult(ActionCode.ERROR, new CommandNotFoundException().getMessage());
        host.appendHistory(commandToInvoke);
        return commandToInvoke.action(new Object[] {});

    }

    public CommandResult invokeHost(HostActionable commandToInvoke) throws NotExecutableByHostException {
        if (commandToInvoke == null)
            return new CommandResult(ActionCode.ERROR, new CommandNotFoundException().getMessage());
        host.appendHistory(commandToInvoke);
        return commandToInvoke.hostAction(new String[] {});
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
