package org.client.commands;

import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.exceptions.WrongArgException;
import org.client.network.Client;
import org.client.network.NetworkByteWrapper;
import org.shared.network.Response;

import java.nio.ByteBuffer;

public class ExitCommand extends ServerCommand {
    SaveCommand saveCommand;
    public ExitCommand(SaveCommand saveCommand, Client client) {
        super("exit", "Команда для выхода из программы", client);
        this.saveCommand = saveCommand;
    }

    @Override
    public CommandResult action(String[] params) {
        try {
            if (params.length != 0)
                throw new WrongArgException();
        } catch (WrongArgException ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong amount of arguments were passed.");
        }

        ByteBuffer data = NetworkByteWrapper.wrapRequest(saveCommand, new Object[]{});
        Response response = client.sendAndGetResponse(data);
        System.out.println("Exiting...");
        System.exit(0);
        return new CommandResult(ActionCode.OK);
    }
}
