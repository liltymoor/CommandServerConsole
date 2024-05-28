package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.types.UserClientCommand;
import org.main.server.fs.CollectionIO;

public class GetHumansCommand extends UserClientCommand {
    private CollectionIO collection;
    public GetHumansCommand(CollectionIO collection) {
        super("get_humans", "Команда для получения клиентом списка с HumanBeing.");
        this.collection = collection;
    }

    @Override
    public CommandResult action(Object[] params, String username) {
        return new CommandResult(ActionCode.OK).setAnswerData(collection.getBeings());
    }
}
