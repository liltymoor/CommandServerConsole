package org.shared.network;

import java.io.Serializable;
import java.util.List;

public class Response implements Serializable {
    public static final int RESPONSE_SIZE = 512;

    private final String resultCode;
    private final String resultMessage;
    private final Object resultData;

    public Response(String resultCode, String resultMessage, Object resultData) {
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
        this.resultData = resultData;
    }

    public Object getResultData() { return resultData; }
    public String getResultCode() {
        return resultCode;
    }
    public String getResultMessage() {
        return resultMessage;
    }
}
