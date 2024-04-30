package org.client.commands;

import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.exceptions.WrongArgException;
import org.client.network.ClientUDP;

public class ShowCommand extends ServerCommand {
    public ShowCommand(ClientUDP client) {
        super("show", "Команда для вывода в терминал элементов коллекции.", client);
    }

    @Override
    public CommandResult action(Object[] params) {
        try {
            if (params.length > 0)
                throw new WrongArgException();
        } catch (WrongArgException ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "No args must be provided.");
        }

        return sendCommand(new Object[] {});
    }
}
