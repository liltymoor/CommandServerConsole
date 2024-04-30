package org.client.commands;

import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.network.ClientUDP;

public class InfoCommand extends ServerCommand {
    public InfoCommand(ClientUDP client) {
        super("info", "Команда для вывода в терминал информации о коллекции.", client);
    }

    @Override
    public CommandResult action(Object[] params) {
        if (params.length > 0)
            return new CommandResult(ActionCode.BAD_INPUT, "No args must be provided.");
        return sendCommand(new Object[]{});
    }
}
