package org.main.server.commands;

import org.main.server.commands.managers.InputRule;
import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.properties.InputCompoundable;
import org.main.server.exceptions.BadLogicWereEaten;
import org.main.server.exceptions.WrongArgException;
import org.main.server.fs.CollectionIO;
import org.shared.model.entity.Car;
import org.shared.model.entity.HumanBeing;
import org.shared.model.entity.params.Coordinates;
import org.shared.model.entity.params.Mood;
import org.shared.model.weapon.WeaponType;

import java.util.LinkedHashMap;

public class UpdateCommand extends Command {
    CollectionIO collection;
    LinkedHashMap<String, InputRule> inputCompound;
    public UpdateCommand(CollectionIO collection) {
        super("update", "Команда для обновления сущности в коллекции.");
        this.collection = collection;
    }

    @Override
    public CommandResult action(Object[] params) {

        HumanBeing being;
        int id;
        try {
            id = (int) params[0];
            being = (HumanBeing) params[1];
        } catch (BadLogicWereEaten ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong data were eaten by program.");
        }

        being.setId(id);
        if (collection.editCollectionEntity(being))
            return new CommandResult(ActionCode.OK);
        return new CommandResult(ActionCode.BAD_INPUT, "Entity wasn't found");
    }
}
