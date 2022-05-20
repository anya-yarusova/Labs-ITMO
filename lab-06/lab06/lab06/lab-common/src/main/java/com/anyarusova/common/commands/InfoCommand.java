package com.anyarusova.common.commands;

import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.CollectionManager;
import com.anyarusova.common.utility.HistoryKeeper;

public class InfoCommand extends Command {

    public InfoCommand() {
        super("info", null);
    }

    @Override
    public CommandResultDTO execute(CollectionManager collectionManager, HistoryKeeper historyKeeper) {
        historyKeeper.addNote(this.getName());
        if (!collectionManager.getMainData().isEmpty()) {
            return new CommandResultDTO("Collection type: " + collectionManager.getMainData().getClass().toString() + "\n"
                    + "Number of elements: " + collectionManager.getMainData().size() + "\n"
                    + "Creation date: " + collectionManager.getCreationDate() + "\n"
                    + "The biggest element has annualTurnover = " + collectionManager.getMainData().peek().getAnnualTurnover());
        } else {
            return new CommandResultDTO("Collection type: " + collectionManager.getMainData().getClass().toString() + "\n"
                    + "Number of elements: " + collectionManager.getMainData().size() + "\n"
                    + "Creation date: " + collectionManager.getCreationDate());
        }
    }
}
