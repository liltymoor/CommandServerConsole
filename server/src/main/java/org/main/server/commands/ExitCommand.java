package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.properties.HostActionable;

public class ExitCommand extends ClientCommand implements HostActionable {
    public ExitCommand() {
        super("exit", "Команда для выхода из программы");
    }

    @Override
    public CommandResult action(Object[] params) {
        System.out.println("Exiting...");
        System.exit(0);
        return new CommandResult(ActionCode.OK);
    }

    @Override
    public CommandResult hostAction(String[] params) {
        return action(params);
    }

    @Override
    public CommandResult hostAction(Object[] params) {
        return action(params);
    }
}
