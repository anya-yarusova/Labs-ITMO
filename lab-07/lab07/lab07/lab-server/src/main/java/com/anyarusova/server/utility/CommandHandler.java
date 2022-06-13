package com.anyarusova.server.utility;

import com.anyarusova.common.commands.PrivateAccessedOrganizationCommand;
import com.anyarusova.common.commands.RegisterCommand;
import com.anyarusova.common.dto.CommandDTO;
import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.dto.ServerRequest;
import com.anyarusova.common.dto.ServerResponse;
import com.anyarusova.common.utility.DataManager;
import com.anyarusova.common.utility.HistoryKeeper;
import com.anyarusova.common.utility.State;

import java.net.SocketAddress;
import java.util.Queue;
import java.util.concurrent.ExecutorService;

public class CommandHandler {
    private final Queue<ServerRequest> requestQueue;
    private final Queue<ServerResponse> responseQueue;
    private final DataManager dataManager;
    private final HistoryKeeper historyKeeper;

    public CommandHandler(
            Queue<ServerRequest> requestQueue,
            Queue<ServerResponse> responseQueue,
            DataManager dataManager,
            HistoryKeeper historyKeeper) {
        this.requestQueue = requestQueue;
        this.responseQueue = responseQueue;
        this.dataManager = dataManager;
        this.historyKeeper = historyKeeper;
    }

    public void startToHandleCommands(
            State<Boolean> isWorkingState,
            ExecutorService threadPool
    ) {
        Runnable startCheckingForAvailableCommandsToRun = () -> {
            while (isWorkingState.getValue()) {
                if (!requestQueue.isEmpty()) {
                    ServerRequest serverRequest = requestQueue.poll();
                    Runnable executeFirstCommandTack = () -> {
                        System.out.println("Starting to execute a new command");
                        assert serverRequest != null;
                        CommandDTO commandDTO = serverRequest.getCommandDTO();
                        SocketAddress clientAddress = serverRequest.getSocketAddress();
                        try {
                            executeWithValidation(commandDTO, clientAddress);
                        } catch (Exception e) {
                            System.out.println(e.getMessage());
                        }
                        System.out.println("Successfully executed the command command");
                    };
                    threadPool.submit(executeFirstCommandTack);
                }
            }
        };
        threadPool.submit(startCheckingForAvailableCommandsToRun);
    }

    private void executeWithValidation(CommandDTO commandDTO, SocketAddress clientAddress) {
        if (dataManager.validateUser(commandDTO.getLogin(), commandDTO.getPassword()) || commandDTO.getCommand() instanceof RegisterCommand) {
            if (commandDTO.getCommand() instanceof PrivateAccessedOrganizationCommand) {
                final int id = ((PrivateAccessedOrganizationCommand) commandDTO.getCommand()).getOrganizationId();
                if (dataManager.validateOwner(commandDTO.getLogin(), id)) {
                    responseQueue.add(new ServerResponse(commandDTO.getCommand().execute(dataManager, historyKeeper, commandDTO.getLogin()), clientAddress));
                } else {
                    responseQueue.add(new ServerResponse(new CommandResultDTO("You are not the owner of the object so you can't do anything with it", true), clientAddress));
                }
            } else {
                responseQueue.add(new ServerResponse(commandDTO.getCommand().execute(dataManager, historyKeeper, commandDTO.getLogin()), clientAddress));
            }
        } else {
            responseQueue.add(new ServerResponse(new CommandResultDTO("Invalid login or password. Command was not executed", false), clientAddress));
        }
    }
}
