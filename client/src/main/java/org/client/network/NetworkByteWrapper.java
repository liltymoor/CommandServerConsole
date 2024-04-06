package org.client.network;

import org.client.commands.Command;
import org.shared.network.Request;
import org.shared.network.Response;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class NetworkByteWrapper {
    public static ByteBuffer wrapRequest(Command command, Object[] args) {
        Request request = new Request(command.getName(), args);
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(request);
            oos.flush();
            return ByteBuffer.wrap(bos.toByteArray());
        } catch (Exception e) { System.out.println(e.getMessage()); System.out.println(Arrays.toString(e.getStackTrace())); }
        return null;
    }

    // TODO Вместо объекта сделать класс с Respons'ом
    public static Response unwrapResponse(ByteBuffer buffer) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(buffer.array(), 0 , buffer.limit() );
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return (Response) ois.readObject();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
            System.out.println(Arrays.toString(e.getStackTrace()));
        }
        catch (ClassNotFoundException e) { System.out.format("Something wrong with data: %s%n", e.getMessage()); }

        return null;
    }
}
