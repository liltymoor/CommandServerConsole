package org.client.network;

import org.client.exceptions.ServerIsNullException;
import org.shared.network.ChunkInfo;
import org.shared.network.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class ClientUDP {
    final private DatagramChannel channel;
    final private ChunkCollector collector;
    private String token = "Not stated";
    final private InetSocketAddress serverAddress = new InetSocketAddress("localhost", 8000);

    private ClientUDP(DatagramChannel channel) {
        this.channel = channel;
        collector = new ChunkCollector();
    }

    public static ClientUDP startClient() throws IOException {
        // System.out.printf("Going to udp him: %s:%s%n", "172.18.0.2", 8000);
        // Получаем канал свободный канал, кста он возьмет случайный порт
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(true);
        return new ClientUDP(channel);
    }

    public void send(ByteBuffer buffer) {
        try {
            channel.send(buffer, serverAddress);
            System.out.println("[CLIENT] Request successfully sent");
        } catch (IOException e) {
            System.out.format("Error sending request: %s\n", e.getMessage());
        }

    }

    public Response sendAndGetResponse(ByteBuffer buffer) {
        send(buffer);
        // Получаем ответ от сервера
        try {
            return receiveResponse();
        } catch (IOException | ServerIsNullException e) {
            System.out.format("Ошибка получения ответа: %s\n", e.getMessage());
        }
        return null;
    }

    private Response receiveResponse() throws IOException, ServerIsNullException {
        ByteBuffer buffer = ByteBuffer.allocate(Response.RESPONSE_SIZE);
        System.out.println("[CLIENT] Waiting for server answer...");
        InetSocketAddress serverAnswer = (InetSocketAddress) channel.receive(buffer);

        if (serverAnswer == null) throw new ServerIsNullException();
        buffer.flip();

        ChunkInfo info = NetworkByteWrapper.unwrapChunkInfo(buffer);
        collector.prepare(info);
        buffer.clear();

        while (!collector.isReady()) {
            channel.receive(buffer);
            buffer.flip();

            collector.concatChunk(buffer);

            buffer.clear();
        }
        return NetworkByteWrapper.unwrapResponse(collector.getData());
    }

    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
