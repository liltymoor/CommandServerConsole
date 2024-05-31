package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.types.UserClientCommand;
import org.main.server.exceptions.WrongArgException;
import org.main.server.fs.CollectionIO;
import org.shared.model.entity.HumanBeing;

import java.util.*;

public class SyncCommand extends UserClientCommand {
    CollectionIO collection;

    public SyncCommand(CollectionIO collection) {
        super("sync", "Команда для проверки валидности локальной-бд клиента.");
        this.collection = collection;
    }

    @Override
    public CommandResult action(Object[] params, String username) {
        try {
            List<HumanBeing> listToSync = (List<HumanBeing>) params[0];

            Set<HumanBeing> differences = new LinkedHashSet<>(collection.getBeings());
            listToSync.forEach(differences::remove);

            if (!differences.isEmpty())
                return new CommandResult(ActionCode.ERROR, "Collection is invalid");

            return new CommandResult(ActionCode.OK, "Validated");

        } catch (ClassCastException e) {
            return new CommandResult(ActionCode.BAD_INPUT, new WrongArgException().getMessage());
        }
    }
}
