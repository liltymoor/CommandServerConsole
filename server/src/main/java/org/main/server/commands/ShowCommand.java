package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.properties.HostActionable;
import org.main.server.fs.CollectionIO;

public class ShowCommand extends ClientCommand implements HostActionable {
    CollectionIO collection;
    public ShowCommand(CollectionIO collection) {
        super("show", "Команда для вывода в терминал элементов коллекции.");
        this.collection = collection;
    }

    @Override
    public CommandResult action(Object[] params) {
        return new CommandResult(ActionCode.OK, "\n" + collection.printCollection());
    }

    @Override
    public CommandResult hostAction(String[] params) {
        return action(params);
    }

    @Override
    public CommandResult hostAction(Object[] params) {
        return action(params);
    }
}
