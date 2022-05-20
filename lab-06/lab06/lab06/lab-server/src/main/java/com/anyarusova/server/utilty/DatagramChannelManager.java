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
    private static final int MAX_PACKAGE_SIZE = 65507;
    private final DatagramChannel datagramChannel;
    private SocketAddress lastRemoteAddress;

    public DatagramChannelManager(int port) throws IOException {
        datagramChannel = DatagramChannel.open();
        datagramChannel.bind(new InetSocketAddress(port));
        datagramChannel.configureBlocking(false);
    }

    public Command receiveCommand() throws IOException {
        ByteBuffer inputPackages = ByteBuffer.wrap(new byte[MAX_PACKAGE_SIZE]);
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

    public void sendResult(CommandResultDTO commandResultDTO) {
        if (commandResultDTO.getOutput().equals("The command was not found. Please use \"help\" to know about commands.")
                || commandResultDTO.getOutput().equals("")) {
            return;
        }
        ByteArrayOutputStream dataOutputStream = serializeCommandResponse(commandResultDTO);
        writeNextDatagram(ByteBuffer.wrap(dataOutputStream.toByteArray()));
    }

    private ByteArrayOutputStream serializeCommandResponse(CommandResultDTO commandResultDTO) {
        try {
            ByteArrayOutputStream dataOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(dataOutputStream);
            objectOutputStream.writeObject(commandResultDTO);
            if (dataOutputStream.size() > MAX_PACKAGE_SIZE) {
                objectOutputStream
                        .writeObject(new CommandResultDTO("Command result couldn't be sent"));
            }
            return dataOutputStream;
        } catch (IOException e) {
            return new ByteArrayOutputStream();
        }
    }

    private void writeNextDatagram(ByteBuffer byteBuffer) {
        while (byteBuffer.hasRemaining()) {
            try {
                datagramChannel.send(byteBuffer, lastRemoteAddress);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
