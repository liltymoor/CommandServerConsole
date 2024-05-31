package org.client.commands;

import javafx.collections.ObservableList;
import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.commands.types.ServerCommand;
import org.client.exceptions.WrongArgException;
import org.client.network.ClientUDP;
import org.shared.model.entity.HumanBeing;


import java.util.List;

public class SyncCommand extends ServerCommand {
    public SyncCommand(ClientUDP client) {
        super("sync", "Команда для проверки валидности коллекции.", client);
    }

    @Override
    public CommandResult action(Object[] params) {
        if (params.length != 1) return new CommandResult(ActionCode.BAD_INPUT, new WrongArgException().getMessage());
        List<HumanBeing> listToSync;

        try {
            listToSync = ((ObservableList<HumanBeing>) params[0]).stream().toList();
            return sendCommand(new Object[]{listToSync});
        } catch (ClassCastException ignored) {}

        try {
            listToSync = (List<HumanBeing>) params[0];
            return sendCommand(new Object[]{listToSync});
        } catch (ClassCastException e) {
            return new CommandResult(ActionCode.BAD_INPUT, "[SYNC COMMAND] Sync command works with List<HumanBeing> and it's siblings only.");
        }
    }
}
