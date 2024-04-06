package org.main.server.network;

import org.main.server.commands.properties.CommandResult;
import org.shared.network.Response;

import javax.xml.crypto.Data;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ResponseEmitter {
    DatagramChannel server;
    public ResponseEmitter(DatagramChannel server) {
        this.server = server;
    }

    private ByteBuffer wrapResponse(CommandResult result) {
        Response response = new Response(result.getCode().toString(), result.toString());
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(response);
            oos.flush();
            return ByteBuffer.wrap(bos.toByteArray());
        } catch (IOException e) { System.out.printf("[RESPONSE]: Error Wrapping: %s%n", e.getMessage()); }
        return null;
    }

    public void emit(CommandResult result, InetSocketAddress clientAddress) {
        ByteBuffer buffer = wrapResponse(result);
        try {
            server.send(buffer, clientAddress);
            System.out.printf("[RESPONSE]: Sent back to %s:%s%n%n",clientAddress.getHostName(), clientAddress.getPort());
        }
        catch (IOException e) { System.out.printf("[RESPONSE]: Error Sending: %s%n%n", e.getMessage()); }
    }
}
