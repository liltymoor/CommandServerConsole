package org.main.server.commands.managers;

public class InputRule {
    private String name = "No name";
    private String description = "No description";

    private String defaultValue = null;

    private Checker checker;
    public InputRule(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public InputRule setRule(Checker checker) {
        this.checker = checker;
        return this;
    }

    public InputRule setDefault(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getName() {
        return name;
    }

    public boolean check(String input) {
        return checker.check(input);
    }

    @Override
    public String toString() {
        return description;
    }
}
