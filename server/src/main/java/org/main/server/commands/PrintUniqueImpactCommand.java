package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.properties.HostActionable;
import org.main.server.commands.types.UserClientCommand;
import org.main.server.fs.CollectionIO;


public class PrintUniqueImpactCommand extends UserClientCommand implements HostActionable {
    CollectionIO collection;
    public PrintUniqueImpactCommand(CollectionIO collection) {
        super("print_unique_impact", "Команда для просмотра уникальных значений скорости");
        this.collection = collection;
    }

    @Override
    public CommandResult action(Object[] params, String username) {
        StringBuilder sb = new StringBuilder();
        for (Long impact : collection.getUniqueImpact())
            sb.append(impact).append(" ");

        return new CommandResult(ActionCode.OK, sb.toString());
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
