package org.client;

import org.client.commands.*;
import org.client.commands.managers.CommandHost;
import org.client.commands.managers.CommandInvoker;
import org.client.commands.managers.InputHandler;
import org.client.exceptions.CommandNotFoundException;
import org.client.network.ClientUDP;

import java.util.Arrays;
import java.util.logging.Logger;

public class ClientAppBackend {
    private Logger backendLogger = Logger.getLogger("backendLogger");
    private CommandHost commandHost;
    private CommandInvoker commandInvoker;
    private ClientUDP client;

    public ClientAppBackend() {
        backendLogger.info("Starting ClientAppBackend...");
        commandHost = new CommandHost();
        commandInvoker = new CommandInvoker(commandHost);
        client = initNetworkClient();

        if (initCommands()) backendLogger.info("Commands initialized successfully");
        else backendLogger.warning("Commands wasn't initialized");

    }

    private ClientUDP initNetworkClient() {
        ClientUDP client;
        try { client = ClientUDP.startClient(); return client; }
        catch (Exception e) { backendLogger.warning("Client wasn't started | Reason: %s".formatted(e.getMessage())); return null; }
    }

    private boolean initCommands() {
        if (commandHost == null) return false;
        backendLogger.info("Initializing local commands...");
        commandHost.addCommand(new HelpCommand(commandHost));

        if (client == null) return false;
        backendLogger.info("Initializing server commands...");
        commandHost.addCommand(new HelpCommand(commandHost));
        commandHost.addCommand(new InfoCommand(client));
        commandHost.addCommand(new ShowCommand(client));
        commandHost.addCommand(new AddCommand(client));
        commandHost.addCommand(new UpdateCommand(client));
        commandHost.addCommand(new RemoveCommand(client));
        commandHost.addCommand(new ClearCommand(client));
        //commandHost.addCommand(new SaveCommand());
        //commandHost.addCommand(new ExecuteScriptCommand(inputHandler, commandHost));
        commandHost.addCommand(new ExitCommand(new SaveCommand(), client));
        commandHost.addCommand(new AddIfMinCommand(client));
        commandHost.addCommand(new RemoveLowerCommand(client));
        commandHost.addCommand(new HistoryCommand(commandHost, client));
        commandHost.addCommand(new RemoveAllByWeaponCommand(client));
        commandHost.addCommand(new PrintUniqueImpactCommand(client));
        commandHost.addCommand(new AuthCommand(client));
        return true;
    }

    public void invokeCommand(String stringCommand, Object... args) {
        Command command = commandHost.getCommands().get(stringCommand);
        try { commandInvoker.invoke(command, args); }
        catch (CommandNotFoundException e) { backendLogger.warning("Command not found"); }
    }


//    public static void main(String[] args) {
//        ClientUDP client = null;
//        try {  client = ClientUDP.startClient(); }
//        catch (Exception e) { System.out.format("Can't start client: %s\n", e.getMessage()); return; }
//        CommandHost cmndHost = new CommandHost();
//        CommandInvoker invoker = new CommandInvoker(cmndHost);
//        InputHandler inputHandler = new InputHandler(cmndHost, invoker);
//
//        cmndHost.addCommand(new HelpCommand(cmndHost));
//        cmndHost.addCommand(new InfoCommand(client));
//        cmndHost.addCommand(new ShowCommand(client));
//        cmndHost.addCommand(new AddCommand(client));
//        cmndHost.addCommand(new UpdateCommand(client));
//        cmndHost.addCommand(new RemoveCommand(client));
//        cmndHost.addCommand(new ClearCommand(client));
//        //cmndHost.addCommand(new SaveCommand());
//        cmndHost.addCommand(new ExecuteScriptCommand(inputHandler, cmndHost));
//        cmndHost.addCommand(new ExitCommand(new SaveCommand(), client));
//        cmndHost.addCommand(new AddIfMinCommand(client));
//        cmndHost.addCommand(new RemoveLowerCommand(client));
//        cmndHost.addCommand(new HistoryCommand(cmndHost, client));
//        cmndHost.addCommand(new RemoveAllByWeaponCommand(client));
//        cmndHost.addCommand(new PrintUniqueImpactCommand(client));
//        cmndHost.addCommand(new AuthCommand(client));
//
//        invoker.invoke(cmndHost.getCommands().get("help"));
//        while (true) {
//            try {
//                inputHandler.handleCommand();
//            } catch (CommandNotFoundException e) {
//                System.out.println(e.getMessage());
//            }
//            System.out.println();
//        }
//
//    }
}