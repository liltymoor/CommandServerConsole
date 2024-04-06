package org.client.commands;

import org.client.commands.managers.CommandHost;
import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.exceptions.WrongArgException;
import org.client.network.Client;

public class HistoryCommand extends ServerCommand {
    CommandHost host;
    public HistoryCommand(CommandHost host, Client client) {
        super("history", "Команда для просмотра истории команд", client);
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

//        for (String command : host.getHistory(5))
//            System.out.println(command);

        return sendCommand(new Object[] {});
    }
}
