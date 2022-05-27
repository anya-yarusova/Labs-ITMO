package com.anyarusova.server.utilty;

import com.anyarusova.common.commands.Command;
import com.anyarusova.common.dto.CommandDTO;
import com.anyarusova.common.dto.CommandResultDTO;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Objects;

public class DatagramChannelManager {
    private static final int PACKAGE_SIZE = 10000;
    private final DatagramChannel datagramChannel;
    private SocketAddress lastRemoteAddress;

    public DatagramChannelManager(int port) throws IOException {
        datagramChannel = DatagramChannel.open();
        datagramChannel.bind(new InetSocketAddress(port));
        datagramChannel.configureBlocking(false);
    }

    public Command receiveCommand() throws IOException {
        ByteBuffer inputPackages = ByteBuffer.wrap(new byte[PACKAGE_SIZE]);
        lastRemoteAddress = datagramChannel.receive(inputPackages);
        try {
            if (Objects.nonNull(lastRemoteAddress)) {
                ObjectInputStream objectInputStream = new ObjectInputStream(
                        new ByteArrayInputStream(inputPackages.array()));
                Command input = ((CommandDTO) objectInputStream.readObject()).getCommand();
                return input;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public void sendResult(CommandResultDTO commandResultDTO) throws IOException {
        if (commandResultDTO.getOutput().equals("The command was not found. Please use \"help\" to know about commands.")
                || commandResultDTO.getOutput().equals("")) {
            return;
        }
        byte[] bufferOutputStream = serialize(commandResultDTO);
        byte[] bufferSize = serialize(bufferOutputStream.length);
        int sendSize = bufferSize.length;
        ByteBuffer sendBufferSize = ByteBuffer.wrap(bufferSize);
        int limit = PACKAGE_SIZE;
        while (datagramChannel.send(sendBufferSize, lastRemoteAddress) < sendSize) {
            limit -= 1;
            if (limit == 0) {
                return;
            }
        }
        ByteBuffer sendBuffer = ByteBuffer.wrap(bufferOutputStream);
        sendSize = bufferOutputStream.length;
        limit = PACKAGE_SIZE;
        while (datagramChannel.send(sendBuffer, lastRemoteAddress) < sendSize) {
            limit -= 1;
            if (limit == 0) {
                return;
            }
        }
    }

    public byte[] serialize(Object obj) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(obj);
        byte[] out = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        objectOutputStream.close();
        return out;
    }

}
