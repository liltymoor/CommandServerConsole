package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.properties.HostActionable;
import org.main.server.commands.properties.InputCompoundable;
import org.main.server.fs.CollectionIO;
import org.shared.model.entity.HumanBeing;
import org.shared.model.input.buildrule.Builder;
import org.shared.model.input.buildrule.HumanBeingBuilder;
import org.shared.model.input.buildrule.HumanBeingWithIdBuilder;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

// TODO ЭТУ И ОСТАЛЬНЫЕ КОМАНДЫ КЛИЕНТА ФИКСАНУТЬ И ПРОВЕРИТЬ, ДАЛЬШЕ ОТПРАВКА/ПОЛУЧЕНИЕ ОТВЕТОВ ЗАПРОСОВ ЧАНКАМИ

public class AddIfMinCommand extends ClientCommand implements InputCompoundable, HostActionable {
    CollectionIO collection;
    public AddIfMinCommand(CollectionIO collection) {
        super("add_if_min", "Команда для добавления элемента в коллекцию, если элемент минимальный");
        this.collection = collection;
    }

    @Override
    public CommandResult action(Object[] params) {
        HumanBeing being;
        try {
            being = (HumanBeing) params[0];
            being.setZonedDT(ZonedDateTime.now());
        }  catch (Exception ex) {
            return new CommandResult(ActionCode.BAD_INPUT, String.format("Something went wrong (%s)", ex.getMessage()));
        }
        if (collection.isMinimal(being)) {
            collection.addToCollection(being);
            return new CommandResult(ActionCode.OK);
        }
        return new CommandResult(ActionCode.NOT_MINIMAL);
    }

    @Override
    public List<Builder<?>> getArgCompound() {
        List<Builder<?>> needToBuild = new ArrayList<>();
        needToBuild.add(new HumanBeingBuilder());
        return needToBuild;
    }

    @Override
    public CommandResult hostAction(String[] params) {
        return action(params);
    }

    @Override
    public CommandResult hostAction(Object[] params) {
        return action(params);
    }
}

