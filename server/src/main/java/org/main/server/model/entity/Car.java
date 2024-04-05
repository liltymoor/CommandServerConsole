package org.main.server.model.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * @author lil_timmie
 * Класс Car содержит поле
 * name - имя автомобиля
 */
@JsonDeserialize(using = CarDeserializer.class)
public class Car {
    public Car() {
        this.name = null;
    }
    public Car(String name) {
        this.name = name;
    }
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name; //Поле может быть null

    public String getName() {return name;}
    public void setName(String value) {
        name = value;
    }

}

/**
 * @author lil_timmie
 * Класс-помощник для десериализации {@link Car}
 */
class CarDeserializer extends StdDeserializer<Car> {
    public CarDeserializer() {
        this(null);
    }

    public CarDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Car deserialize(
            JsonParser jsonParser,
            DeserializationContext deserializationContext
    ) throws IOException, JacksonException {
        ObjectCodec codec = jsonParser.getCodec();
        JsonNode node = codec.readTree(jsonParser);
        if (node == null)
            return null;

        JsonNode nodeName = node.get("name");
        String name = null;

        if (nodeName != null)
            name = nodeName.asText();

        return new Car(name);
    }
}
