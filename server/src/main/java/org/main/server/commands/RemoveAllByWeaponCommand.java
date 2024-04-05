package org.main.server.commands;

import org.main.server.commands.managers.InputRule;
import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.properties.InputCompoundable;
import org.main.server.exceptions.WrongArgException;
import org.main.server.fs.CollectionIO;
import org.main.server.model.weapon.WeaponType;

import java.util.LinkedHashMap;

public class RemoveAllByWeaponCommand extends Command implements InputCompoundable {
    CollectionIO collection;
    public RemoveAllByWeaponCommand(CollectionIO collection) {
        super("remove_all_by_weapon", "Команда для удаления всех записей с указанным оружием");
        this.collection = collection;
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

        collection.removeByWeapon(weaponType);

        return new CommandResult(ActionCode.OK);
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