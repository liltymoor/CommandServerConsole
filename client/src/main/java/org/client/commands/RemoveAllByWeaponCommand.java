package org.client.commands;

import org.client.commands.managers.InputRule;
import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.commands.properties.InputCompoundable;
import org.client.exceptions.WrongArgException;
import org.client.network.Client;
import org.shared.model.weapon.WeaponType;

import java.util.LinkedHashMap;

public class RemoveAllByWeaponCommand extends ServerCommand implements InputCompoundable {
    public RemoveAllByWeaponCommand(Client client) {
        super("remove_all_by_weapon", "Команда для удаления всех записей с указанным оружием", client);
    }

    @Override
    public CommandResult action(String[] params) {
        try {
            if (params.length != getArgCompound().size())
                throw new WrongArgException();
        } catch (WrongArgException ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong amount of arguments were passed.");
        }
        WeaponType weaponType;
        try {
            weaponType = WeaponType.valueOf(params[0]);
        }  catch (Exception ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong data were eaten by program.");
        }

//        collection.removeByWeapon(weaponType);

        return sendCommand(new Object[] {weaponType});
    }

    @Override
    public LinkedHashMap<String, InputRule> getArgCompound() {
        LinkedHashMap<String, InputRule> args = new LinkedHashMap<>();
        args.put("weaponType", new InputRule("(one of: AXE, SHOTGUN, RIFLE, MACHINE GUN)") {
            @Override
            public boolean check(String input) {
                try {
                    WeaponType.valueOf(input.toUpperCase());
                    return true;
                } catch (Exception ex) {
                    return false;
                }
            }
        });
        return args;
    }
}
