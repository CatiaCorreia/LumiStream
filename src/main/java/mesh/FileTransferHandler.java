package mesh;

import io.netty.channel.*;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileTransferHandler extends SimpleChannelInboundHandler<String> {

    private static final String STORAGE_DIR = "shared_files";

    @Override
    public void channelRead0(ChannelHandlerContext ctx, String msg) {
        System.out.println("Received message: " + msg);

        if (msg.startsWith("REQUEST_FILE")) {
            String fileName = msg.split(":")[1];
            sendFile(ctx, fileName);
        } else if (msg.startsWith("FILE")) {
            String[] parts = msg.split(":", 3);
            String fileName = parts[1];
            String fileContent = parts[2];

            saveFile(fileName, fileContent);
        }
    }

    private void sendFile(ChannelHandlerContext ctx, String fileName) {
        try {
            Path filePath = Paths.get(STORAGE_DIR, fileName);
            //String fileContent = Files.readString(filePath);
            //ctx.writeAndFlush("FILE:" + fileName + ":" + fileContent);
        } catch (IOException e) {
            System.err.println("Failed to read file: " + e.getMessage());
        }
    }

    private void saveFile(String fileName, String fileContent) {
        try {
            Path dirPath = Paths.get(STORAGE_DIR);
            if (!Files.exists(dirPath)) {
                Files.createDirectories(dirPath);
            }

            Path filePath = dirPath.resolve(fileName);
            //Files.writeString(filePath, fileContent);
            System.out.println("File saved: " + filePath);
        } catch (IOException e) {
            System.err.println("Failed to save file: " + e.getMessage());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
