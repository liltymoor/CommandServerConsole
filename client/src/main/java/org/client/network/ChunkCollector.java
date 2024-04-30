package org.client.network;

import org.shared.network.ChunkInfo;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class ChunkCollector {
    private ByteBuffer buffer;
    private int chunksToCollect;
    private int chunksCollected;

    public ChunkCollector() {
        chunksToCollect = 0;
        chunksCollected = 0;
    }

    public void prepare(ChunkInfo info) {
        chunksToCollect = info.getChunks();
        buffer = ByteBuffer.allocate(chunksToCollect * ChunkInfo.RESPONSE_SIZE);
        System.out.println("\n[CLIENT] Prepared to collect chunked answer.");
    }

    public void concatChunk(ByteBuffer data) {
        try {
            if (buffer.remaining() >= data.remaining()) {
                buffer.put(data);
            } else {
                System.out.println("[ERROR] Not enough space in the buffer to store the data");
            }
            chunksCollected += 1;
            System.out.printf("[CLIENT] Chunk received (%d/%d).\n", chunksCollected, chunksToCollect);
        } catch (Exception e) {
            System.out.println("[ERROR] An error occurred: " + e.getMessage());
        }
    }

    public boolean isReady() {
        return chunksCollected == chunksToCollect;
    }

    private void clear() {
        chunksCollected = 0;
        chunksToCollect = 0;
        buffer.clear();
    }

    public ByteBuffer getData() {
        if (isReady()) {
            ByteBuffer data = buffer.duplicate();
            clear();
            data.flip();
            return data;
        }
        return null;
    }
}
