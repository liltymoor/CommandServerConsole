package org.main.server.commands.properties;

import org.shared.model.input.buildrule.Builder;
import java.util.List;

public interface InputCompoundable {
    public List<Builder<?>> getArgCompound();
}
