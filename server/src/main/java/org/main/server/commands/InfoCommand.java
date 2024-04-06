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
    public CommandResult action(Object[] params) {
        String result = String.format("Размер: %d, Тип: LinkedHashSet, Название коллекции: %s%n", collectionIO.size(), collectionIO.getCollectionName());
        return new CommandResult(ActionCode.OK, result);
    }
}
