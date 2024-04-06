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

public class AddCommand extends Command {
    private CollectionIO collection;
    public AddCommand(CollectionIO collection) {
        super("add", "Команда для добавления сущности в коллекцию HumanBeing.");
        this.collection = collection;
    }

    @Override
    public CommandResult action(Object[] params) {
        HumanBeing being;
        try {
            being = (HumanBeing) params[0];
            being.setZonedDT(ZonedDateTime.now());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong data were eaten by program.");
        }

        collection.addToCollection(being);
        return new CommandResult(ActionCode.OK);
    }
}
