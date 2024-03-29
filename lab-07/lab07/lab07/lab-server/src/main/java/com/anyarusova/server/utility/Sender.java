package com.anyarusova.server.utility;

import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.Pair;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeoutException;

public class Sender extends RecursiveTask<Void> {
    private static final int TIMEOUT_TO_SEND = 10;
    private static final int HEADER_LENGTH = 4;
    private final CommandResultDTO commandResultDTO;
    private final transient DatagramChannel datagramChannel;
    private final SocketAddress socketAddress;

    public Sender(
            CommandResultDTO commandResultDTO,
            DatagramChannel datagramChannel,
            SocketAddress socketAddress
    ) {
        this.commandResultDTO = commandResultDTO;
        this.datagramChannel = datagramChannel;
        this.socketAddress = socketAddress;
    }

    @Override
    protected Void compute() {
        System.out.println("Started to send message to the client");
        try {
            send(
                    socketAddress
            );
        } catch (TimeoutException | IOException e) {
            System.out.println("Could not send answer to client");
        }

        return null;
    }


    private void send(
            SocketAddress clientSocketAddress
    ) throws TimeoutException, IOException {
        Pair<byte[], byte[]> pair = serializeWithHeader(commandResultDTO);

        byte[] sendDataBytes = pair.getFirst();
        byte[] sendDataAmountBytes = pair.getSecond();


        try {
            ByteBuffer sendDataAmountWrapper = ByteBuffer.wrap(sendDataAmountBytes);
            int limit = TIMEOUT_TO_SEND;
            while (datagramChannel.send(sendDataAmountWrapper, clientSocketAddress) <= 0) {
                limit -= 1;
                System.out.println("could not sent a package, re-trying");
                if (limit == 0) {
                    throw new TimeoutException();
                }
            } // сначала отправляется файл-количество байтов в основном массиве байтов
            ByteBuffer sendBuffer = ByteBuffer.wrap(sendDataBytes);
            while (datagramChannel.send(sendBuffer, clientSocketAddress) <= 0) {
                limit -= 1;
                System.out.println("could not send a package, re-trying");
                if (limit == 0) {
                    throw new TimeoutException();
                }
            }
            System.out.println("sent the command result to the client");
        } catch (IOException e) {
            System.out.println("could not send the data to client because the message is too big");
        }


    }

    /**
     * @return first - data itself, second - amount of bytes in data
     */
    public Pair<byte[], byte[]> serializeWithHeader(Object obj) throws IOException {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);

        objectOutputStream.writeObject(obj);
        byte[] sizeBytes = ByteBuffer.allocate(HEADER_LENGTH).putInt(byteArrayOutputStream.size()).array();

        return new Pair<>(byteArrayOutputStream.toByteArray(), sizeBytes); // в первых 4 байтах будет храниться число-количество данных отправления
    }
}
