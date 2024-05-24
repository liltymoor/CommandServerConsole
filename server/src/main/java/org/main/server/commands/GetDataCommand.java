package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.fs.CollectionIO;

import java.util.ArrayList;
import java.util.List;

public class GetDataCommand extends UserClientCommand {
    private CollectionIO collection;
    public GetDataCommand(CollectionIO collection) {
        super("get_data", "Команда для получения клиентом списка с HumanBeing.");
        this.collection = collection;
    }

    @Override
    public CommandResult action(Object[] params, String username) {
        return new CommandResult(ActionCode.OK).setAnswerData(collection.getBeings());
    }
}
