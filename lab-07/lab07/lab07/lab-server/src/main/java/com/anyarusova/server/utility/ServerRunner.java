package com.anyarusova.server.utility;

import com.anyarusova.common.dto.ServerRequest;
import com.anyarusova.common.dto.ServerResponse;
import com.anyarusova.common.utility.DataManager;
import com.anyarusova.common.utility.State;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.channels.DatagramChannel;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ForkJoinPool;

public class ServerRunner {
    private final Queue<ServerRequest> requestQueue;
    private final Queue<ServerResponse> responseQueue;
    private final int port;
    private final String ip;
    private final CommandHandler commandHandler;
    private final Receiver receiver;
    private final ExecutorService threadPool;
    private final ForkJoinPool forkJoinPool;
    private final DataManager dataManager;

    public ServerRunner(
            int port,
            String ip,
            ExecutorService threadPool,
            ForkJoinPool forkJoinPool,
            DataManager dataManager
    ) {
        this.ip = ip;
        this.port = port;
        requestQueue = new LinkedBlockingQueue<>();
        responseQueue = new LinkedBlockingQueue<>();
        this.commandHandler = new CommandHandler(requestQueue, responseQueue, dataManager, new HistoryManager());
        this.receiver = new Receiver(requestQueue);
        this.threadPool = threadPool;
        this.forkJoinPool = forkJoinPool;
        this.dataManager = dataManager;
    }

    public void start(
            State<Boolean> isWorking
    ) throws IOException {
        try (DatagramChannel datagramChannel = DatagramChannel.open()) {
            datagramChannel.bind(new InetSocketAddress(ip, port));
            datagramChannel.configureBlocking(false);

            threadPool.submit(() -> {
                try {
                    receiver.startReceivingData(datagramChannel, isWorking, threadPool);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });


            commandHandler.startToHandleCommands(
                    isWorking,
                    threadPool
            );


            while (isWorking.getValue()) {
                if (!responseQueue.isEmpty()) {
                    ServerResponse serverResponse = responseQueue.poll();
                    forkJoinPool.invoke(new Sender(serverResponse.getCommandResultDTO(), datagramChannel, serverResponse.getSocketAddress()));
                }
            }

        } catch (BindException e) {
            System.out.println("Could not start the server, bind exception. Please, use another port.");
            isWorking.setValue(false);
        }
    }
}
