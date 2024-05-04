package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;

import org.main.server.commands.properties.InputCompoundable;
import org.main.server.fs.CollectionIO;

import org.shared.model.entity.HumanBeing;
import org.shared.model.input.buildrule.Builder;
import org.shared.model.input.buildrule.HumanBeingBuilder;
import org.shared.model.input.buildrule.HumanBeingWithIdBuilder;

import java.util.ArrayList;
import java.util.List;

public class RemoveLowerCommand extends UserClientCommand implements InputCompoundable {
    CollectionIO collection;
    public RemoveLowerCommand(CollectionIO collection) {
        super("remove_lower", "Команда удаляет все элементы меньше заданного");
        this.collection = collection;
    }

    @Override
    public CommandResult action(Object[] params, String username) {
        HumanBeing being;
        try {
            being = (HumanBeing) params[0];
        }  catch (Exception ex) {
            return new CommandResult(ActionCode.BAD_INPUT, String.format("Something went wrong (%s)", ex.getMessage()));
        }

        collection.forEach(humanBeing -> {
            if (humanBeing.compareTo(being) < 0)
                collection.removeFromCollection(humanBeing, username);
        });

        return new CommandResult(ActionCode.OK);
    }

    @Override
    public List<Builder<?>> getArgCompound() {
        List<Builder<?>> toBuild = new ArrayList<>();
        toBuild.add(new HumanBeingBuilder());
        return toBuild;
    }
}
