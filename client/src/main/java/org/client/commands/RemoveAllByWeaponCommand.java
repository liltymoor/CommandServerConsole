package org.client.commands;

import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.exceptions.WrongArgException;
import org.client.network.Client;
import org.shared.model.weapon.WeaponType;

import java.util.Arrays;
import java.util.stream.Collectors;

public class RemoveAllByWeaponCommand extends ServerCommand {
    private final String possible_values = Arrays.stream(WeaponType.values()).map(type-> type.name()).collect(Collectors.joining(", "));
    public RemoveAllByWeaponCommand(Client client) {
        super("remove_all_by_weapon", "Команда для удаления всех записей с указанным оружием (%s)", client);
        setDescription(getDescription().formatted(possible_values));
    }

    @Override
    public CommandResult action(Object[] params) {
        if (params.length != 1)
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong amount of arguments were passed.");

        WeaponType weaponType;
        try {
            weaponType = WeaponType.valueOf((String) params[0]);
        } catch (Exception ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Not weapon object. Try this: " + possible_values);
        }

        return sendCommand(new Object[]{weaponType});
    }
}
