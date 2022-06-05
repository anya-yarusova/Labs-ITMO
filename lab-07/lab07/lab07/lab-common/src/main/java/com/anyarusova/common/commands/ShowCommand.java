package com.anyarusova.common.commands;

import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.DataManager;
import com.anyarusova.common.utility.HistoryKeeper;

public class ShowCommand extends Command {

    public ShowCommand() {
        super("show", null);
    }

    @Override
    public CommandResultDTO execute(DataManager dataManager, HistoryKeeper historyKeeper, String username) {
        historyKeeper.addNote(this.getName());
        if (dataManager.getMaxByIdOrganization() == null) {
            return new CommandResultDTO("Collection is empty", true);
        }
        return new CommandResultDTO(dataManager.showSortedByName(), true);
    }
}
