package com.anyarusova.common.dto;

import java.net.SocketAddress;

public class ServerResponse {

    private final CommandResultDTO commandResultDTO;
    private final SocketAddress socketAddress;

    public ServerResponse(CommandResultDTO commandResultDTO, SocketAddress socketAddress) {
        this.commandResultDTO = commandResultDTO;
        this.socketAddress = socketAddress;
    }

    public CommandResultDTO getCommandResultDTO() {
        return commandResultDTO;
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }
}
