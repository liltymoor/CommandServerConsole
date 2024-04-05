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
