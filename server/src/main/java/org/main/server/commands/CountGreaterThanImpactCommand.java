package org.main.server.commands;

import org.main.server.commands.managers.InputRule;
import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.properties.InputCompoundable;
import org.main.server.exceptions.WrongArgException;
import org.main.server.fs.CollectionIO;

import java.util.LinkedHashMap;


public class CountGreaterThanImpactCommand extends Command {
    CollectionIO collection;
    public CountGreaterThanImpactCommand(CollectionIO collection) {
        super("count_greater_than_impact", "Вывести количество элементов в коллекции, которые выше указанной величины impact");
        this.collection = collection;
    }

    @Override
    public CommandResult action(Object[] params) {
        Long impactSpeed;
        try {
            impactSpeed = (Long)params[0];
        } catch (Exception ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong data were eaten by program.");
        }

        int result = collection.countImpactSpeedGreater(impactSpeed);
        return new CommandResult(ActionCode.OK, String.valueOf(result));
    }
}
