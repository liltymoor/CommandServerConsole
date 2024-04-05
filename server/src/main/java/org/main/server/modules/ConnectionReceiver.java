package org.main.server.modules;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class ConnectionReceiver {
    private DatagramSocket socket;
    private byte[] buffer = new byte[1024];
    public ConnectionReceiver(int port) {
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            System.out.format("Порт %s не найден%n", port);
        }
    }

    public void receive() {
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        try { socket.receive(packet); }
        catch (IOException e) { System.out.println("Ошибка при получении пакета | " + e.getMessage()); }

        // received
        InetAddress address = packet.getAddress();
        int port = packet.getPort();
        packet = new DatagramPacket(buffer, buffer.length, address, port);

        String received = new String(packet.getData(), 0, packet.getLength());
        //System.out.format("Получено сообщение от %s:%s%n", address.getHostName(), port);
        System.out.format("Сообщение: %s%n", received);
    }
}
