package org.main.server.commands.properties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CommandResult {
    ActionCode code;

    String message = "No message were provided.";
    List<Object> answerData;

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
    public String getMessage() { return message; }

    public List<Object> getAnswerData() {
        return answerData;
    }

    public CommandResult setAnswerData(List<Object> answerData) {
        this.answerData = answerData;
        return this;
    }

    public CommandResult setAnswerData(Object... answerData) {
        this.answerData = new ArrayList<>();
        for (Object o : answerData) {
            this.answerData.add(o);
        }
        return this;
    }
}
