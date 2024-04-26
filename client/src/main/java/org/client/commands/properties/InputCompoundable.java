package org.client.commands.properties;

import org.client.commands.managers.InputRule;
import org.shared.model.input.buildrule.Builder;

import java.util.LinkedHashMap;
import java.util.List;

public interface InputCompoundable {
    public List<Builder<?>> getArgCompound();
}
