package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.properties.HostActionable;
import org.main.server.exceptions.WrongArgException;
import org.main.server.fs.CollectionIO;

public class RemoveCommand extends UserClientCommand implements HostActionable {
    CollectionIO collection;
    public RemoveCommand(CollectionIO collection) {
        super("remove", "Команда удаляет элемент из коллекци по ID.");
        this.collection = collection;
    }

    @Override
    public CommandResult action(Object[] params, String username) {
        int idToRemove = (int) params[0];
        if(collection.removeFromCollection(idToRemove, username))
            return new CommandResult(ActionCode.OK);

        return new CommandResult(ActionCode.BAD_INPUT, "Entity wasn't found");
    }

    @Override
    public CommandResult hostAction(String[] params) {
        if (params.length != 1)
            return new CommandResult(ActionCode.ERROR, new WrongArgException().getMessage());
        int id;
        try {
            id = Integer.parseInt(params[0]);
        } catch (Exception ex) {
            return new CommandResult(ActionCode.BAD_INPUT, String.format("Something went wrong (%s)", ex.getMessage()));
        }
        return action(new Object[]{id}, "admin");
    }

    @Override
    public CommandResult hostAction(Object[] params) {
        return action(params, "admin");
    }
}
