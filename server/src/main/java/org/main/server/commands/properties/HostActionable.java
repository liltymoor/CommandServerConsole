package org.main.server.commands.properties;

public interface HostActionable extends Actionable {
    public CommandResult hostAction(String[] params);
    public CommandResult hostAction(Object[] params);
}
