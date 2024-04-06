package org.client.commands;

import org.client.commands.managers.CommandHost;
import org.client.commands.managers.InputHandler;
import org.client.commands.properties.ActionCode;
import org.client.commands.properties.CommandResult;
import org.client.exceptions.CommandNotFoundException;
import org.client.exceptions.MaxScriptDepthReachedException;
import org.client.exceptions.WrongArgException;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ExecuteScriptCommand extends Command {
    InputHandler inputHandler;
    CommandHost commandHost;

    public ExecuteScriptCommand(InputHandler inputHandler, CommandHost host) {
        super("execute_script", "Комадна  для выполнения скрипта");
        this.inputHandler = inputHandler;
        this.commandHost = host;
    }

    @Override
    public CommandResult action(String[] params) {
        try {
            if (params.length != 1)
                throw new WrongArgException();
        } catch (WrongArgException ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Please, provide script name.");
        }

        String scriptName = params[0];
        String script = "";

        try {
            FileInputStream fileInputStream = new FileInputStream(scriptName);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            script = new String(bufferedInputStream.readAllBytes(), StandardCharsets.UTF_8);
            bufferedInputStream.close();
            fileInputStream.close();
        } catch (Exception ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Can't open file " + scriptName);
        }


        try {
            List<String> history = commandHost.getHistory();
            if (history.get(history.size() - 1).equals("execute_script"))
                commandHost.increaseDepth();
        } catch (MaxScriptDepthReachedException e) {
            commandHost.dropDepth();
            return new CommandResult(ActionCode.BAD_INPUT, "Max script depth reached");
        };


        int lineCount = 0;
        for (String scriptCommand : script.split("\n")) {
            try {
                inputHandler.handleCommand(scriptCommand);
            } catch (CommandNotFoundException ex) {
                System.out.format("Line %d: error executing command <%s>\n", lineCount, scriptCommand);
                return new CommandResult(ActionCode.BAD_INPUT, ex.getMessage());
            }
            lineCount++;
        }
        commandHost.dropDepth();

        return new CommandResult(ActionCode.OK, "Script " + scriptName + " executed.");
    }

    ;
}
