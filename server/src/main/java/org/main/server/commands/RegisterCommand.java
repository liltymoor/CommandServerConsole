package org.main.server.commands;

import org.main.server.commands.properties.*;
import org.main.server.commands.types.ClientCommand;
import org.main.server.fs.CollectionIO;
import org.main.server.fs.RegistrationResult;
import org.main.server.network.JwtTokenManager;
import org.shared.model.input.buildrule.Builder;
import org.shared.model.input.buildrule.UserBuilder;
import org.shared.network.User;

import java.util.ArrayList;
import java.util.List;

public class RegisterCommand extends ClientCommand implements NotAuthorizable, InputCompoundable, HostActionable {
    private final CollectionIO collection;
    public RegisterCommand(CollectionIO collection) {
        super("reg", "Команда для регистрации новых пользователей.");
        this.collection = collection;
    }

    @Override
    public CommandResult hostAction(String[] params) {
        return action(new Object[]{new User(params[0], params[1])});
    }

    @Override
    public CommandResult hostAction(Object[] params) {
        return action(params);
    }


    @Override
    public CommandResult action(Object[] params) {
        User userInfo = (User)params[0];
        RegistrationResult result = collection.registerUser(userInfo);
        if (result == RegistrationResult.SUCCESS)
            return new CommandResult(ActionCode.OK, JwtTokenManager.generateToken(userInfo.getUsername()));
        return new CommandResult(ActionCode.ERROR, result.getDescription());
    }

    @Override
    public List<Builder<?>> getArgCompound() {
        List<Builder<?>> needToBuild = new ArrayList<>();
        needToBuild.add(new UserBuilder());
        return needToBuild;
    }
}
