package org.main.server.commands;


import org.main.server.commands.managers.CommandHost;
import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.properties.HostActionable;

public class HelpCommand extends UserClientCommand implements HostActionable {
    private CommandHost host;
    public HelpCommand(CommandHost host) {
        super("help", "Команда для вывода в терминал списка всех комманд.");
        this.host = host;
    }

    @Override
    public CommandResult action(Object[] params, String username) {
        host.getCommands().values().forEach(command -> {
            System.out.print("%s : %s%n".formatted(command.getName(), command.getDescription()));
        });
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
