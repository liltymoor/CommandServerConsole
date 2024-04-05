package org.main.server.commands;

import org.main.server.commands.managers.InputRule;
import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.properties.InputCompoundable;
import org.main.server.exceptions.BadLogicWereEaten;
import org.main.server.exceptions.WrongArgException;
import org.main.server.fs.CollectionIO;
import org.main.server.model.entity.Car;
import org.main.server.model.entity.HumanBeing;
import org.main.server.model.entity.params.Coordinates;
import org.main.server.model.entity.params.Mood;
import org.main.server.model.weapon.WeaponType;

import java.util.LinkedHashMap;

public class UpdateCommand extends Command implements InputCompoundable {
    CollectionIO collection;
    LinkedHashMap<String, InputRule> inputCompound;
    public UpdateCommand(CollectionIO collection) {
        super("update", "Команда для обновления сущности в коллекции.");
        this.collection = collection;
    }

    @Override
    public CommandResult action(String[] params) {
        try {
            if (params.length != getArgCompound().size() + 1) // extra id param
                throw new WrongArgException();
        } catch (WrongArgException ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong amount of arguments were passed.");
        }

        HumanBeing being;
        int id;
        try {
            id = Integer.parseInt(params[0]);
            String name = params[1];

            Float x;
            if (params[2] == null)
                x = null;
            else if (params[2].isEmpty()) {
                x = null;
            } else
                x = Float.parseFloat(params[2]);

            Long y = Long.parseLong(params[3]);

            Coordinates coords = new Coordinates(x, y);

            Boolean realHero = params[4].equals("true");
            Boolean hasToothpick = params[5].equals("true");
            Long impactSpeed = Long.parseLong(params[6]);
            Double minutesOfWaiting = Double.parseDouble(params[7]);
            WeaponType weaponType = WeaponType.valueOf(params[8]);
            Mood mood = Mood.valueOf(params[9]);
            Car car = new Car(params[10]);

            being = new HumanBeing(name, coords, realHero, hasToothpick, impactSpeed, minutesOfWaiting, weaponType, mood, car);
        } catch (BadLogicWereEaten ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong data were eaten by program.");
        }

        being.setId(id);
        if (collection.editCollectionEntity(being))
            return new CommandResult(ActionCode.OK);
        return new CommandResult(ActionCode.BAD_INPUT, "Entity wasn't found");
    }

    @Override
    public LinkedHashMap<String, InputRule> getArgCompound() {
        return HumanBeing.getFieldsMapWithId();
    }
}
