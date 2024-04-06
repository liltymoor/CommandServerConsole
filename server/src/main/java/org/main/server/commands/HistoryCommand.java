package org.main.server.commands;

import org.main.server.commands.managers.CommandHost;
import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.exceptions.WrongArgException;

public class HistoryCommand extends Command {
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
}
