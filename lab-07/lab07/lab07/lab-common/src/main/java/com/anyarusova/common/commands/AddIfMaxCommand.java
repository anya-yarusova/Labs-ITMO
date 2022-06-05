package com.anyarusova.common.commands;

import com.anyarusova.common.data.Organization;
import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.DataManager;
import com.anyarusova.common.utility.HistoryKeeper;

import java.io.Serializable;

public class AddIfMaxCommand extends Command {

    public AddIfMaxCommand(Serializable arg) {
        super("add_if_max", arg);
    }

    @Override
    public CommandResultDTO execute(DataManager dataManager, HistoryKeeper historyKeeper, String username) {
        historyKeeper.addNote(this.getName());
        Organization organization = (Organization) arg;
        if (dataManager.checkIfMax(organization)) {
            dataManager.addOrganization(organization);
            return new CommandResultDTO("The element was added successfully", true);
        } else {
            return new CommandResultDTO("The element was not max, so it was not added", true);
        }
    }
}
