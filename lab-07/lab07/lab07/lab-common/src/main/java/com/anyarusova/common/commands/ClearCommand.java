package com.anyarusova.common.commands;

import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.DataManager;
import com.anyarusova.common.utility.HistoryKeeper;

public class ClearCommand extends Command {

    public ClearCommand() {
        super("clear", null);
    }

    @Override
    public CommandResultDTO execute(DataManager dataManager, HistoryKeeper historyKeeper, String username) {
        historyKeeper.addNote(this.getName());
        dataManager.clearOwnedData(username);
        return new CommandResultDTO("The collection was cleared successfully", true);
    }
}
