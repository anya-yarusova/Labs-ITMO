package com.anyarusova.common.commands;

import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.DataManager;
import com.anyarusova.common.utility.HistoryKeeper;

import java.io.Serializable;

public class RemoveByIdCommand extends Command implements PrivateAccessedOrganizationCommand {

    public RemoveByIdCommand(Serializable arg) {
        super("remove_by_id", arg);
    }

    @Override
    public CommandResultDTO execute(DataManager dataManager, HistoryKeeper historyKeeper, String username) {
        historyKeeper.addNote(this.getName());
        int inputArg;
        try {
            inputArg = Integer.parseInt((String) arg);
        } catch (NumberFormatException e) {
            return new CommandResultDTO("Your argument was incorrect. The command was not executed.", true);
        }
        dataManager.removeOrganizationById(inputArg);
        return new CommandResultDTO("The element was deleted successfully.", true);
    }

    @Override
    public int getOrganizationId() {
        try {
            return Integer.parseInt((String) arg);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
