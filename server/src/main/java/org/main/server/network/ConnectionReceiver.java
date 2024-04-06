package org.main.server.network;

import org.main.server.commands.managers.CommandInvoker;
import org.main.server.commands.properties.CommandResult;
import org.shared.network.Request;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ConnectionReceiver {
    final private int BUFFER_SIZE = Request.REQUEST_SIZE;
    DatagramChannel server;
    CommandInvoker invoker;
    ResponseEmitter emitter;
    public ConnectionReceiver(CommandInvoker invoker, ResponseEmitter emitter, DatagramChannel server) {
        this.invoker = invoker;
        this.emitter = emitter;
        this.server = server;
    }

    public void receive() {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        try {
            SocketAddress clientRequest = server.receive(buffer);
            System.out.format("[REQUEST]: Received from %s:%s%n", (
                    (InetSocketAddress) clientRequest).getHostName(),
                    ((InetSocketAddress) clientRequest).getPort()
            );
            System.out.format("[REQUEST]: Packet size %s%n", buffer.remaining());
            Request request = RequestReader.read(buffer);
            System.out.format("[REQUEST]: Command: %s | Parameters: %s%n", request.getCommandName(), request.getArgs().length);

            CommandResult result = invoker.invoke(request.getCommandName(), request.getArgs());
            System.out.printf("[REQUEST]: Action code: %s | Result: %s%n", result.getCode(), result);
            emitter.emit(result, (InetSocketAddress) clientRequest);
        } catch (IOException e) {
            System.out.println("[ERROR]: " + e.getMessage());
        }

    }
}
