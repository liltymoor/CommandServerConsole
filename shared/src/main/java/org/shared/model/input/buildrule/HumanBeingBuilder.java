package org.shared.model.input.buildrule;

import org.shared.model.entity.Car;
import org.shared.model.entity.HumanBeing;
import org.shared.model.entity.params.Coordinates;
import org.shared.model.entity.params.Mood;
import org.shared.model.input.FieldStructure;
import org.shared.model.weapon.WeaponType;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.Scanner;

public class HumanBeingBuilder extends Builder<HumanBeing> {
    public HumanBeingBuilder() { name = "HumanBeing"; }

    private String getName(Scanner input, FieldStructure struct) {
        String userInput = ask(input, struct);
        userInput = checkNullable(userInput, struct);
        if (userInput == null) return null;

        while (true) {
            if (!userInput.isEmpty()) return userInput;
            incorrectMessage();
            userInput = ask(input, struct);
        }

    }

    private Coordinates getCoordinates(Scanner input, FieldStructure struct) {
        String userInput = ask(input, struct);
        userInput = checkNullable(userInput, struct);
        if (userInput == null) return null;

        Float x;
        Long y;

        while (true) {
            try {
                String[] splitted = userInput.split(" ");
                x = splitted[0].isEmpty() ? null : Float.parseFloat(splitted[0]);
                y = Long.parseLong(splitted[1]);
                if (y <= -190) throw new IOException();
                Coordinates coords = new Coordinates(x, y);
                return coords;
            } catch (Exception ex) {
                incorrectMessage();
                userInput = ask(input, struct);
            }
        }

    }

    private Boolean getRealHero(Scanner input, FieldStructure struct) {
        String userInput = ask(input, struct);
        userInput = checkNullable(userInput, struct);
        if (userInput == null) return null;

        while (true) {
            try {
                if (userInput.equals("true")) return true;
                if (userInput.equals("false")) return false;
                throw new IOException();
            } catch (Exception ex) {
                incorrectMessage();
                userInput = ask(input, struct);
            }
        }

    }

    private Boolean getHasToothpick(Scanner input, FieldStructure struct) {
        String userInput = ask(input, struct);
        userInput = checkNullable(userInput, struct);
        if (userInput == null) return null;

        while (true) {
            try {
                if (userInput.equals("true")) return true;
                if (userInput.equals("false")) return false;
                throw new IOException();
            } catch (Exception ex) {
                incorrectMessage();
                userInput = ask(input, struct);
            }
        }

    }

    private Long getImpactSpeed(Scanner input, FieldStructure struct) {
        String userInput = ask(input, struct);
        userInput = checkNullable(userInput, struct);
        if (userInput == null) return null;

        Long result;

        while (true) {
            try {
                result = Long.parseLong(userInput);
                return result;
            } catch (Exception ex) {
                incorrectMessage();
                userInput = ask(input, struct);
            }
        }
    }

    private Double getMinutesOfWaiting(Scanner input, FieldStructure struct) {
        String userInput = ask(input, struct);
        userInput = checkNullable(userInput, struct);
        if (userInput == null) return null;

        Double result;

        while (true) {
            try {
                result = Double.parseDouble(userInput);
                return result;
            } catch (Exception ex) {
                incorrectMessage();
                userInput = ask(input, struct);
            }
        }
    }

    private WeaponType getWeapongType(Scanner input, FieldStructure struct) {
        String userInput = ask(input, struct);
        userInput = checkNullable(userInput, struct);
        if (userInput == null) return null;

        WeaponType result;

        while (true) {
            try {
                result = WeaponType.valueOf(userInput.toUpperCase());
                return result;
            } catch (Exception ex) {
                incorrectMessage();
                userInput = ask(input, struct);
            }
        }
    }

    private Mood getMood(Scanner input, FieldStructure struct) {
        String userInput = ask(input, struct);
        userInput = checkNullable(userInput, struct);
        if (userInput == null) return null;

        Mood result;

        while (true) {
            try {
                result = Mood.valueOf(userInput.toUpperCase());
                return result;
            } catch (Exception ex) {
                incorrectMessage();
                userInput = ask(input, struct);
            }
        }
    }

    private Car getCar(Scanner input, FieldStructure struct) {
        String userInput = ask(input, struct);
        userInput = checkNullable(userInput, struct);
        if (userInput == null) return null;

        Car result;

        while (true) {
            try {
                result = new Car(userInput);
                return result;
            } catch (Exception ex) {
                incorrectMessage();
                userInput = ask(input, struct);
            }
        }
    }

    @Override
    public HumanBeing build(Scanner input) throws IOException {
        HumanBeing result = new HumanBeing(
                getName(input, new FieldStructure("name", "name of Human object | example: Vanya").setDefault("Vanya").notNullable()),
                getCoordinates(input, new FieldStructure("coordinates", "coordinates object(x y), y > -190 | example: 10 3").setDefault("10 3").notNullable()),
                getRealHero(input, new FieldStructure("realHero", "boolean | example: true").setDefault("true").notNullable()),
                getHasToothpick(input, new FieldStructure("hasToothpick", "boolean | example: true").setDefault("true").notNullable()),
                getImpactSpeed(input, new FieldStructure("impactSpeed", "example: 1000").setDefault("1000").notNullable()),
                getMinutesOfWaiting(input, new FieldStructure("minutesOfWaiting", " example: 3").setDefault("3").notNullable()),
                getWeapongType(input, new FieldStructure("weaponType", "one of: AXE, SHOTGUN, RIFLE, MACHINE GUN").setDefault("AXE").notNullable()),
                getMood(input, new FieldStructure("mood", "one of: SADNESS, LONGING, GLOOM, APATHY, RAGE").setDefault("SADNESS").notNullable()),
                getCar(input, new FieldStructure("car", "example: BMW").setDefault("BMW").notNullable())
        );
        result.setZonedDT(ZonedDateTime.now());
        return result;
    }
}
