package org.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.collections.ObservableSet;
import org.client.commands.*;
import org.client.commands.managers.CommandHost;
import org.client.commands.managers.CommandInvoker;
import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.commands.properties.DataProvidedCommandResult;
import org.client.commands.types.Command;
import org.client.commands.types.DataProvidableServerCommand;
import org.client.exceptions.CommandNotFoundException;
import org.client.network.ClientUDP;
import org.shared.model.entity.HumanBeing;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ClientAppBackend {
    private Logger backendLogger = Logger.getLogger("backendLogger");
    private CommandHost commandHost;
    private CommandInvoker commandInvoker;
    private ClientUDP client;

    private ObservableList<HumanBeing> localCollection;

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
        commandHost.addCommand(new RegisterCommand(client));
        commandHost.addCommand(new GetHumansCommand(client));
        commandHost.addCommand(new SyncCommand(client));
        return true;
    }

    public CommandResult invokeCommand(String stringCommand, Object... args) {
        Command command = commandHost.getCommands().get(stringCommand);
        try { return commandInvoker.invoke(command, args); }
        catch (CommandNotFoundException e) { backendLogger.warning("Command not found"); }

        return new CommandResult(ActionCode.ERROR, "Command not found");
    }

    public <T> DataProvidedCommandResult<T> callCommand(String stringCommand, Object... args) {
        try {
            DataProvidableServerCommand<T> command = (DataProvidableServerCommand<T>) commandHost.getCommands().get(stringCommand);
            return commandInvoker.call(command, args);
        }
        catch (ClassCastException e) { backendLogger.warning("Command is not callable, but invokable"); }
        catch (CommandNotFoundException e) { backendLogger.warning("Command not found"); }

        return new DataProvidedCommandResult<>(ActionCode.ERROR, null);
    };

    private void completeCollection(LinkedHashSet<HumanBeing> remoteCollection) {
        try {
            localCollection.clear();
            localCollection.addAll(remoteCollection);
        } catch (IllegalStateException ex) { }
    }

    public ObservableList<HumanBeing> syncCollection() {
        if (localCollection == null) setupCollection();

        CommandResult syncResult = invokeCommand("sync", localCollection.stream().toList());
        if (syncResult.getCode() != ActionCode.OK) {
            DataProvidedCommandResult<LinkedHashSet<HumanBeing>> getResult = callCommand("get_humans");
            completeCollection(getResult.getData());
        }
        return localCollection;
    }

    private ObservableList<HumanBeing> setupCollection() {
        if (localCollection != null) {
            syncCollection();
            return localCollection;
        }
        DataProvidedCommandResult<LinkedHashSet<HumanBeing>> reqResult = callCommand("get_humans");
        localCollection = FXCollections.observableArrayList(reqResult.getData().stream().toList());
        return localCollection;
    }

    public ObservableList<HumanBeing> getLocalCollection() {
        if (localCollection == null) setupCollection();
        // todo возможно добавить сюда проверку на время последнего обновления и добавить sync
        return localCollection;
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