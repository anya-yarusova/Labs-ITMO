package com.anyarusova.common.commands;

import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.CollectionManager;
import com.anyarusova.common.utility.HistoryKeeper;

import java.io.Serializable;

public class RemoveByIdCommand extends Command {

    public RemoveByIdCommand(Serializable arg) {
        super("remove_by_id", arg);
    }

    @Override
    public CommandResultDTO execute(CollectionManager collectionManager, HistoryKeeper historyKeeper) {
        historyKeeper.addNote(this.getName());
        int inputArg;
        try {
            inputArg = Integer.parseInt((String) arg);
        } catch (NumberFormatException e) {
            return new CommandResultDTO("Your argument was incorrect. The command was not executed.");
        }

        if (collectionManager.getMainData().removeIf(x -> x.getId() == inputArg)) {
            return new CommandResultDTO("The element was deleted successfully.");
        } else {
            return new CommandResultDTO("Could not find written id. The command was not executed");
        }
    }
}
