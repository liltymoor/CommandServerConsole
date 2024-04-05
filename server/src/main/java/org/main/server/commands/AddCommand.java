package org.main.server.commands;

import org.main.server.commands.managers.InputRule;
import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.properties.InputCompoundable;
import org.main.server.exceptions.WrongArgException;
import org.main.server.fs.CollectionIO;
import org.main.server.model.entity.Car;
import org.main.server.model.entity.HumanBeing;
import org.main.server.model.entity.params.Coordinates;
import org.main.server.model.entity.params.Mood;
import org.main.server.model.weapon.WeaponType;

import java.util.LinkedHashMap;

public class AddCommand extends Command implements InputCompoundable {
    private CollectionIO collection;
    public AddCommand(CollectionIO collection) {
        super("add", "Команда для добавления сущности в коллекцию HumanBeing.");
        this.collection = collection;
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
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong data were eaten by program.");
        }

        collection.addToCollection(being);
        return new CommandResult(ActionCode.OK);
    }

    @Override
    public LinkedHashMap<String, InputRule> getArgCompound() {
        return HumanBeing.getFieldsMap();
    }
}
