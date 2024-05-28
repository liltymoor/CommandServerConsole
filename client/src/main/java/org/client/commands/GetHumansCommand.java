package org.client.commands;

import org.client.commands.properties.DataProvidedCommandResult;
import org.client.commands.types.DataProvidableServerCommand;
import org.client.network.ClientUDP;
import org.shared.model.entity.HumanBeing;

import java.util.LinkedHashSet;

public class GetHumansCommand extends DataProvidableServerCommand<LinkedHashSet<HumanBeing>> {
    public GetHumansCommand(ClientUDP clientUDP) {
        super("get_humans", "Команда для получения всех HumanBeing", clientUDP);
    }

    //TODO Создать интерфейс с генериком, который будет возвращать результаты из Response (который возвращает resultData)

    @Override
    public DataProvidedCommandResult<LinkedHashSet<HumanBeing>> action(Object[] params) {
        return sendCommand(params);

    }
}
