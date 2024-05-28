package org.client.commands;

import org.client.commands.managers.CommandHost;
import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.commands.types.ServerCommand;
import org.client.network.ClientUDP;

public class HistoryCommand extends ServerCommand {
    CommandHost host;

    public HistoryCommand(CommandHost host, ClientUDP client) {
        super("history", "Команда для просмотра истории команд", client);
        this.host = host;
    }

    @Override
    public CommandResult action(Object[] params) {
        if (params.length != 0)
            return new CommandResult(ActionCode.BAD_INPUT, "No args must be provided.");

        return sendCommand(new Object[]{});
    }
}
