package com.anyarusova.common.commands;

import com.anyarusova.common.data.Organization;
import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.CollectionManager;
import com.anyarusova.common.utility.HistoryKeeper;

import java.io.Serializable;

public class AddCommand extends Command {

    public AddCommand(Serializable arg) {
        super("add", arg);
    }

    @Override
    public CommandResultDTO execute(CollectionManager collectionManager, HistoryKeeper historyKeeper) {
        historyKeeper.addNote(this.getName());
        Organization organization = (Organization) arg;
        organization.setId(collectionManager.getMaxId() + 1);
        collectionManager.getMainData().add(organization);
        return new CommandResultDTO("The element was added successfully");
    }
}
