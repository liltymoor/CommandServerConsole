package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.properties.HostActionable;
import org.main.server.commands.types.UserClientCommand;
import org.main.server.fs.CollectionIO;

public class InfoCommand extends UserClientCommand implements HostActionable {
    CollectionIO collectionIO;
    public InfoCommand(CollectionIO collection) {
        super("info", "Команда для вывода в терминал информации о коллекции.");
        collectionIO = collection;
    }

    @Override
    public CommandResult action(Object[] params, String username) {
        String result = String.format("Размер: %d, Тип: LinkedHashSet, Название коллекции: PostgreSQL%n", collectionIO.size());
        return new CommandResult(ActionCode.OK, result);
    }

    @Override
    public CommandResult hostAction(String[] params) {
        return action(params, "admin");
    }

    @Override
    public CommandResult hostAction(Object[] params) {
        return action(params, "admin");
    }
}
