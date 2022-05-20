package com.anyarusova.client.utility;

import com.anyarusova.common.dto.CommandDTO;
import com.anyarusova.common.dto.CommandResultDTO;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class DatagramSocketManager {

    private static final int MAX_PACKAGE_SIZE = 65507;
    private static final int TIMEOUT = 1000;
    private final InetSocketAddress serverAddress;
    private DatagramSocket socket;

    public DatagramSocketManager(InetSocketAddress serverAddress) throws SocketException {
        this.serverAddress = serverAddress;
        setupNewSocket();
    }

    private void setupNewSocket() throws SocketException {
        InetSocketAddress address;
        address = new InetSocketAddress(0);
        socket = new DatagramSocket(address);
        socket.connect(serverAddress);
        socket.setSoTimeout(TIMEOUT);
    }

    private CommandResultDTO setupNewSocketSuppressed() {
        try {
            setupNewSocket();
            return new CommandResultDTO("Couldn't get response from server");
        } catch (SocketException e) {
            return new CommandResultDTO("Can't connect to server");
        }
    }

    public CommandResultDTO receiveCommandResult() throws IOException {
        try {
            DatagramPacket packet = new DatagramPacket(new byte[MAX_PACKAGE_SIZE], MAX_PACKAGE_SIZE);
            socket.receive(packet);
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(packet.getData());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteInputStream);
            return (CommandResultDTO) objectInputStream.readObject();
        } catch (SocketTimeoutException e) {
            return setupNewSocketSuppressed();
        } catch (IOException e) {
            return new CommandResultDTO("Couldn't receive any answer from server");
        } catch (ClassNotFoundException e) {
            return new CommandResultDTO("Received incorrect answer from server");
        }
    }

    void sendCommand(CommandDTO commandDTO) throws IOException {
        try {
            ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteOutputStream);
            objectOutputStream.writeObject(commandDTO);
            DatagramPacket packet = new DatagramPacket(byteOutputStream.toByteArray(),
                    byteOutputStream.toByteArray().length);
            socket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
