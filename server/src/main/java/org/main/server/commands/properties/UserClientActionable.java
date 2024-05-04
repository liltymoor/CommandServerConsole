package org.main.server.commands.properties;

public interface UserClientActionable extends Actionable{
    CommandResult action(Object[] params, String username);
}
