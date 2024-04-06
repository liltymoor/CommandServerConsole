package org.client.commands;

import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.exceptions.WrongArgException;
import org.client.network.Client;


public class RemoveCommand extends ServerCommand{
    public RemoveCommand(Client client) {
        super("remove", "Команда удаляет элемент из коллекци по ID.", client);
    }

    @Override
    public CommandResult action(String[] params) {
        try {
            if (params.length != 1)
                throw new WrongArgException();
        } catch (WrongArgException ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong amount of arguments were passed.");
        }

        int idToRemove = Integer.parseInt(params[0]);
//        if(collection.removeFromCollection(idToRemove))
//            return new CommandResult(ActionCode.OK);

        return sendCommand(new Object[] {idToRemove});

    }
}
