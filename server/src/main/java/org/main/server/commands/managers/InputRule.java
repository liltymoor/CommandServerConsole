package org.main.server.commands.managers;

public abstract class InputRule implements Checkable{

    private String description = "No description";

    public InputRule(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean check(String input) {
        return true;
    };
}
