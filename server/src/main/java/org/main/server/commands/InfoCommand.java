package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.exceptions.WrongArgException;
import org.main.server.fs.CollectionIO;

public class InfoCommand extends Command{
    CollectionIO collectionIO;
    public InfoCommand(CollectionIO collection) {
        super("info", "Команда для вывода в терминал информации о коллекции.");
        collectionIO = collection;
    }

    @Override
    public CommandResult action(String[] params) {
        try {
            if (params.length > 0)
                throw new WrongArgException();
        } catch (WrongArgException ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "No args must be provided.");
        }
        System.out.printf("Размер: %d, Тип: LinkedHashSet, Название коллекции: %s%n", collectionIO.size(), collectionIO.getCollectionName());
        return new CommandResult(ActionCode.OK);
    }
}
