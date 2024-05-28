package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.properties.HostActionable;
import org.main.server.commands.types.UserClientCommand;

public class ExitCommand extends UserClientCommand implements HostActionable {
    public ExitCommand() {
        super("exit", "Команда для выхода из программы");
    }

    @Override
    public CommandResult action(Object[] params, String username) {
        System.out.println("Exiting...");
        System.exit(0);
        return new CommandResult(ActionCode.OK);
    }

    @Override
    public CommandResult hostAction(String[] params) {
        return action(params, "admin");
    }

    @Override
    public CommandResult hostAction(Object[] params) {
        return action(params, "admin");
    }
}
