package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.exceptions.WrongArgException;
import org.main.server.fs.CollectionIO;

public class ShowCommand extends Command{
    CollectionIO collection;
    public ShowCommand(CollectionIO collection) {
        super("show", "Команда для вывода в терминал элементов коллекции.");
        this.collection = collection;
    }

    @Override
    public CommandResult action(String[] params) {
        try {
            if (params.length > 0)
                throw new WrongArgException();
        } catch (WrongArgException ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "No args must be provided.");
        }

        collection.forEach(System.out::println);
        return new CommandResult(ActionCode.OK);
    }
}
