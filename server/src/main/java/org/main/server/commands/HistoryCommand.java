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
    public CommandResult action(String[] params) {
        try {
            if (params.length != 0)
                throw new WrongArgException();
        } catch (WrongArgException ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong amount of arguments were passed.");
        }

        for (String command : host.getHistory(5))
            System.out.println(command);

        return new CommandResult(ActionCode.OK);
    }
}
