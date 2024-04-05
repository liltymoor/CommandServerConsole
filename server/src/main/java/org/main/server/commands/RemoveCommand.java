package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.exceptions.WrongArgException;
import org.main.server.fs.CollectionIO;

public class RemoveCommand extends Command{
    CollectionIO collection;
    public RemoveCommand(CollectionIO collection) {
        super("remove", "Команда удаляет элемент из коллекци по ID.");
        this.collection = collection;
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
        if(collection.removeFromCollection(idToRemove))
            return new CommandResult(ActionCode.OK);

        return new CommandResult(ActionCode.BAD_INPUT, "Entity wasn't found");
    }
}
