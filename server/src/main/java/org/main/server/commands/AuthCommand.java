package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.properties.NotAuthorizable;
import org.main.server.commands.types.ClientCommand;
import org.main.server.fs.CollectionIO;
import org.main.server.fs.UserAuthResult;
import org.main.server.network.JwtTokenManager;
import org.shared.network.User;

public class AuthCommand extends ClientCommand implements NotAuthorizable {
    CollectionIO collection;
    public AuthCommand(CollectionIO collection) {
        super("auth", "Команда для авторизации.");
        this.collection = collection;
    }

    @Override
    public CommandResult action(Object[] params) {
        User userInfo = (User)params[0];
        UserAuthResult result = collection.authUser(userInfo);
        if (result == UserAuthResult.SUCCESS)
            return new CommandResult(ActionCode.OK, JwtTokenManager.generateToken(userInfo.getUsername()));
        return new CommandResult(ActionCode.ERROR, result.getDescription());
    }
}
