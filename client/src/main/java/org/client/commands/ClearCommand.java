package org.client.commands;

import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.network.ClientUDP;


public class ClearCommand extends ServerCommand {
    public ClearCommand(ClientUDP client) {
        super("clear", "Команда для полной очистки коллекции.", client);
    }

    @Override
    public CommandResult action(Object[] params) {
        if (params.length != 0)
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong amount of arguments were passed.");

        return sendCommand(new Object[]{});
    }
}
