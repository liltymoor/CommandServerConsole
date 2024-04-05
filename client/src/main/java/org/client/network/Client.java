package org.client.network;

import org.client.exceptions.ServerIsNullException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Client {
    final private int BUFFER_SIZE = 1024;
    final private DatagramChannel channel;
    final private InetSocketAddress serverAddress = new InetSocketAddress("localhost", 8000);

    private Client(DatagramChannel channel) {
        this.channel = channel;
    }

    public static Client startClient() throws IOException {
        // Получаем канал свободный канал, кста он возьмет случайный порт
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(false);
        return new Client(channel);
    }

    public void send(ByteBuffer buffer) {
        try {
            channel.send(buffer, serverAddress);
        } catch (IOException e) {
            System.out.format("Ошибка отправки: %s\n", e.getMessage());
        }

    }

    public Object sendAndGetResponse(ByteBuffer buffer) {
        send(buffer);
        // Получаем ответ от сервера
        try {
            return receiveResponse();
        } catch (IOException | ServerIsNullException e) {
            System.out.format("Ошибка получения ответа: %s\n", e.getMessage());
        }
        return null;
    }

    //TODO Переделать под класс Response
    private Object receiveResponse() throws IOException, ServerIsNullException {
        ByteBuffer receiveBuffer = ByteBuffer.allocate(BUFFER_SIZE);
        InetSocketAddress server = (InetSocketAddress) channel.receive(receiveBuffer);

        if (server == null) throw new ServerIsNullException();

        receiveBuffer.flip();
        byte[] data = new byte[receiveBuffer.limit()];
        receiveBuffer.get(data);
        return NetworkByteWrapper.unwrapResponse(data);
    }
}
