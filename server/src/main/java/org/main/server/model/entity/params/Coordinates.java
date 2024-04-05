package org.main.server.model.entity.params;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.main.server.exceptions.BadLogicWereEaten;

/**
 * @author lil_timmie
 * Класс, содержащий координаты x, y (y Должно быть больше -195)
 */
public class Coordinates {

    public Coordinates() {
        this.y = null;
    }

    public Coordinates(Float x, Long y) throws BadLogicWereEaten{
        setX(x);
        setY(y);
    }

    public Coordinates(Long y) throws BadLogicWereEaten {
        setY(y);
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Float x;

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        this.x = x;
    }

    private Long y; //Значение поля должно быть больше -195, Поле не может быть null

    public void setY(Long y) throws BadLogicWereEaten {
        if (y > -195)
            this.y = y;
        else
            throw new BadLogicWereEaten();
    }

    public Long getY() {
        return y;
    }
}
