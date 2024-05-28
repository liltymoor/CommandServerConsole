package org.main.server.commands;

import org.main.server.commands.managers.CommandHost;
import org.main.server.commands.properties.ActionCode;
import org.main.server.commands.properties.CommandResult;
import org.main.server.commands.types.ClientCommand;
import org.main.server.exceptions.WrongArgException;

public class ExecuteScriptCommand extends ClientCommand {
    CommandHost commandHost;
    public ExecuteScriptCommand(CommandHost host) {
        super("execute_script", "Комадна  для выполнения скрипта");
        this.commandHost = host;
    }

    @Override
    public CommandResult action(Object[] params) {
        try {
            if (params.length != 1)
                throw new WrongArgException();
        } catch (WrongArgException ex) {
            return new CommandResult(ActionCode.BAD_INPUT, "Please, provide script name.");
        }
        return new CommandResult(ActionCode.BAD_INPUT, "Please, provide script name.");
        // TODO доделать
//        String scriptName = params[0];
//        String script = "";
//        try {
//            FileInputStream fileInputStream = new FileInputStream(scriptName);
//            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
//            script = new String(bufferedInputStream.readAllBytes(), StandardCharsets.UTF_8);
//            bufferedInputStream.close();
//            fileInputStream.close();
//        } catch (Exception ex) {
//            return new CommandResult(ActionCode.BAD_INPUT, "Can't open file " + scriptName);
//        }
//
//
//        try {
//            List<String> history = commandHost.getHistory();
//            if (history.get(history.size() - 1).equals("execute_script"))
//                commandHost.increaseDepth();
//        } catch (MaxScriptDepthReachedException e) {
//            commandHost.dropDepth();
//            return new CommandResult(ActionCode.BAD_INPUT, "Max script depth reached");
//        };
//
//
//        int lineCount = 0;
//        for (String scriptCommand : script.split("\n")) {
//            try {
//                inputHandler.handleCommand(scriptCommand);
//            } catch (CommandNotFoundException ex) {
//                System.out.format("Line %d: error executing command <%s>\n", lineCount, scriptCommand);
//                return new CommandResult(ActionCode.BAD_INPUT, ex.getMessage());
//            }
//            lineCount++;
//        }
//        commandHost.dropDepth();
//        return new CommandResult(ActionCode.OK, "Script " + scriptName + " executed.");
    };
}
