package org.main.server.commands;

import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.properties.HostActionable;
import org.main.server.commands.types.UserClientCommand;
import org.main.server.exceptions.WrongArgException;
import org.main.server.fs.CollectionIO;
import org.shared.model.weapon.WeaponType;

public class RemoveAllByWeaponCommand extends UserClientCommand implements HostActionable {
    CollectionIO collection;

    public RemoveAllByWeaponCommand(CollectionIO collection) {
        super("remove_all_by_weapon", "Команда для удаления всех записей с указанным оружием");
        this.collection = collection;
    }

    @Override
    public CommandResult action(Object[] params, String username) {
        WeaponType weaponType;
        try {
            weaponType = (WeaponType) params[0];
        } catch (Exception ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong data were eaten by program.");
        }

        collection.removeByWeapon(weaponType, username);

        return new CommandResult(ActionCode.OK);
    }

    @Override
    public CommandResult hostAction(String[] params) {
        WeaponType weaponType;
        try {
            if (params.length == 0)
                throw new WrongArgException();
            weaponType = WeaponType.valueOf(params[0]);
        } catch (Exception ex) {
            return new CommandResult(ActionCode.BAD_INPUT, String.format("Something went wrong (%s)", ex.getMessage()));
        }
        return action(new Object[]{weaponType}, "admin");
    }

    @Override
    public CommandResult hostAction(Object[] params) {
        return action(params, "admin");
    }
}
