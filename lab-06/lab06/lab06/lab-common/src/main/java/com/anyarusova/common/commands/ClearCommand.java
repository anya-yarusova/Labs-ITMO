package com.anyarusova.common.commands;

import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.CollectionManager;
import com.anyarusova.common.utility.HistoryKeeper;

public class ClearCommand extends Command {

    public ClearCommand() {
        super("clear", null);
    }

    @Override
    public CommandResultDTO execute(CollectionManager collectionManager, HistoryKeeper historyKeeper) {
        historyKeeper.addNote(this.getName());
        collectionManager.getMainData().clear();
        return new CommandResultDTO("The collection was cleared successfully.");
    }
}
