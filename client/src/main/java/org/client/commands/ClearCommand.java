package org.client.commands;

import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.exceptions.WrongArgException;


public class ClearCommand extends Command{
    public ClearCommand() {
        super("clear", "Команда для полной очистки коллекции.");
    }

    @Override
    public CommandResult action(String[] params) {
        try {
            if (params.length != 0)
                throw new WrongArgException();
        } catch (WrongArgException ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong amount of arguments were passed.");
        }

        //collection.clearCollection();
        return new CommandResult(ActionCode.OK);
    }
}
