package org.client.commands;

import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.exceptions.WrongArgException;
import org.client.network.Client;
import org.client.network.NetworkByteWrapper;
import org.shared.network.Response;

import java.nio.ByteBuffer;


public class ClearCommand extends ServerCommand{
    public ClearCommand(Client client) {
        super("clear", "Команда для полной очистки коллекции.", client);
    }

    @Override
    public CommandResult action(String[] params) {
        try {
            if (params.length != 0)
                throw new WrongArgException();
        } catch (WrongArgException ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong amount of arguments were passed.");
        }

        return sendCommand(new Object[] {});
    }
}
