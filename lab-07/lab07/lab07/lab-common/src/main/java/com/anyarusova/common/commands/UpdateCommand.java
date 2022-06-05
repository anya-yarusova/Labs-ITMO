package com.anyarusova.common.commands;

import com.anyarusova.common.data.Organization;
import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.DataManager;
import com.anyarusova.common.utility.HistoryKeeper;

import java.io.Serializable;

public class UpdateCommand extends Command implements PrivateAccessedOrganizationCommand {

    private final Serializable inputId;

    public UpdateCommand(Serializable arg, Serializable id) {
        super("update_id", arg);
        this.inputId = id;
    }

    @Override
    public CommandResultDTO execute(DataManager dataManager, HistoryKeeper historyKeeper, String username) {
        historyKeeper.addNote(this.getName());
        int inputArg;
        try {
            inputArg = Integer.parseInt((String) inputId);
        } catch (NumberFormatException e) {
            return new CommandResultDTO("Your argument was incorrect. The command was not executed.", true);
        }
        Organization organization = (Organization) arg;
        dataManager.updateOrganizationById(inputArg, organization);
        return new CommandResultDTO("Element was updated if id was in the table", true);
    }


    @Override
    public int getOrganizationId() {
        try {
            return Integer.parseInt((String) inputId);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
