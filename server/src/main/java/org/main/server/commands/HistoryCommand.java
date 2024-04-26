package org.main.server.commands;

import org.main.server.commands.managers.CommandHost;
import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.properties.HostActionable;

public class HistoryCommand extends ClientCommand implements HostActionable {
    CommandHost host;
    public HistoryCommand(CommandHost host) {
        super("history", "Команда для просмотра истории команд");
        this.host = host;
    }

    @Override
    public CommandResult action(Object[] params) {
        StringBuilder sb = new StringBuilder();
        for (String command : host.getHistory(5))
            sb.append(command).append(" ");

        return new CommandResult(ActionCode.OK, sb.toString());
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
