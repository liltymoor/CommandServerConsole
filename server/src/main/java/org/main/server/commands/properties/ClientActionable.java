package org.main.server.commands.properties;


public interface ClientActionable extends Actionable {
    public CommandResult action(Object[] params);
}
