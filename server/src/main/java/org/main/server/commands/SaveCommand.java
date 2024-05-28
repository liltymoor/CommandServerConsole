package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.types.HostCommand;
import org.main.server.exceptions.WrongArgException;
import org.main.server.fs.CollectionIO;

public class SaveCommand extends HostCommand {
    CollectionIO collection;
    public SaveCommand(CollectionIO collection) {
        super("save", "Команда для сохранения коллекции в файл.");
        this.collection = collection;
    }

    @Override
    public CommandResult hostAction(String[] params) {
        if (params.length > 0)
            throw new WrongArgException();

        return new CommandResult(ActionCode.UNKNOWN_ERROR, "Something went wrong while saving collection.");
    }

    @Override
    public CommandResult hostAction(Object[] params) {

        try {
            if (params.length > 0)
                throw new WrongArgException();
        } catch (WrongArgException ex) {
            return new CommandResult(ActionCode.BAD_INPUT, String.format("Something went wrong (%s)", ex.getMessage()));
        }

        return new CommandResult(ActionCode.UNKNOWN_ERROR, "Something went wrong while saving collection.");
    }
}
