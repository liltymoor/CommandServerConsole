package org.client.commands;

import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.exceptions.WrongArgException;
import org.client.network.Client;

public class SaveCommand extends Command{
    public SaveCommand() {
        super("save", "Команда для сохранения коллекции в файл.");
    }

    @Override
    public CommandResult action(String[] params) {
        try {
            if (params.length != 0)
                throw new WrongArgException();
        } catch (WrongArgException ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong amount of arguments were passed.");
        }

//        if (collection.flushToJson())
//            return new CommandResult(ActionCode.OK);
//        return sendCommand(new Object[] {});
        return new CommandResult(ActionCode.OK);
    }
}
