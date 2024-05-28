package org.client.commands;

import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.commands.types.ServerCommand;
import org.client.network.ClientUDP;


public class PrintUniqueImpactCommand extends ServerCommand {
    public PrintUniqueImpactCommand(ClientUDP client) {
        super("print_unique_impact", "Команда для просмотра уникальных значений скорости", client);
    }

    @Override
    public CommandResult action(Object[] params) {
        if (params.length != 0)
            return new CommandResult(ActionCode.BAD_INPUT, "No args must be provided.");

        return sendCommand(new Object[]{});
    }
}
