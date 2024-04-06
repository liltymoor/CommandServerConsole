package org.main.server;

import org.main.server.commands.*;
import org.main.server.commands.managers.CommandHost;
import org.main.server.commands.managers.CommandInvoker;
import org.main.server.fs.CollectionIO;
import org.main.server.network.ConnectionReceiver;
import org.main.server.network.ResponseEmitter;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;

public class Main {
    public static int port = 8000;
    public static void main(String[] args) {
        CollectionIO collection = new CollectionIO();
        CommandHost cmndHost = new CommandHost();
        CommandInvoker invoker = new CommandInvoker(cmndHost);
        //TODO вместо инпутХендлера сделать обработчик Requestoв
        //InputHandler inputHandler = new InputHandler(cmndHost, invoker);

        cmndHost.addCommand(new HelpCommand(cmndHost));
        cmndHost.addCommand(new InfoCommand(collection));
        cmndHost.addCommand(new ShowCommand(collection));
        cmndHost.addCommand(new AddCommand(collection));
        cmndHost.addCommand(new UpdateCommand(collection));
        cmndHost.addCommand(new RemoveCommand(collection));
        cmndHost.addCommand(new ClearCommand(collection));
        cmndHost.addCommand(new SaveCommand(collection));
        //TODO вместо инпутХендлера сделать обработчик Requestoв
//        cmndHost.addCommand(new ExecuteScriptCommand(inputHandler, cmndHost));
        cmndHost.addCommand(new ExitCommand());
        cmndHost.addCommand(new AddIfMinCommand(collection));
        cmndHost.addCommand(new RemoveLowerCommand(collection));
        cmndHost.addCommand(new HistoryCommand(cmndHost));
        cmndHost.addCommand(new RemoveAllByWeaponCommand(collection));
        cmndHost.addCommand(new PrintUniqueImpactCommand(collection));

        DatagramChannel server;
        try {
            DatagramChannel channel = DatagramChannel.open();
            InetSocketAddress address = new InetSocketAddress("localhost", port);
            server = channel.bind(address);
        } catch (IOException e) { System.out.format("Ошибка при открытии сокета | %s\n", e); return;}

        ResponseEmitter emitter = new ResponseEmitter(server);
        ConnectionReceiver receiver = new ConnectionReceiver(invoker, emitter, server);

        while (true) {
            receiver.receive();
        }


    }
}