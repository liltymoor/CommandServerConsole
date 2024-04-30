package org.main.server.network;

import org.main.server.commands.properties.CommandResult;
import org.shared.network.ChunkInfo;
import org.shared.network.Response;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.List;

public class ResponseEmitter {
    DatagramChannel server;
    public ResponseEmitter(DatagramChannel server) {
        this.server = server;
    }

    private ByteBuffer wrapResponse(CommandResult result) {
        Response response = new Response(result.getCode().toString(), result.toString());
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(response);
            oos.flush();
            return ByteBuffer.wrap(bos.toByteArray());
        } catch (IOException e) { System.out.printf("[RESPONSE]: Error Wrapping: %s%n", e.getMessage()); }
        return null;
    }

    private ByteBuffer wrapChunkInfo(ChunkInfo info) {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(info);
            oos.flush();
            return ByteBuffer.wrap(bos.toByteArray());
        } catch (IOException e) { System.out.printf("[RESPONSE]: Error Wrapping: %s%n", e.getMessage()); }
        return null;
    }

    public void emit(CommandResult result, InetSocketAddress clientAddress) {
        ByteBuffer buffer = wrapResponse(result);

        ChunkInfo info = new ChunkInfo(buffer);
        ByteBuffer chunkData = wrapChunkInfo(info);

        if (chunkData == null) return;

        try {
            server.send(chunkData, clientAddress);
            System.out.printf("[RESPONSE]: Chunk info sent to %s:%s%n%n",clientAddress.getHostName(), clientAddress.getPort());
        }
        catch (IOException e) { System.out.printf("[RESPONSE]: Error Sending: %s%n%n", e.getMessage()); };

        List<ByteBuffer> toSend = ChunkGenerator.splitChunks(buffer);

        for (ByteBuffer chunk: toSend) {
            try {
                server.send(chunk, clientAddress);
                System.out.printf("[RESPONSE]: Sent back to %s:%s%n%n",clientAddress.getHostName(), clientAddress.getPort());
            }
            catch (IOException e) { System.out.printf("[RESPONSE]: Error Sending: %s%n%n", e.getMessage()); }
        }
    }
}
