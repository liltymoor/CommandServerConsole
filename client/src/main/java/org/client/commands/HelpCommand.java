package org.client.commands;


import org.client.commands.managers.CommandHost;
import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.exceptions.WrongArgException;

public class HelpCommand extends Command {
    private CommandHost host;
    public HelpCommand(CommandHost host) {
        super("help", "Команда для вывода в терминал списка всех комманд.");
        this.host = host;
    }

    @Override
    public CommandResult action(String[] params) {
        try {
            if (params.length > 0)
                throw new WrongArgException();
        } catch (WrongArgException ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "No args must be provided.");
        }
        host.getCommands().values().forEach(command -> {
            System.out.print("%s : %s%n".formatted(command.getName(), command.getDescription()));
        });
        return new CommandResult(ActionCode.OK);
    }
}
