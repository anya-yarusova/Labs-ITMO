package com.anyarusova.common.commands;

import com.anyarusova.common.data.Organization;
import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.DataManager;
import com.anyarusova.common.utility.HistoryKeeper;

import java.io.Serializable;

public class RemoveLowerCommand extends Command {

    public RemoveLowerCommand(Serializable arg) {
        super("remove_lower", arg);
    }

    @Override
    public CommandResultDTO execute(DataManager dataManager, HistoryKeeper historyKeeper, String username) {
        historyKeeper.addNote(this.getName());
        Organization organization = (Organization) arg;
        dataManager.removeLowerIfOwned(organization, username);
        return new CommandResultDTO("Lower elements were removed successfully", true);
    }
}
