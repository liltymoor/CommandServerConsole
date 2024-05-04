package org.main.server.network;

import org.main.server.commands.managers.AuthManager;
import org.main.server.commands.managers.CommandInvoker;
import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.shared.network.Request;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.*;

public class Server {
    final private int BUFFER_SIZE = Request.REQUEST_SIZE;
    private final DatagramChannel channel;
    private final CommandInvoker invoker;
    private final ResponseEmitter emitter;
    private final AuthManager authManager;

    private final ExecutorService requestPool = Executors.newCachedThreadPool();
    private final BlockingQueue<String> commandQueue = new LinkedBlockingQueue<>();
    private final ForkJoinPool responsePool = new ForkJoinPool();

    public class requestThread implements Runnable {
        private final InetSocketAddress clientRequest;
        private final ByteBuffer buffer;

        private requestThread(InetSocketAddress clientRequest, ByteBuffer buffer) {
            this.clientRequest = new InetSocketAddress(
                    clientRequest.getAddress(),
                    clientRequest.getPort()
            );
            this.buffer = buffer.duplicate();
        }

        @Override
        public void run() {
            Request request = RequestReader.read(buffer);
            System.out.format("[REQUEST]: Command: %s | Parameters: %s%n", request.getCommandName(), request.getArgs().length);

            String username = authManager.authenticate(request.getUserToken());

            Thread requestJob = new Thread(() -> {
                CommandResult result = invoker.invoke(request.getCommandName(), request.getArgs(), username);
                System.out.printf("[REQUEST]: Action code: %s | Result: %s%n", result.getCode(), result);
                responsePool.execute(() -> {
                    emitter.emit(result, clientRequest);
                });
            });
            requestJob.start();
        }
    }


    public Server(CommandInvoker invoker, ResponseEmitter emitter, DatagramChannel channel, AuthManager authManager) {
        this.invoker = invoker;
        this.emitter = emitter;
        this.channel = channel;
        this.authManager = authManager;
    }

    public void listen() {
        ByteBuffer buffer = ByteBuffer.allocate(Request.REQUEST_SIZE);

        while (true) {
            buffer.clear();
            try {
                // Listening till the request comes
                SocketAddress clientRequest = channel.receive(buffer);
                if (clientRequest == null) continue;
                System.out.format("[REQUEST]: Received from %s:%s%n", (
                                (InetSocketAddress) clientRequest).getHostName(),
                        ((InetSocketAddress) clientRequest).getPort()
                );
                System.out.format("[REQUEST]: Packet size %s%n", buffer.remaining());

                Runnable requestThread = new requestThread((InetSocketAddress) clientRequest, buffer);
                requestPool.execute(requestThread);

            } catch (IOException e) {
                System.out.println("[ERROR]: " + e.getMessage());
            }
        }
    }
}
