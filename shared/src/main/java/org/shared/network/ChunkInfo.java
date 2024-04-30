package org.shared.network;

import java.io.Serializable;
import java.nio.ByteBuffer;

public class ChunkInfo implements Serializable {
    public static final int RESPONSE_SIZE = Response.RESPONSE_SIZE;
    private int chunks = 0;
    public ChunkInfo(ByteBuffer buffer) {
        chunks = (int) Math.floor(buffer.limit() / RESPONSE_SIZE) + 1;
    }

    public int getChunks() {
        return chunks;
    }
}
