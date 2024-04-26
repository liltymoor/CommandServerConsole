package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.properties.HostActionable;
import org.main.server.fs.CollectionIO;

public class ClearCommand extends ClientCommand implements HostActionable {
    CollectionIO collection;
    public ClearCommand(CollectionIO collection) {
        super("clear", "Команда для полной очистки коллекции.");
        this.collection = collection;
    }

    @Override
    public CommandResult action(Object[] params) {
        collection.clearCollection();
        return new CommandResult(ActionCode.OK);
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
