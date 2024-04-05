package org.main.server.model.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author lil_timmie
 * Класс-помощник для сериализации {@link ZonedDateTime}.
 */
public class ZonedDTSerializer extends StdSerializer<ZonedDateTime> {
    public ZonedDTSerializer() {
        this(null);
    }
    public ZonedDTSerializer(Class<ZonedDateTime> t) {
        super(t);
    }

    @Override
    public void serialize(
            ZonedDateTime zonedDateTime,
            JsonGenerator jsonGenerator,
            SerializerProvider serializerProvider
    ) throws IOException {


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssXXX'['VV']'");
        String formattedString = zonedDateTime.format(formatter);
        jsonGenerator.writeString(formattedString);
    }
}
