package com.anyarusova.common.commands;

import com.anyarusova.common.data.Organization;
import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.CollectionManager;
import com.anyarusova.common.utility.HistoryKeeper;

import java.io.Serializable;

public class AddIfMaxCommand extends Command {

    public AddIfMaxCommand(Serializable arg) {
        super("add_if_max", arg);
    }

    @Override
    public CommandResultDTO execute(CollectionManager collectionManager, HistoryKeeper historyKeeper) {
        historyKeeper.addNote(this.getName());
        Organization organization = (Organization) arg;
        organization.setId(collectionManager.getMaxId() + 1);
        if (collectionManager.getMainData().isEmpty() || organization.compareTo(collectionManager.getMainData().peek()) < 0) {
            collectionManager.getMainData().add(organization);
            return new CommandResultDTO("The element was added successfully");
        } else {
            return new CommandResultDTO("The element was not max, so it was not added");
        }
    }
}
