package org.client.commands;

import org.client.commands.managers.InputRule;
import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.commands.properties.InputCompoundable;
import org.client.exceptions.WrongArgException;
import org.client.models.HumanBeingFormed;
import org.client.network.Client;
import org.shared.model.entity.Car;
import org.shared.model.entity.HumanBeing;
import org.shared.model.entity.params.Coordinates;
import org.shared.model.entity.params.Mood;
import org.shared.model.weapon.WeaponType;

import java.util.LinkedHashMap;

public class RemoveLowerCommand extends ServerCommand implements InputCompoundable {
    public RemoveLowerCommand(Client client) {
        super("remove_lower", "Команда удаляет все элементы меньше заданного", client);
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

//        collection.forEach(humanBeing -> {
//            if (humanBeing.compareTo(being) < 0)
//                collection.removeFromCollection(humanBeing);
//        });

        return sendCommand(new Object[] {being});
    }

    @Override
    public LinkedHashMap<String, InputRule> getArgCompound() {
        return HumanBeingFormed.getFieldsMap();
    }
}
