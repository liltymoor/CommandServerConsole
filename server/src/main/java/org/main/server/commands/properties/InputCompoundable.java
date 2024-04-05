package org.main.server.commands.properties;

import org.main.server.commands.managers.InputRule;

import java.util.LinkedHashMap;

public interface InputCompoundable {
    public LinkedHashMap<String, InputRule> getArgCompound();
}
