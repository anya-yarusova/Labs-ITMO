package com.anyarusova.common.commands;

import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.CollectionManager;
import com.anyarusova.common.utility.HistoryKeeper;

public class HistoryCommand extends Command {

    public HistoryCommand() {
        super("history", null);
    }

    @Override
    public CommandResultDTO execute(CollectionManager collectionManager, HistoryKeeper historyKeeper) {
        historyKeeper.addNote(this.getName());
        return new CommandResultDTO(historyKeeper.niceToString());
    }
}
