package org.main.server.commands;

import org.main.server.commands.managers.InputRule;
import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.properties.InputCompoundable;
import org.main.server.exceptions.WrongArgException;
import org.main.server.fs.CollectionIO;
import org.shared.model.entity.Car;
import org.shared.model.entity.HumanBeing;
import org.shared.model.entity.params.Coordinates;
import org.shared.model.entity.params.Mood;
import org.shared.model.weapon.WeaponType;

import java.time.ZonedDateTime;
import java.util.LinkedHashMap;

public class AddIfMinCommand extends Command {
    CollectionIO collection;
    public AddIfMinCommand(CollectionIO collection) {
        super("add_if_min", "Команда для добавления элемента в коллекцию, если элемент минимальный");
        this.collection = collection;
    }

    @Override
    public CommandResult action(Object[] params) {
        HumanBeing being;
        try {
            being = (HumanBeing) params[0];
            being.setZonedDT(ZonedDateTime.now());
        }  catch (Exception ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong data were eaten by program.");
        }
        if (collection.isMinimal(being)) {
            collection.addToCollection(being);
            return new CommandResult(ActionCode.OK);
        }
        return new CommandResult(ActionCode.NOT_MINIMAL);
    }
}
