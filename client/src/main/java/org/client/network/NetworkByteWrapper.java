package org.client.network;

import org.client.commands.Command;

import java.io.*;
import java.nio.ByteBuffer;

public class NetworkByteWrapper {
    static ByteBuffer wrapRequest(Class<? extends Command> commandClass, Object[] args) {
        CommandArgsPair pair = new CommandArgsPair(commandClass, args);
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(pair);
            return ByteBuffer.wrap(bos.toByteArray());
        } catch (IOException e) { System.out.println("Неправильно нахуй"); }
        return null;
    }

    // TODO Вместо объекта сделать класс с Respons'ом
    static Object unwrapResponse(byte[] data) {
        try (ByteArrayInputStream bis = new ByteArrayInputStream(data);
             ObjectInputStream ois = new ObjectInputStream(bis)) {
            return ois.readObject();
        }
        catch (IOException e) { System.out.println("Неправильно нахуй"); }
        catch (ClassNotFoundException e) { System.out.format("Something wrong with data: %s%n", e.getMessage()); }

        return null;
    }
}
