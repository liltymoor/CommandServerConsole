package org.main.server.commands.properties;


public interface Actionable {
    public CommandResult action(String[] params);
}
