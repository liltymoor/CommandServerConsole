package org.main.server.commands;

import org.main.server.commands.managers.InputRule;
import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.properties.InputCompoundable;
import org.main.server.exceptions.WrongArgException;
import org.main.server.fs.CollectionIO;
import org.shared.model.weapon.WeaponType;

import java.util.LinkedHashMap;

public class RemoveAllByWeaponCommand extends Command {
    CollectionIO collection;
    public RemoveAllByWeaponCommand(CollectionIO collection) {
        super("remove_all_by_weapon", "Команда для удаления всех записей с указанным оружием");
        this.collection = collection;
    }

    @Override
    public CommandResult action(Object[] params) {
        WeaponType weaponType;
        try {
            weaponType = (WeaponType) params[0];
        }  catch (Exception ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Wrong data were eaten by program.");
        }

        collection.removeByWeapon(weaponType);

        return new CommandResult(ActionCode.OK);
    }
}
