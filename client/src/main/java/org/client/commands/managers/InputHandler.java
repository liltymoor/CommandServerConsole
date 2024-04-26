package org.client.commands.managers;

import org.client.commands.Command;
import org.client.commands.properties.InputCompoundable;
import org.client.exceptions.CommandNotFoundException;
import org.shared.model.input.buildrule.Builder;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lil_timmie
 * Класс для работы с вводом пользователя - связывает ввод с командами.
 */
public class InputHandler {

    CommandHost host;
    CommandInvoker invoker;

    Scanner inputHost;
    Pattern commandPattern;

    public InputHandler(CommandHost host, CommandInvoker invoker) {
        commandPattern = Pattern.compile("\\S+");
        this.host = host;
        this.invoker = invoker;
        inputHost = new Scanner(System.in);
    }

    private String[] parseInput(Matcher matcher) {
        ArrayList<String> result = new ArrayList<>();
        while (matcher.find()) {
            result.add(matcher.group());
        }
        return result.toArray(new String[0]);
    }

    public void handleCommand() {
        String input = inputHost.nextLine();
        handleCommand(input);
    }

    public void handleCommand(String input) throws CommandNotFoundException {
        Matcher matcher = commandPattern.matcher(input.trim());
        String[] splittedInput = parseInput(matcher);

        if (splittedInput.length == 0) {
            return;
        }

        Command command = host.getCommands().get(splittedInput[0]);

        if (splittedInput.length > 1) {
            String[] params = new String[splittedInput.length - 1];
            System.arraycopy(splittedInput, 1, params, 0, splittedInput.length - 1);

            invoker.invoke(command, params);
            return;
        }

        if (command instanceof InputCompoundable) {
            List<Builder<?>> toBuild = ((InputCompoundable) command).getArgCompound();

            Object[] params = new Object[toBuild.size()];
            for (int i = 0; i < toBuild.size(); i++) {
                Builder<?> builder = toBuild.get(i);
                try { params[i] = builder.build(inputHost);}
                catch (IOException ex) {
                    System.out.printf("[CLIENT] Failed to build %s%n", builder.getName());
                    return;
                }
            }
            invoker.invoke(command, params);
            return;
        }

        invoker.invoke(command);
    }
}
