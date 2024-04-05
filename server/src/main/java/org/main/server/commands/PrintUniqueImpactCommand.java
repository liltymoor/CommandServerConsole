package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.exceptions.WrongArgException;
import org.main.server.fs.CollectionIO;


public class PrintUniqueImpactCommand extends Command {
    CollectionIO collection;
    public PrintUniqueImpactCommand(CollectionIO collection) {
        super("print_unique_impact", "Команда для просмотра уникальных значений скорости");
        this.collection = collection;
    }

    @Override
    public CommandResult action(String[] params) {
        try {
            if (params.length != 0)
                throw new WrongArgException();
        } catch (WrongArgException ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong amount of arguments were passed.");
        }

        for (Long impact : collection.getUniqueImpact())
            System.out.println(impact);

        return new CommandResult(ActionCode.OK);
    }
}
