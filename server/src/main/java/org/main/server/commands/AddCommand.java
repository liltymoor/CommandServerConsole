package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.properties.HostActionable;
import org.main.server.commands.properties.InputCompoundable;
import org.main.server.commands.types.UserClientCommand;
import org.main.server.fs.CollectionIO;
import org.shared.model.entity.HumanBeing;
import org.shared.model.input.buildrule.Builder;
import org.shared.model.input.buildrule.HumanBeingBuilder;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

public class AddCommand extends UserClientCommand implements InputCompoundable, HostActionable {
    private final CollectionIO collection;

    public AddCommand(CollectionIO collection) {
        super("add", "Команда для добавления сущности в коллекцию HumanBeing.");
        this.collection = collection;
    }

    @Override
    public CommandResult action(Object[] params, String username) {
        HumanBeing being;
        try {
            being = (HumanBeing) params[0];
            being.setEntityOwner(username);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new CommandResult(ActionCode.BAD_INPUT, String.format("Something went wrong (%s)", ex.getMessage()));
        }

        collection.addToCollection(being, username);
        return new CommandResult(ActionCode.OK);
    }



    @Override
    public List<Builder<?>> getArgCompound() {
        List<Builder<?>> needToBuild = new ArrayList<>();
        needToBuild.add(new HumanBeingBuilder());
        return needToBuild;
    }

    @Override
    public CommandResult hostAction(Object[] params) {
        return action(params, "admin");
    }

    @Override
    public CommandResult hostAction(String[] params) {
        return action(params, "admin");
    }
}
