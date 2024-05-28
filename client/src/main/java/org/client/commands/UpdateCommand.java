package org.client.commands;

import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.commands.properties.InputCompoundable;
import org.client.commands.types.ServerCommand;
import org.client.exceptions.WrongArgException;
import org.client.network.ClientUDP;
import org.shared.model.entity.HumanBeing;
import org.shared.model.input.buildrule.Builder;
import org.shared.model.input.buildrule.HumanBeingWithIdBuilder;

import java.util.ArrayList;
import java.util.List;

public class UpdateCommand extends ServerCommand implements InputCompoundable {
    public UpdateCommand(ClientUDP client) {
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
