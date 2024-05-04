package org.shared.model.input;

public class FieldStructure {
    private String name = "No name";
    private String description = "No description";
    private boolean notNullable = false;

    private String defaultValue = null;

    public FieldStructure(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public FieldStructure setDefault(String defaultValue) {
        this.defaultValue = defaultValue;
        return this;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public String getName() {
        return name;
    }
    public boolean isNotNullable() { return notNullable; }

    public FieldStructure notNullable() { notNullable = true; return this; }

    @Override
    public String toString() {
        return description;
    }
}
