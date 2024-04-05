package org.client.commands;

import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.exceptions.WrongArgException;

public class InfoCommand extends Command {
    public InfoCommand() {
        super("info", "Команда для вывода в терминал информации о коллекции.");
    }

    @Override
    public CommandResult action(String[] params) {
        try {
            if (params.length > 0)
                throw new WrongArgException();
        } catch (WrongArgException ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "No args must be provided.");
        }
//        System.out.printf("Размер: %d, Тип: LinkedHashSet, Название коллекции: %s%n", collectionIO.size(), collectionIO.getCollectionName());
        return new CommandResult(ActionCode.OK);
    }
}
