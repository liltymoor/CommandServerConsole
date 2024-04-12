package org.main.server.network;

import org.shared.model.entity.HumanBeing;
import org.shared.network.Request;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class RequestReader {

    private static Request unwrapRequset(ByteBuffer buffer) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(buffer.array(), 0 , buffer.limit() );
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (Request) ois.readObject();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        catch (ClassNotFoundException e) { System.out.format("Something wrong with data: %s%n", e.getMessage()); }

        return null;
    }

    public static Request read(ByteBuffer buffer) {
        buffer.flip();
        Request request = unwrapRequset(buffer);
        buffer.flip();
        return request;
    }
}
