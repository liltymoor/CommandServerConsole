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

    public void invoke(Command commandToInvoke , String[] params) throws CommandNotFoundException {
        if (commandToInvoke == null)
            throw new CommandNotFoundException();
        host.appendHistory(commandToInvoke);
        CommandResult result = commandToInvoke.action(params);

        if (result.getCode() != ActionCode.OK) {
            System.out.println(result);
            System.out.println("Please try again.");
        }
    }

    public void invoke(Command commandToInvoke) {
        if (commandToInvoke == null)
            throw new CommandNotFoundException();
        host.appendHistory(commandToInvoke);
        commandToInvoke.action(new String[] {});
    }
}
