package org.main.server.commands;


import org.main.server.commands.managers.CommandHost;
import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.exceptions.WrongArgException;

public class HelpCommand extends Command {
    private CommandHost host;
    public HelpCommand(CommandHost host) {
        super("help", "Команда для вывода в терминал списка всех комманд.");
        this.host = host;
    }

    @Override
    public CommandResult action(Object[] params) {
        host.getCommands().values().forEach(command -> {
            System.out.print("%s : %s%n".formatted(command.getName(), command.getDescription()));
        });
        return new CommandResult(ActionCode.OK);
    }
}
