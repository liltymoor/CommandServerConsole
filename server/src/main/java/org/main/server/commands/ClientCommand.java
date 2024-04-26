package org.main.server.commands;
import org.main.server.commands.properties.ClientActionable;

public abstract class ClientCommand extends Command implements ClientActionable {
    public ClientCommand(String commandName, String description) {
        super(commandName, description);
    }

    public ClientCommand(String commandName) {
        super(commandName);
    }
}
