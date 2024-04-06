package org.client.network;

import org.client.exceptions.ServerIsNullException;
import org.shared.network.Request;
import org.shared.network.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Client {
    final private DatagramChannel channel;
    final private InetSocketAddress serverAddress = new InetSocketAddress("localhost", 8000);

    private Client(DatagramChannel channel) {
        this.channel = channel;
    }

    public static Client startClient() throws IOException {
        // Получаем канал свободный канал, кста он возьмет случайный порт
        DatagramChannel channel = DatagramChannel.open();
        channel.configureBlocking(true);
        return new Client(channel);
    }

    public void send(ByteBuffer buffer) {
        try {
            channel.send(buffer, serverAddress);
            System.out.println("Запрос успешно отправлен");
        } catch (IOException e) {
            System.out.format("Ошибка отправки: %s\n", e.getMessage());
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

    //TODO Переделать под класс Response
    private Response receiveResponse() throws IOException, ServerIsNullException {
        ByteBuffer receiveBuffer = ByteBuffer.allocate(Response.RESPONSE_SIZE);
        System.out.println("Ждем ответ от сервера...");
        InetSocketAddress serverAnswer = (InetSocketAddress) channel.receive(receiveBuffer);

        if (serverAnswer == null) throw new ServerIsNullException();
        receiveBuffer.flip();
        return NetworkByteWrapper.unwrapResponse(receiveBuffer);
    }
}
