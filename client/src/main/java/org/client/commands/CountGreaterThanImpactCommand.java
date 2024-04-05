package org.client.commands;

import org.client.commands.managers.InputRule;
import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.commands.properties.InputCompoundable;
import org.client.exceptions.WrongArgException;

import java.util.LinkedHashMap;

public class CountGreaterThanImpactCommand extends Command implements InputCompoundable {
    public CountGreaterThanImpactCommand() {
        super("count_greater_than_impact", "Вывести количество элементов в коллекции, которые выше указанной величины impact");
    }

    @Override
    public CommandResult action(String[] params) {
        try {
            if (params.length != getArgCompound().size())
                throw new WrongArgException();
        } catch (WrongArgException ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong amount of arguments were passed.");
        }

        Long impactSpeed;
        try {
            impactSpeed = Long.parseLong(params[0]);
        } catch (Exception ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong data were eaten by program.");
        }

        //System.out.println(collection.countImpactSpeedGreater(impactSpeed));
        return new CommandResult(ActionCode.OK);
    }

    @Override
    public LinkedHashMap<String, InputRule> getArgCompound() {
        LinkedHashMap<String, InputRule> args = new LinkedHashMap<>();
        args.put("impactSpeed", new InputRule("") {
            @Override
            public boolean check(String input) {
                return input != "";
            }
        });
        return args;
    }
}
