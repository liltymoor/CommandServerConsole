package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.properties.HostActionable;
import org.main.server.commands.properties.InputCompoundable;
import org.main.server.commands.types.UserClientCommand;
import org.main.server.exceptions.BadLogicWereEaten;
import org.main.server.fs.CollectionIO;
import org.shared.model.entity.HumanBeing;
import org.shared.model.input.buildrule.Builder;
import org.shared.model.input.buildrule.HumanBeingWithIdBuilder;

import java.util.ArrayList;
import java.util.List;

public class UpdateCommand extends UserClientCommand implements InputCompoundable, HostActionable {
    CollectionIO collection;
    public UpdateCommand(CollectionIO collection) {
        super("update", "Команда для обновления сущности в коллекции.");
        this.collection = collection;
    }

    @Override
    public CommandResult action(Object[] params, String username) {

        HumanBeing being;
        int id;
        try {
            id = (int) params[0];
            being = (HumanBeing) params[1];
        } catch (BadLogicWereEaten ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong data were eaten by program.");
        }

        being.setId(id);
        being.setEntityOwner(username);
        if (collection.editCollectionEntity(being, username))
            return new CommandResult(ActionCode.OK);
        return new CommandResult(ActionCode.BAD_INPUT, "Entity wasn't found");
    }

    @Override
    public CommandResult hostAction(String[] params) {
        return action(params, "admin");
    }

    @Override
    public CommandResult hostAction(Object[] params) {
        return action(params, "admin");
    }

    @Override
    public List<Builder<?>> getArgCompound() {
        List<Builder<?>> toBuild = new ArrayList<>();
        toBuild.add(new HumanBeingWithIdBuilder());
        return toBuild;
    }
}
