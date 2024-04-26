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
import org.shared.model.input.buildrule.Builder;
import org.shared.model.input.buildrule.HumanBeingBuilder;
import org.shared.model.weapon.WeaponType;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class RemoveLowerCommand extends ServerCommand implements InputCompoundable {
    public RemoveLowerCommand(Client client) {
        super("remove_lower", "Команда удаляет все элементы меньше заданного", client);
    }

    @Override
    public CommandResult action(Object[] params) {
        try {
            if (params.length != getArgCompound().size())
                throw new WrongArgException();
        } catch (WrongArgException ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong amount of arguments were passed.");
        }

        HumanBeing being;
        try {
            being = (HumanBeing) params[0];
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong data were eaten by program.");
        }

        return sendCommand(new Object[] {being});
    }

    @Override
    public List<Builder<?>> getArgCompound() {
        List<Builder<?>> toBuild = new ArrayList<>();
        toBuild.add(new HumanBeingBuilder());
        return toBuild;
    }
}
