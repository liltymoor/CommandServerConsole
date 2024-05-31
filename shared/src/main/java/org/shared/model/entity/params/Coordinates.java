package org.shared.model.entity.params;

import org.shared.model.entity.HumanBeing;

import java.io.IOException;
import java.io.Serializable;

/**
 * @author lil_timmie
 * Класс, содержащий координаты x, y (y Должно быть больше -195)
 */
public class Coordinates implements Serializable {

    public Coordinates() {
        this.y = null;
    }

    public Coordinates(Float x, Long y) throws IOException {
        setX(x);
        setY(y);
    }

    public Coordinates(Long y) throws IOException {
        setY(y);
    }

    private Float x;
    private Long y; //Значение поля должно быть больше -195, Поле не может быть null


    public Float getX() {
        return x;
    }
    public Long getY() {
        return y;
    }

    public void setX(Float x) {
        this.x = x;
    }

    public void setY(Long y) throws IOException {
        if (y > -195)
            this.y = y;
        else
            throw new IOException();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordinates humanBeing = (Coordinates) obj;
        return x.equals(humanBeing.x) && y.equals(humanBeing.y);
    }
}
