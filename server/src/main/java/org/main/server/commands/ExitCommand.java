package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.exceptions.WrongArgException;

public class ExitCommand extends Command {
    public ExitCommand() {
        super("exit", "Команда для выхода из программы");
    }

    @Override
    public CommandResult action(String[] params) {
        try {
            if (params.length != 0)
                throw new WrongArgException();
        } catch (WrongArgException ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong amount of arguments were passed.");
        }

        System.out.println("Exiting...");
        System.exit(0);
        return new CommandResult(ActionCode.OK);
    }
}
