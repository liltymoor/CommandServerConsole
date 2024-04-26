package org.main.server.commands;
import org.main.server.commands.properties.HostActionable;

public abstract class HostCommand extends Command implements HostActionable {
    public HostCommand(String commandName) {
        super(commandName);
    }

    public HostCommand(String commandName, String description) {
        super(commandName, description);
    }
}
