package org.client.commands;

import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.network.ClientUDP;
import org.client.network.NetworkByteWrapper;
import org.shared.network.Response;

import java.nio.ByteBuffer;

public abstract class ServerCommand extends Command {
    protected final ClientUDP client;

    public ServerCommand(String commandName, ClientUDP client) {
        super(commandName);
        this.client = client;
    }

    public ServerCommand(String commandName, String description, ClientUDP client) {
        super(commandName, description);
        this.client = client;
    }

    protected CommandResult sendCommand(Object[] args) {
        ByteBuffer data = NetworkByteWrapper.wrapRequest(this, args);
        Response response = client.sendAndGetResponse(data);
        System.out.println("[CLIENT] Server response received\n");
        return new CommandResult(ActionCode.valueOf(response.getResultCode()), response.getResultMessage());
    }
}
