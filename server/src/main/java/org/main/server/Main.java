package org.main.server;

import org.main.server.commands.*;
import org.main.server.commands.managers.AuthManager;
import org.main.server.commands.managers.CommandHost;
import org.main.server.commands.managers.CommandInvoker;
import org.main.server.commands.managers.InputHandler;
import org.main.server.fs.CollectionIO;
import org.main.server.fs.HumanDatabaseHelper;
import org.main.server.network.Server;
import org.main.server.network.ResponseEmitter;
import org.shared.network.Request;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static int port = 8000;

    public static ExecutorService pool = Executors.newCachedThreadPool();

    public static void inputHandle(InputHandler inputHandler) {
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
    }

    public static void main(String[] args) {

        // MANAGERS INIT

        CommandHost cmndHost = new CommandHost();
        CommandInvoker invoker = new CommandInvoker(cmndHost);
        InputHandler inputHandler = new InputHandler(cmndHost, invoker);

        CollectionIO collection = new CollectionIO(HumanDatabaseHelper.getDatabaseHelper());

        // NETWORK TOOLS INIT

        DatagramChannel channel;
        try {
            DatagramChannel dtChannel = DatagramChannel.open();
            dtChannel.configureBlocking(false);
            InetSocketAddress address = new InetSocketAddress("localhost", port);
            channel = dtChannel.bind(address);
        } catch (IOException e) {
            System.out.format("Ошибка при открытии сокета | %s\n", e);
            return;
        }

        ResponseEmitter emitter = new ResponseEmitter(channel);
        AuthManager authManager = new AuthManager();

        Server server = new Server(invoker, emitter, channel, authManager);

        // COMMANDS INIT

        cmndHost.addCommand(new HelpCommand(cmndHost));
        cmndHost.addCommand(new InfoCommand(collection));
        cmndHost.addCommand(new ShowCommand(collection));
        cmndHost.addCommand(new AddCommand(collection));
        cmndHost.addCommand(new CountGreaterThanImpactCommand(collection));
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
        cmndHost.addCommand(new RegisterCommand(collection));
        cmndHost.addCommand(new AuthCommand(collection));
        cmndHost.addCommand(new GetHumansCommand(collection));

        // INVOKING COMMAND "HELP" FOR HELP TO START

        invoker.invoke("help", "admin");

        // THREAD THAT READS FROM CONSOLE

        Thread inputThread = new Thread(() -> {
            inputHandle(inputHandler);
        });

        inputThread.start();

        // REQUEST RECEIVER
        // TODO С ПОМОЩЬЮ CACHED THREAD POOL
        server.listen();


    }
}