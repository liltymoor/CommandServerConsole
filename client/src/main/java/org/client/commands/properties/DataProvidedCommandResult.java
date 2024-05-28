package org.client.commands.properties;

public class DataProvidedCommandResult<T> extends CommandResult {
    private T data;

    public DataProvidedCommandResult(ActionCode code, String message, T providedData) {
        super(code, message);
        data = providedData;
    }

    public DataProvidedCommandResult(ActionCode code, T providedData) {
        super(code);
        data = providedData;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return super.toString().concat(" Data: %s".formatted(data.getClass()));
    }
}
