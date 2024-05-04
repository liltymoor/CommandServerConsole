package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.properties.HostActionable;
import org.main.server.commands.properties.InputCompoundable;
import org.main.server.fs.CollectionIO;
import org.main.server.fs.RegistrationResult;
import org.shared.model.input.buildrule.Builder;
import org.shared.model.input.buildrule.UserBuilder;
import org.shared.network.User;

import java.util.ArrayList;
import java.util.List;

public class RegisterCommand extends HostCommand implements InputCompoundable {
    private final CollectionIO collection;
    public RegisterCommand(CollectionIO collection) {
        super("reg", "Команда для регистрации новых пользователей.");
        this.collection = collection;
    }

    @Override
    public CommandResult hostAction(String[] params) {
        User userInfo = new User(params[0], params[1]);
        RegistrationResult result = collection.registerUser(userInfo);
        if (result == RegistrationResult.SUCCESS)
            return new CommandResult(ActionCode.OK, result.getDescription());
        return new CommandResult(ActionCode.ERROR, result.getDescription());
    }

    @Override
    public CommandResult hostAction(Object[] params) {
        User userInfo = (User)params[0];
        RegistrationResult result = collection.registerUser(userInfo);
        if (result == RegistrationResult.SUCCESS)
            return new CommandResult(ActionCode.OK, result.getDescription());
        return new CommandResult(ActionCode.ERROR, result.getDescription());
    }

    @Override
    public List<Builder<?>> getArgCompound() {
        List<Builder<?>> needToBuild = new ArrayList<>();
        needToBuild.add(new UserBuilder());
        return needToBuild;
    }
}
