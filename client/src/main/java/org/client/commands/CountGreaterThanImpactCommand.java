package org.client.commands;

import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.network.ClientUDP;

public class CountGreaterThanImpactCommand extends ServerCommand {
    public CountGreaterThanImpactCommand(ClientUDP client) {
        super("count_greater_than_impact", "Вывести количество элементов в коллекции, которые выше указанной величины impact", client);
    }

    @Override
    public CommandResult action(Object[] params) {
        if (params.length != 1)
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong amount of arguments were passed.");

        Long impactSpeed;
        try {
            impactSpeed = Long.parseLong((String) params[0]);
        } catch (Exception ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong data were eaten by program.");
        }

        return sendCommand(new Object[]{impactSpeed});

    }
}
