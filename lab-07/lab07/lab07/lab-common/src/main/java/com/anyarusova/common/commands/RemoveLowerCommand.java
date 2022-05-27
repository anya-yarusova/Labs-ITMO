package com.anyarusova.common.commands;

import com.anyarusova.common.data.Organization;
import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.CollectionManager;
import com.anyarusova.common.utility.HistoryKeeper;

import java.io.Serializable;

public class RemoveLowerCommand extends Command {

    public RemoveLowerCommand(Serializable arg) {
        super("remove_lower", arg);
    }

    @Override
    public CommandResultDTO execute(CollectionManager collectionManager, HistoryKeeper historyKeeper) {
        historyKeeper.addNote(this.getName());
        Organization organization = (Organization) arg;
        collectionManager.getMainData().removeIf(x -> x.compareTo(organization) < 0);
        return new CommandResultDTO("Lower elements were removed successfully");
    }
}
