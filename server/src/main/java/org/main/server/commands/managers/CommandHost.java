package org.main.server.commands.managers;

import org.main.server.commands.ClientCommand;
import org.main.server.commands.Command;
import org.main.server.commands.properties.Actionable;
import org.main.server.exceptions.MaxScriptDepthReachedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lil_timmie
 * Класс для хранения и управления командами.
 */
public class CommandHost {
    private List<String> commandExecutionHistory;
    private Map<String, Command> commandMap;

    private final int maxDepth = 30;
    private int currentDepth = 0;

    public CommandHost() {
        commandExecutionHistory = new ArrayList<>();
        commandMap = new HashMap<>();
    }

    public CommandHost(Command[] commands) {
        commandExecutionHistory = new ArrayList<>();
        commandMap = new HashMap<>();
        for (Command command: commands)
            addCommand(command);
    }

    public void addCommand(Command command) {
        commandMap.put(command.getName(), command);
    }

    public Map<String, Command> getCommands() {
        return commandMap;
    }

    public void appendHistory(Actionable command) {
        commandExecutionHistory.add(((Command)command).getName());
    }

    public List<String> getHistory() {
        return commandExecutionHistory;
    }

    public List<String> getHistory(int last) {

        if (commandExecutionHistory.size() < last)
            return commandExecutionHistory;

        return commandExecutionHistory.subList(
                commandExecutionHistory.size() - 1 - last,
                commandExecutionHistory.size()
        );
    }

    public void dropDepth() {
        currentDepth = 0;
    }

    public int getDepth() {
        return currentDepth;
    }

    public void increaseDepth() throws MaxScriptDepthReachedException {
        if (currentDepth >= maxDepth)
            throw new MaxScriptDepthReachedException(maxDepth);
        currentDepth++;
    }
}
