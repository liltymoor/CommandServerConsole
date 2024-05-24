package org.client.commands;

import org.client.commands.properties.CommandResult;
import org.client.network.ClientUDP;

public class GetHumansCommand extends ServerCommand {
    public GetHumansCommand(ClientUDP clientUDP) {
        super("get_humans", "Команда для получения всех HumanBeing", clientUDP);
    }

    //TODO Создать интерфейс с генериком, который будет возвращать результаты из Response (который возвращает resultData)

    @Override
    public CommandResult action(Object[] params) {
        return null;
    }
}
