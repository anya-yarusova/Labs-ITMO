package com.anyarusova.common.dto;

import java.net.SocketAddress;

public class ServerRequest {
    private final CommandDTO commandDTO;
    private final SocketAddress socketAddress;

    public ServerRequest(CommandDTO commandDTO, SocketAddress socketAddress) {
        this.commandDTO = commandDTO;
        this.socketAddress = socketAddress;
    }

    public CommandDTO getCommandDTO() {
        return commandDTO;
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }
}
