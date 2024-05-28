package org.client.commands;

import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.commands.types.Command;
import org.client.exceptions.WrongArgException;

public class SaveCommand extends Command {
    public SaveCommand() {
        super("save", "Команда для сохранения коллекции в файл.");
    }

    @Override
    public CommandResult action(Object[] params) {
        try {
            if (params.length != 0)
                throw new WrongArgException();
        } catch (WrongArgException ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong amount of arguments were passed.");
        }

        return new CommandResult(ActionCode.OK);
    }
}
