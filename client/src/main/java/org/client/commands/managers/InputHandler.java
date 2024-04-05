package org.client.commands.managers;

import org.client.commands.Command;
import org.client.commands.properties.InputCompoundable;
import org.client.exceptions.CommandNotFoundException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;
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

    public InputHandler (CommandHost host, CommandInvoker invoker) {
        commandPattern = Pattern.compile("\\S+");
        this.host = host;
        this.invoker = invoker;
        inputHost = new Scanner(System.in);
    }

    private String[] parseInput (Matcher matcher) {
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

        if (command instanceof InputCompoundable) {
            LinkedHashMap<String, InputRule> compound = ((InputCompoundable) command).getArgCompound();

            String[] params;

            // If we have something (params) in first line of command
            if (splittedInput.length > 1) {
                params = new String[compound.size() + splittedInput.length - 1];
                System.arraycopy(splittedInput, 1, params, 0, splittedInput.length - 1);
            } else
                params = new String[compound.size()];


            int counter = splittedInput.length - 1;

            for (String field: compound.keySet()) {

                InputRule rule = compound.get(field);
                System.out.printf("%s %s: ", field, rule);
                String compoundInput = inputHost.nextLine();

                while (!rule.check(compoundInput)) {
                    System.out.println("Incorrect input\n");
                    System.out.printf("%s %s: ", field, rule);
                    compoundInput = inputHost.nextLine();
                }
                params[counter++] = compoundInput == "" ? null : compoundInput;
            }
            invoker.invoke(command, params);
            return;
        }

        if (splittedInput.length > 1) {
            String[] params = new String[splittedInput.length - 1];
            System.arraycopy(splittedInput, 1, params, 0, splittedInput.length - 1);

            invoker.invoke(command, params);
            return;
        }

        invoker.invoke(command);
    }
}
