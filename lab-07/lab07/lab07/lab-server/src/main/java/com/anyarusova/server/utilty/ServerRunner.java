package com.anyarusova.server.utilty;

import com.anyarusova.common.commands.Command;
import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.CollectionManager;
import com.anyarusova.common.utility.HistoryKeeper;
import com.anyarusova.server.commands.SaveCommand;

import java.io.IOException;
import java.util.Objects;
import java.util.Scanner;

public class ServerRunner {
    private final HistoryKeeper historyKeeper;
    private final CollectionManager collectionManager;
    private final FileManager fileManager;
    private final DatagramChannelManager datagramChannelManager;

    public ServerRunner(HistoryKeeper historyKeeper, CollectionManager collectionManager,
                        FileManager fileManager, DatagramChannelManager datagramChannelManager) {
        this.historyKeeper = historyKeeper;
        this.collectionManager = collectionManager;
        this.fileManager = fileManager;
        this.datagramChannelManager = datagramChannelManager;
    }

    public void start() throws IOException {
        boolean isWorkingState = true;
        Scanner scanner = new Scanner(System.in);
        while (isWorkingState) {
            try {
                if (System.in.available() > 0) {
                    final String input = scanner.nextLine();
                    if ("exit".equals(input)) {
                        isWorkingState = false;
                    }
                    if ("save".equals(input)) {
                        new SaveCommand(fileManager).execute(collectionManager, historyKeeper);
                        System.out.println("Save data to file");
                    }
                }
                Command command = datagramChannelManager.receiveCommand();
                if (Objects.isNull(command)) {
                    continue;
                }
                CommandResultDTO commandResultDTO = command.execute(collectionManager, historyKeeper);
                datagramChannelManager.sendResult(commandResultDTO);
            } catch (IOException e) {
                e.printStackTrace();
            }
            new SaveCommand(fileManager).execute(collectionManager, historyKeeper);
        }
    }
}

