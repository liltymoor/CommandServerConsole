package org.client.commands;

import org.client.commands.managers.InputRule;
import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.commands.properties.InputCompoundable;
import org.client.exceptions.WrongArgException;
import org.client.model.entity.Car;
import org.client.model.entity.HumanBeing;
import org.client.model.entity.params.Coordinates;
import org.client.model.entity.params.Mood;
import org.client.model.weapon.WeaponType;

import java.util.LinkedHashMap;

public class AddIfMinCommand extends Command implements InputCompoundable {
    public AddIfMinCommand() {
        super("add_if_min", "Команда для добавления элемента в коллекцию, если элемент минимальный");
    }

    @Override
    public CommandResult action(String[] params) {
        try {
            if (params.length != getArgCompound().size())
                throw new WrongArgException();
        } catch (WrongArgException ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong amount of arguments were passed.");
        }

        HumanBeing being;
        try {
            String name = params[0];

            Float x;
            if (params[1] == null)
                x = null;
            else if (params[1].isEmpty()) {
                x = null;
            } else
                x = Float.parseFloat(params[1]);

            Long y = Long.parseLong(params[2]);

            Coordinates coords = new Coordinates(x, y);

            Boolean realHero = params[3].equals("true");
            Boolean hasToothpick = params[4].equals("true");
            Long impactSpeed = Long.parseLong(params[5]);
            Double minutesOfWaiting = Double.parseDouble(params[6]);
            WeaponType weaponType = WeaponType.valueOf(params[7]);
            Mood mood = Mood.valueOf(params[8]);
            Car car = new Car(params[9]);

            being = new HumanBeing(name, coords, realHero, hasToothpick, impactSpeed, minutesOfWaiting, weaponType, mood, car);
        }  catch (Exception ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong data were eaten by program.");
        }
//        if (collection.isMinimal(being)) {
//            collection.addToCollection(being);
//            return new CommandResult(ActionCode.OK);
//        }
        return new CommandResult(ActionCode.NOT_MINIMAL);
    }

    @Override
    public LinkedHashMap<String, InputRule> getArgCompound() {
        return HumanBeing.getFieldsMap();
    }
}
