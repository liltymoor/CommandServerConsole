package org.client.commands;

import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.exceptions.WrongArgException;


public class PrintUniqueImpactCommand extends Command {
    public PrintUniqueImpactCommand() {
        super("print_unique_impact", "Команда для просмотра уникальных значений скорости");
    }

    @Override
    public CommandResult action(String[] params) {
        try {
            if (params.length != 0)
                throw new WrongArgException();
        } catch (WrongArgException ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong amount of arguments were passed.");
        }

//        for (Long impact : collection.getUniqueImpact())
//            System.out.println(impact);

        return new CommandResult(ActionCode.OK);
    }
}
