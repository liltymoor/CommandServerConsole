package org.client.commands;

import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.exceptions.WrongArgException;


public class RemoveCommand extends Command{
    public RemoveCommand() {
        super("remove", "Команда удаляет элемент из коллекци по ID.");
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

        return new CommandResult(ActionCode.BAD_INPUT, "Entity wasn't found");
    }
}
