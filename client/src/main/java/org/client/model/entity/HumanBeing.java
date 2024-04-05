package org.client.model.entity;

import org.client.commands.managers.InputRule;
import org.client.model.entity.params.Coordinates;
import org.client.model.entity.params.Mood;
import org.client.model.weapon.WeaponType;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;

/**
 * @author lil_timmie
 * Класс-модель, вокруг которой построена работа всего Json файла и командной строки.
 *
 * Класс-модель имеет следующие поля:
 * id - уникальный идентификатор (заполняется автоматически)
 * name - имя персонажа
 * coords - координаты {@link Coordinates}
 * zonedDT - дата и время {@link ZonedDateTime}
 * realHero - реальный персонаж (boolean)
 * hasToothpick - есть ли у персонажа зубочистка (boolean)
 * impactSpeed - скорость урона персонажа ( Long, по данному параметру сравниваются персонажи )
 * minutesOfWaiting - минуты ожидания персонажа
 * weaponType - тип оружия персонажа {@link WeaponType}
 * mood - настроение персонажа {@link Mood}
 * modelCar - модель автомобиля {@link Car}
 */


public class HumanBeing implements Comparable<HumanBeing> {
    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Coordinates coords; //Поле не может быть null
    private ZonedDateTime zonedDT; //Поле не может быть null, Значение этого поля должно генерироваться автоматически

    public ZonedDateTime getZonedDT() {
        return zonedDT;
    }

    public void setCreationDate(ZonedDateTime zonedDT) {
        this.zonedDT = zonedDT;
    }

    private Boolean realHero; //Поле не может быть null
    private Boolean hasToothpick; //Поле может быть null
    private Long impactSpeed; //Поле не может быть null
    private Double minutesOfWaiting; //Поле может быть null
    private WeaponType weaponType; //Поле не может быть null
    private Mood mood; //Поле не может быть null
    private Car modelCar; //Поле не может быть null

    public HumanBeing(
            String name,
            Coordinates coords,
            Boolean realHero,
            Boolean hasToothpick,
            Long impactSpeed,
            Double minutesOfWaiting,
            WeaponType weaponType,
            Mood mood,
            Car car
    ) {
        id = -1;
        setName(name);
        this.coords = coords;
        setCreationDate(ZonedDateTime.now());
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.minutesOfWaiting = minutesOfWaiting;
        this.weaponType = weaponType;
        this.mood = mood;
        this.modelCar = car;
    }

    public HumanBeing(
            String name,
            Coordinates coords,
            ZonedDateTime zonedDT,
            Boolean realHero,
            Boolean hasToothpick,
            Long impactSpeed,
            Double minutesOfWaiting,
            WeaponType weaponType,
            Mood mood,
            Car car
    ) {
        id = -1;
        setName(name);
        this.coords = coords;
        setCreationDate(zonedDT);
        this.realHero = realHero;
        this.hasToothpick = hasToothpick;
        this.impactSpeed = impactSpeed;
        this.minutesOfWaiting = minutesOfWaiting;
        this.weaponType = weaponType;
        this.mood = mood;
        this.modelCar = car;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Boolean getRealHero() {
        return realHero;
    }

    public Boolean getHasToothpick() {
        return hasToothpick;
    }

    public Long getImpactSpeed() {
        return impactSpeed;
    }

    public Double getMinutesOfWaiting() {
        return minutesOfWaiting;
    }

    public WeaponType getWeaponType() {
        return weaponType;
    }

    public Mood getMood() {
        return mood;
    }

    public Car getModelCar() {
        return modelCar;
    }

    public Coordinates getCoords() {
        return coords;
    }

    @Override
    public int compareTo(HumanBeing o) {
        return Long.compare(getImpactSpeed(), o.getImpactSpeed());
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX'['VV']'");
        String formattedString = zonedDT.format(formatter);
        return ("HumanBeing | " +
                "id: %d, " +
                "name: %s, " +
                "x: %f, y: %d, " +
                "creationDate: %s, " +
                "realHero: %s, " +
                "hasToothpick: %s, " +
                "impactSpeed: %d, " +
                "minutesOfWaiting: %f, " +
                "weaponType: %s, " +
                "mood: %s, " +
                "modelCar: %s").formatted(
                id, name,
                coords.getX(), coords.getY(),
                formattedString,
                realHero.toString(),
                hasToothpick.toString(),
                impactSpeed,
                minutesOfWaiting,
                weaponType.toString(),
                mood.toString(),
                modelCar.getName()
        );
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
