package org.client.commands;

import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.commands.properties.InputCompoundable;
import org.client.commands.types.ServerCommand;
import org.client.network.ClientUDP;
import org.shared.model.input.buildrule.Builder;
import org.shared.model.input.buildrule.UserBuilder;
import org.shared.network.User;

import java.util.ArrayList;
import java.util.List;

public class AuthCommand extends ServerCommand implements InputCompoundable {
    public AuthCommand(ClientUDP client) {
        super("auth", "Команда для того чтобы авторизоваться.", client);
    }

    @Override
    public CommandResult action(Object[] params) {
        User userInfo = (User)params[0];
        CommandResult result = sendCommand(new Object[]{userInfo});

        if (result.getCode() == ActionCode.OK) {
            client.setToken(result.getMessage());
            return new CommandResult(ActionCode.OK, "Auth completed.");
        }

        return result;
    }

    @Override
    public List<Builder<?>> getArgCompound() {
        List<Builder<?>> toBuild = new ArrayList<>();
        toBuild.add(new UserBuilder());
        return toBuild;
    }
}
