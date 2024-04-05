package org.main.server;

import org.main.server.modules.ConnectionReceiver;

public class Main {
    public static void main(String[] args) {
        ConnectionReceiver reciever = new ConnectionReceiver(8000);
        while (true) {
            reciever.receive();
        }
    }
}