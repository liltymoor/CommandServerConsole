package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.exceptions.WrongArgException;
import org.main.server.fs.CollectionIO;

public class SaveCommand extends Command{
    CollectionIO collection;
    public SaveCommand(CollectionIO collection) {
        super("save", "Команда для сохранения коллекции в файл.");
        this.collection = collection;
    }

    @Override
    public CommandResult action(Object[] params) {
        if (collection.flushToJson())
            return new CommandResult(ActionCode.OK);
        return new CommandResult(ActionCode.UNKNOWN_ERROR, "Something went wrong with collection.");
    }
}
