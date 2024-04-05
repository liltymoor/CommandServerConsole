package org.client.modules;

import java.io.IOException;
import java.net.*;

public class RequestSender {
    private DatagramSocket socket;
    private InetAddress address;

    private byte[] buf;

    public RequestSender() {
        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName("localhost");
        }
        catch (SocketException e) { System.out.println("Unable to create socket"); }
        catch (UnknownHostException e) { System.out.println("Unable to get host address"); }
    }

    public void send(String message) {
        buf = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 8000);
        try { socket.send(packet); }
        catch (IOException e) { System.out.println("Unable to send message " + e.getMessage()); }
        socket.close();
    }


}
