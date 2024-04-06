package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;

import org.main.server.fs.CollectionIO;

import org.shared.model.entity.HumanBeing;

public class RemoveLowerCommand extends Command {
    CollectionIO collection;
    public RemoveLowerCommand(CollectionIO collection) {
        super("remove_lower", "Команда удаляет все элементы меньше заданного");
        this.collection = collection;
    }

    @Override
    public CommandResult action(Object[] params) {
        HumanBeing being;
        try {
            being = (HumanBeing) params[0];
        }  catch (Exception ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong data were eaten by program.");
        }

        collection.forEach(humanBeing -> {
            if (humanBeing.compareTo(being) < 0)
                collection.removeFromCollection(humanBeing);
        });

        return new CommandResult(ActionCode.OK);
    }
}
