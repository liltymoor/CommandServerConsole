package org.client;

import org.client.modules.RequestSender;

public class Main {
    public static void main(String[] args) {
        RequestSender requestSender = new RequestSender();
        requestSender.send("Hi from client | Рефрешер на бристлбэка");
    }
}