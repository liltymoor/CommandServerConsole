package org.client.commands;

import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.exceptions.WrongArgException;
import org.client.network.ClientUDP;


public class RemoveCommand extends ServerCommand{
    public RemoveCommand(ClientUDP client) {
        super("remove", "Команда удаляет элемент из коллекци по ID.", client);
    }

    @Override
    public CommandResult action(Object[] params) {
        try {
            if (params.length != 1)
                throw new WrongArgException();
        } catch (WrongArgException ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong amount of arguments were passed.");
        }

        int idToRemove = Integer.parseInt((String) params[0]);

        return sendCommand(new Object[] {idToRemove});

    }
}
