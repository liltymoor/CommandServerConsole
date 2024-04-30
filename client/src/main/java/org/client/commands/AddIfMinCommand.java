package org.client.commands;

import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.commands.properties.InputCompoundable;
import org.client.network.ClientUDP;
import org.shared.model.entity.HumanBeing;
import org.shared.model.input.buildrule.Builder;
import org.shared.model.input.buildrule.HumanBeingBuilder;

import java.util.ArrayList;
import java.util.List;

public class AddIfMinCommand extends ServerCommand implements InputCompoundable {
    public AddIfMinCommand(ClientUDP client) {
        super("add_if_min", "Команда для добавления элемента в коллекцию, если элемент минимальный", client);
    }

    @Override
    public CommandResult action(Object[] params) {
        if (params.length != getArgCompound().size())
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong amount of arguments were passed.");

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
