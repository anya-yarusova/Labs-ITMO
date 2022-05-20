package com.anyarusova.server;

import com.anyarusova.common.utility.CollectionManager;
import com.anyarusova.common.utility.HistoryKeeper;
import com.anyarusova.server.utilty.DatagramChannelManager;
import com.anyarusova.server.utilty.FileManager;
import com.anyarusova.server.utilty.HistoryManager;
import com.anyarusova.server.utilty.PriorityQueueManager;
import com.anyarusova.server.utilty.ServerRunner;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public final class Server {

    private static final int MAX_PORT = 65535;
    private static final int MIN_PORT = 1;
    private static final int DEFAULT_PORT = 1812;

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) throws IOException {
        File file = getSourceFile(args);
        int port = getServerPort(args);
        if (Objects.isNull(file) || port == -1) {
            return;
        }
        CollectionManager collectionManager = new PriorityQueueManager();
        HistoryKeeper historyKeeper = new HistoryManager();
        FileManager fileManager = new FileManager(args[0]);
        DatagramChannelManager datagramChannelManager = new DatagramChannelManager(port);
        ServerRunner serverRunner;
        try {
            serverRunner = new ServerRunner(historyKeeper, collectionManager, fileManager, datagramChannelManager);
            serverRunner.start();
        } catch (IOException e) {
            System.out.println("An unexpected IO error occurred. The message is: " + e.getMessage());
        }
    }

    public static File getSourceFile(String[] args) {
        if (args.length == 0) {
            System.out.println("Starting server failed: Path to collection file must be provided");
            return null;
        }
        File file = new File(args[0]);
        if (!file.exists() || !file.isFile()) {
            System.out.println("Starting server failed: File not found");
            return null;
        }
        if (!file.canRead()) {
            System.out.println("Starting server failed: Can't read from file");
            return null;
        }
        return file;
    }

    public static int getServerPort(String[] args) {
        if (args.length < 2) {
            System.out.println("Port value set as default (1812)");
            return DEFAULT_PORT;
        }
        if (args[1].matches("\\d{1,5}")) {
            int port = Integer.parseInt(args[1]);
            if (port <= MAX_PORT && port >= MIN_PORT) {
                System.out.println("Port value set to " + args[1]);
                return port;
            }
        }
        System.out.println("Port number out of range");
        return -1;
    }
}
