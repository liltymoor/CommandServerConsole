package org.shared.model.entity;

import java.io.IOException;
import java.io.Serializable;

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

}
