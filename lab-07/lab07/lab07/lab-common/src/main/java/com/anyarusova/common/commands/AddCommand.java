package com.anyarusova.common.commands;

import com.anyarusova.common.data.Organization;
import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.DataManager;
import com.anyarusova.common.utility.HistoryKeeper;

import java.io.Serializable;

public class AddCommand extends Command {

    public AddCommand(Serializable arg) {
        super("add", arg);
    }

    @Override
    public CommandResultDTO execute(DataManager dataManager, HistoryKeeper historyKeeper, String username) {
        historyKeeper.addNote(this.getName());
        Organization organization = (Organization) arg;
        organization.setId(-1);
        dataManager.addOrganization(organization);
        return new CommandResultDTO("The element was added successfully", true);
    }
}
