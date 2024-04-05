package org.main.server.commands.properties;

public interface Describable {
    public default String getDescription() {
      return "This command have no description yet.";
    };
}
