package org.client.models;

import org.client.commands.managers.InputRule;
import org.client.commands.properties.InputCompoundable;
import org.shared.model.entity.Car;
import org.shared.model.entity.HumanBeing;
import org.shared.model.entity.params.Coordinates;
import org.shared.model.entity.params.Mood;
import org.shared.model.weapon.WeaponType;

import java.util.LinkedHashMap;

public class HumanBeingFormed extends HumanBeing {
    public HumanBeingFormed(String name, Coordinates coords, Boolean realHero, Boolean hasToothpick, Long impactSpeed, Double minutesOfWaiting, WeaponType weaponType, Mood mood, Car car) {
        super(name, coords, realHero, hasToothpick, impactSpeed, minutesOfWaiting, weaponType, mood, car);
    }

    public static LinkedHashMap<String, InputRule> getFieldsMapWithId() {
        LinkedHashMap<String, InputRule> fieldMap = new LinkedHashMap<>();

        fieldMap.put("id", new InputRule("") {
            @Override
            public boolean check(String value) {
                try {
                    Integer.parseInt(value);
                    return true;
                } catch (Exception e) {
                    return false;
                }
            }
        });

        fieldMap.putAll(getFieldsMap());
        return fieldMap;
    }

    public static LinkedHashMap<String, InputRule> getFieldsMap() {
        LinkedHashMap<String, InputRule> fieldMap = new LinkedHashMap<>();
        fieldMap.put("name", new InputRule("") {
            @Override
            public boolean check(String input) {
                return input != "";
            }
        });

        fieldMap.put("coords x", new InputRule("ex: 1.0") {
            @Override
            public boolean check(String input) {
                try {
                    if (input.isEmpty()) return true;
                    Float.parseFloat(input);
                    return true;
                } catch (Exception e) { return false; }
            }
        });

        fieldMap.put("coords y", new InputRule("ex: 0 (must be greater than -195)") {
            @Override
            public boolean check(String input) {
                try {
                    float y = Float.parseFloat(input);
                    return !(y <= -195);
                } catch (Exception e) { return false;}
            }
        });
        fieldMap.put("realHero", new InputRule("ex: true") {
            @Override
            public boolean check(String input) {
                try {
                    return input.equals("true") || input.equals("false");
                } catch (Exception ex) {
                    return false;
                }
            }
        });
        fieldMap.put("hasToothpick", new InputRule("ex: true") {
            @Override
            public boolean check(String input) {
                try {
                    return input.equals("true") || input.equals("false");
                } catch (Exception ex) {
                    return false;
                }
            }
        });
        fieldMap.put("impactSpeed", new InputRule("ex: 1000") {
            @Override
            public boolean check(String input) {
                try {
                    Long.parseLong(input);
                    return true;
                } catch (Exception ex) {
                    return false;
                }
            }
        });
        fieldMap.put("minutesOfWaiting", new InputRule("ex: 10.0") {
            @Override
            public boolean check(String input) {
                try {
                    Double.parseDouble(input);
                    return true;
                } catch (Exception ex) {
                    return false;
                }
            }
        });
        fieldMap.put("weaponType", new InputRule("one of: AXE, SHOTGUN, RIFLE, MACHINE GUN") {
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
        fieldMap.put("mood", new InputRule("one of: SADNESS, LONGING, GLOOM, APATHY, RAGE") {
            @Override
            public boolean check(String input) {
                try {
                    Mood.valueOf(input.toUpperCase());
                    return true;
                } catch (Exception ex) {
                    return false;
                }
            }
        });
        fieldMap.put("car", new InputRule("ex: Mercedes") {
            @Override
            public boolean check(String input) {
                return super.check(input);
            }
        });
        return fieldMap;
    }
}
