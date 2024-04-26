package org.main.server.commands;

import org.main.server.commands.properties.Describable;

public abstract class Command implements Describable {
    private String commandName;
    private String description = "";

    public Command(String commandName) {
        this.commandName = commandName;
    }

    public Command(String commandName, String description) {
        this.commandName = commandName;
        this.description = description;
    }

    @Override
    public String getDescription() {
        return this.description.isEmpty() ? Describable.super.getDescription() : description;
    }

    public String getName() {
        return commandName;
    }


    @Override
    public String toString() {
        return "Command: %s%nDescription: %s".formatted(this.commandName, this.description);
    }

}
