package org.main.server;

import org.main.server.commands.*;
import org.main.server.commands.managers.CommandHost;
import org.main.server.commands.managers.CommandInvoker;
import org.main.server.commands.managers.InputHandler;
import org.main.server.fs.CollectionIO;
import org.main.server.network.ConnectionReceiver;
import org.main.server.network.ResponseEmitter;
import org.shared.network.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

public class Main {
    public static int port = 8000;
    public static void main(String[] args) {
        CollectionIO collection = new CollectionIO();
        CommandHost cmndHost = new CommandHost();
        CommandInvoker invoker = new CommandInvoker(cmndHost);
        InputHandler inputHandler = new InputHandler(cmndHost, invoker);

        cmndHost.addCommand(new HelpCommand(cmndHost));
        cmndHost.addCommand(new InfoCommand(collection));
        cmndHost.addCommand(new ShowCommand(collection));
        cmndHost.addCommand(new AddCommand(collection));
        cmndHost.addCommand(new UpdateCommand(collection));
        cmndHost.addCommand(new RemoveCommand(collection));
        cmndHost.addCommand(new ClearCommand(collection));
        cmndHost.addCommand(new SaveCommand(collection));
        cmndHost.addCommand(new ExitCommand());
        cmndHost.addCommand(new AddIfMinCommand(collection));
        cmndHost.addCommand(new RemoveLowerCommand(collection));
        cmndHost.addCommand(new HistoryCommand(cmndHost));
        cmndHost.addCommand(new RemoveAllByWeaponCommand(collection));
        cmndHost.addCommand(new PrintUniqueImpactCommand(collection));

        DatagramChannel server;
        try {
            DatagramChannel channel = DatagramChannel.open();
            channel.configureBlocking(false);
            InetSocketAddress address = new InetSocketAddress("localhost", port);
            server = channel.bind(address);
        } catch (IOException e) { System.out.format("Ошибка при открытии сокета | %s\n", e); return;}

        ResponseEmitter emitter = new ResponseEmitter(server);
        ConnectionReceiver receiver = new ConnectionReceiver(invoker, emitter, server);

        Thread inputThread = new Thread(() -> {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
                while (!Thread.currentThread().isInterrupted()) {
                    if (reader.ready()) {
                        inputHandler.handleCommand();
                    } else {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt(); // Восстанавливаем флаг прерывания
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        inputThread.start();
        ByteBuffer buffer = ByteBuffer.allocate(Request.REQUEST_SIZE);
        while (true) {
            receiver.receive(buffer);
        }


    }
}