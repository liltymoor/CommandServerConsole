package org.client.commands.types;

import org.client.commands.properties.ActionCode;
import org.client.commands.properties.DataProvidedCommandResult;
import org.client.network.ClientUDP;
import org.shared.network.Response;

import java.util.logging.Logger;

public abstract class DataProvidableServerCommand<T> extends ServerCommand{
    public DataProvidableServerCommand(String commandName, ClientUDP client) {
        super(commandName, client);
    }

    public DataProvidableServerCommand(String commandName, String description, ClientUDP client) {
        super(commandName, description, client);
    }

    @Override
    protected DataProvidedCommandResult<T> sendCommand(Object[] args) {
        Response response = sendCommandData(args);
        T resultData;
        try { resultData = (T) response.getResultData(); }
        catch (ClassCastException e) {
            Logger.getLogger("client-backend").warning("DataProvider was crashed because of unknown type of response data | " + e.getMessage() );
            return new DataProvidedCommandResult<>(ActionCode.ERROR, null);
        }
        return new DataProvidedCommandResult<T>(ActionCode.valueOf(response.getResultCode()), response.getResultMessage(), resultData);
    }

    @Override
    public abstract DataProvidedCommandResult<T> action(Object[] params);
}
