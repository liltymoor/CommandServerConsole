package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.properties.UserClientActionable;
import org.main.server.exceptions.UnauthorizedException;

public abstract class UserClientCommand extends ClientCommand implements UserClientActionable {
    public UserClientCommand(String commandName, String description) {
        super(commandName, description);
    }

    public UserClientCommand(String commandName) {
        super(commandName);
    }

    @Override
    public CommandResult action(Object[] params) {
        return new CommandResult(ActionCode.ERROR, new UnauthorizedException().getMessage());
    }
}
