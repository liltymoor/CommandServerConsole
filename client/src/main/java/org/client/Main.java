package org.client;

import org.client.commands.*;
import org.client.commands.managers.CommandHost;
import org.client.commands.managers.CommandInvoker;
import org.client.commands.managers.InputHandler;
import org.client.exceptions.CommandNotFoundException;
import org.client.network.Client;

public class Main {
    public static void main(String[] args) {
        Client client = null;
        try {  client = Client.startClient(); }
        catch (Exception e) { System.out.format("Can't start client: %s\n", e.getMessage()); return; }
        CommandHost cmndHost = new CommandHost();
        CommandInvoker invoker = new CommandInvoker(cmndHost);
        InputHandler inputHandler = new InputHandler(cmndHost, invoker);

        cmndHost.addCommand(new HelpCommand(cmndHost));
        cmndHost.addCommand(new InfoCommand(client));
        cmndHost.addCommand(new ShowCommand(client));
        cmndHost.addCommand(new AddCommand(client));
        cmndHost.addCommand(new UpdateCommand(client));
        cmndHost.addCommand(new RemoveCommand(client));
        cmndHost.addCommand(new ClearCommand(client));
        //cmndHost.addCommand(new SaveCommand());
        cmndHost.addCommand(new ExecuteScriptCommand(inputHandler, cmndHost));
        cmndHost.addCommand(new ExitCommand(new SaveCommand(), client));
        cmndHost.addCommand(new AddIfMinCommand(client));
        cmndHost.addCommand(new RemoveLowerCommand(client));
        cmndHost.addCommand(new HistoryCommand(cmndHost, client));
        cmndHost.addCommand(new RemoveAllByWeaponCommand(client));
        cmndHost.addCommand(new PrintUniqueImpactCommand(client));

        invoker.invoke(cmndHost.getCommands().get("help"));
        while (true) {
            try {
                inputHandler.handleCommand();
            } catch (CommandNotFoundException e) {
                System.out.println(e.getMessage());
            }
            System.out.println();
        }

    }
}