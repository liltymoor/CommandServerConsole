package org.client.commands;

import org.client.commands.managers.InputRule;
import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.commands.properties.InputCompoundable;
import org.client.exceptions.BadLogicWereEaten;
import org.client.exceptions.WrongArgException;
import org.client.models.HumanBeingFormed;
import org.client.network.Client;
import org.shared.model.entity.Car;
import org.shared.model.entity.HumanBeing;
import org.shared.model.entity.params.Coordinates;
import org.shared.model.entity.params.Mood;
import org.shared.model.input.buildrule.Builder;
import org.shared.model.input.buildrule.HumanBeingBuilder;
import org.shared.model.input.buildrule.HumanBeingWithIdBuilder;
import org.shared.model.weapon.WeaponType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class UpdateCommand extends ServerCommand implements InputCompoundable {
    public UpdateCommand(Client client) {
        super("update", "Команда для обновления сущности в коллекции.", client);
    }

    @Override
    public CommandResult action(Object[] params) {
        try {
            if (params.length != getArgCompound().size() + 1) // extra id param
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
        toBuild.add(new HumanBeingWithIdBuilder());
        return toBuild;
    }
}
