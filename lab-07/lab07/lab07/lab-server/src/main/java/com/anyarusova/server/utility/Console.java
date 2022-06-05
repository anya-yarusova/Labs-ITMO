package com.anyarusova.server.utility;

import com.anyarusova.common.utility.State;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console {
    private final State<Boolean> serverIsRunningState;
    private final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

    public Console(State<Boolean> serverIsRunningState) {
        this.serverIsRunningState = serverIsRunningState;
    }

    public void start() {
        while (serverIsRunningState.getValue()) {
            final String input;
            try {
                input = bufferedReader.readLine();
            } catch (IOException e) {
                System.out.println("An unexpected IO exception occurred");
                serverIsRunningState.setValue(false);
                break;
            }
            if ("exit".equals(input)) {
                serverIsRunningState.setValue(false);
                System.out.println("Closing server...");
            }
        }
    }
    /*public void start() throws IOException {
        boolean isWorkingState = true;
        Scanner scanner = new Scanner(System.in);
        while (isWorkingState) {
            try {
                if (System.in.available() > 0) {
                    final String input = scanner.nextLine();
                    if ("exit".equals(input)) {
                        isWorkingState = false;
                    }
                }
                Command command = datagramChannelManager.receiveCommand();
                if (Objects.isNull(command)) {
                    continue;
                }
                CommandResultDTO commandResultDTO = command.execute(dataManager, historyKeeper);
                datagramChannelManager.sendResult(commandResultDTO);
            } catch (IOException e) {
                e.printStackTrace();
            }
            new SaveCommand(fileManager).execute(dataManager, historyKeeper);
        }
    }*/
}

