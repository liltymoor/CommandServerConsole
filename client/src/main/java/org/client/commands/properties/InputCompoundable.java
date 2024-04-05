package org.client.commands.properties;

import org.client.commands.managers.InputRule;

import java.util.LinkedHashMap;

public interface InputCompoundable {
    public LinkedHashMap<String, InputRule> getArgCompound();
}
