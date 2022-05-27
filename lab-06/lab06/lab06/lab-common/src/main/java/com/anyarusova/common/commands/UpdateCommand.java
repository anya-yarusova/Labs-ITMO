package com.anyarusova.common.commands;

import com.anyarusova.common.data.Organization;
import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.CollectionManager;
import com.anyarusova.common.utility.HistoryKeeper;

import java.io.Serializable;

public class UpdateCommand extends Command {

    private final Serializable inputId;

    public UpdateCommand(Serializable arg, Serializable id) {
        super("update_id", arg);
        this.inputId = id;
    }

    @Override
    public CommandResultDTO execute(CollectionManager collectionManager, HistoryKeeper historyKeeper) {
        historyKeeper.addNote(this.getName());
        int inputArg;
        try {
            inputArg = Integer.parseInt((String) inputId);
        } catch (NumberFormatException e) {
            return new CommandResultDTO("Your argument was incorrect. The command was not executed.");
        }
        if (collectionManager.getMainData().removeIf(x -> x.getId() == inputArg)) {
            Organization organization = (Organization) arg;
            organization.setId(inputArg);
            collectionManager.getMainData().add(organization);
            return new CommandResultDTO("The element was updated successfully");
        } else {
            return new CommandResultDTO("Written id was not found. The command was not executed");
        }
    }
}
