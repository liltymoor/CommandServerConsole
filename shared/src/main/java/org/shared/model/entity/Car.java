package org.shared.model.entity;

import org.shared.model.entity.params.Coordinates;

import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;

/**
 * @author lil_timmie
 * Класс Car содержит поле
 * name - имя автомобиля
 */
public class Car implements Serializable {
    public Car() {
        this.name = null;
    }
    public Car(String name) {
        this.name = name;
    }
    private String name; //Поле может быть null

    public String getName() {return name;}
    public void setName(String value) {
        name = value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Car otherCar = (Car) obj;
        return name.equals(otherCar.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
