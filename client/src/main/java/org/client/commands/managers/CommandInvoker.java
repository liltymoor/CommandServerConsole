package org.client.commands.managers;

import org.client.commands.properties.DataProvidedCommandResult;
import org.client.commands.types.Command;
import org.client.commands.properties.CommandResult;
import org.client.commands.types.DataProvidableServerCommand;
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
        System.out.println("[CLIENT] Executing \"%s\" command".formatted(commandToInvoke.getName()));
        CommandResult result = commandToInvoke.action(params);
        System.out.printf("[CLIENT] %s%n%n", result.toString());
        return result;
    }

    public CommandResult invoke(Command commandToInvoke) {
        if (commandToInvoke == null)
            throw new CommandNotFoundException();
        host.appendHistory(commandToInvoke);
        System.out.println("[CLIENT] Executing \"%s\" command".formatted(commandToInvoke.getName()));
        CommandResult result = commandToInvoke.action(new String[] {});
        System.out.printf("[CLIENT] %s%n%n", result.toString());
        return result;
    }

    public <T> DataProvidedCommandResult<T> call(DataProvidableServerCommand<T> commandToInvoke, Object[] params) {
        if (commandToInvoke == null)
            throw new CommandNotFoundException();
        host.appendHistory(commandToInvoke);
        System.out.println("[CLIENT] Executing \"%s\" command".formatted(commandToInvoke.getName()));
        DataProvidedCommandResult<T> result = commandToInvoke.action(new String[] {});
        System.out.printf("[CLIENT] %s%n%n", result.toString());
        return result;
    }

    public <T> DataProvidedCommandResult<T> call(DataProvidableServerCommand<T> commandToInvoke) {
        if (commandToInvoke == null)
            throw new CommandNotFoundException();
        host.appendHistory(commandToInvoke);
        System.out.println("[CLIENT] Executing \"%s\" command".formatted(commandToInvoke.getName()));
        DataProvidedCommandResult<T> result = commandToInvoke.action(new String[] {});
        System.out.printf("[CLIENT] %s%n%n", result.toString());
        return result;
    }
}
