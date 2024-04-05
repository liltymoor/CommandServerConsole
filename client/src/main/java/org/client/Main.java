package org.client;

import org.client.commands.*;
import org.client.commands.managers.CommandHost;
import org.client.commands.managers.CommandInvoker;
import org.client.commands.managers.InputHandler;
import org.client.exceptions.CommandNotFoundException;
import org.client.modules.RequestSender;

public class Main {
    public static void main(String[] args) {
        CommandHost cmndHost = new CommandHost();
        CommandInvoker invoker = new CommandInvoker(cmndHost);
        InputHandler inputHandler = new InputHandler(cmndHost, invoker);

        cmndHost.addCommand(new HelpCommand(cmndHost));
        cmndHost.addCommand(new InfoCommand());
        cmndHost.addCommand(new ShowCommand());
        cmndHost.addCommand(new AddCommand());
        cmndHost.addCommand(new UpdateCommand());
        cmndHost.addCommand(new RemoveCommand());
        cmndHost.addCommand(new ClearCommand());
        //cmndHost.addCommand(new SaveCommand());
        cmndHost.addCommand(new ExecuteScriptCommand(inputHandler, cmndHost));
        cmndHost.addCommand(new ExitCommand());
        cmndHost.addCommand(new AddIfMinCommand());
        cmndHost.addCommand(new RemoveLowerCommand());
        cmndHost.addCommand(new HistoryCommand(cmndHost));
        cmndHost.addCommand(new RemoveAllByWeaponCommand());
        cmndHost.addCommand(new PrintUniqueImpactCommand());

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