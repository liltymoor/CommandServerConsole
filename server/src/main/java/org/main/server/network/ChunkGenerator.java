package org.main.server.network;

import org.shared.network.ChunkInfo;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class ChunkGenerator {
    public static List<ByteBuffer> splitChunks(ByteBuffer buffer) {
        List<ByteBuffer> chunks = new ArrayList<>();
        if (buffer.limit() > ChunkInfo.RESPONSE_SIZE) {
            int offset = 0;

            while (offset < buffer.limit()) {
                ByteBuffer smallBuffer = buffer.slice(offset, Math.min(buffer.limit() - offset, ChunkInfo.RESPONSE_SIZE));
                chunks.add(smallBuffer);
                offset += ChunkInfo.RESPONSE_SIZE;
            }
        } else chunks.add(buffer);
        return chunks;
    }
}
