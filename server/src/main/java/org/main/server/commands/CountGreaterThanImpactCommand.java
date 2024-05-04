package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.properties.HostActionable;
import org.main.server.fs.CollectionIO;


public class CountGreaterThanImpactCommand extends UserClientCommand implements HostActionable {
    CollectionIO collection;
    public CountGreaterThanImpactCommand(CollectionIO collection) {
        super("count_greater_than_impact", "Вывести количество элементов в коллекции, которые выше указанной величины impact");
        this.collection = collection;
    }

    @Override
    public CommandResult action(Object[] params, String username) {
        Long impactSpeed;
        try {
            impactSpeed = (Long)params[0];
        } catch (Exception ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong data were eaten by program.");
        }

        int result = collection.countImpactSpeedGreater(impactSpeed);
        return new CommandResult(ActionCode.OK, String.valueOf(result));
    }

    @Override
    public CommandResult hostAction(String[] params) {
        Long impactSpeed;
        try {
            impactSpeed = Long.parseLong(params[0]);
        } catch (Exception ex) {
            return new CommandResult(ActionCode.BAD_INPUT, String.format("Something went wrong (%s)", ex.getMessage()));
        }
        return action(new Object[] {impactSpeed});
    }

    @Override
    public CommandResult hostAction(Object[] params) {
        return action(params, "admin");
    }
}
