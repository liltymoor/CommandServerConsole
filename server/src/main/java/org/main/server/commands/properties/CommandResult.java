package org.main.server.commands.properties;

import java.util.HashMap;

public class CommandResult {
    ActionCode code;
    String message = "No message were provided.";
    public CommandResult(ActionCode code) {
        String fetchMessage = getCodesDescription().get(code.name());
        if (fetchMessage!= null)
            this.message = fetchMessage;

        this.code = code;

    }

    public CommandResult(ActionCode code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "%s | %s".formatted(code.toString(), message);
    }

    private static HashMap<String, String> getCodesDescription() {
        HashMap<String,String> codes = new HashMap<>();
        codes.put("NOT_MINIMAL", "Object is not minimal.");
        return codes;
    }
    public ActionCode getCode() {
        return code;
    }
}
