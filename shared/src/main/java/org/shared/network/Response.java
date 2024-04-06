package org.shared.network;

import java.io.Serializable;

public class Response implements Serializable {
    public static final int RESPONSE_SIZE = 512;

    private final String resultCode;
    private final String resultMessage;

    public Response(String resultCode, String resultMessage) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }

    public String getResultCode() {
        return resultCode;
    }
    public String getResultMessage() {
        return resultMessage;
    }
}
