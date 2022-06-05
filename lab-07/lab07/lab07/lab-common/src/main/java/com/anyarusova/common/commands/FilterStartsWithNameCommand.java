package com.anyarusova.common.commands;

import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.DataManager;
import com.anyarusova.common.utility.HistoryKeeper;

import java.io.Serializable;

public class FilterStartsWithNameCommand extends Command {

    public FilterStartsWithNameCommand(Serializable arg) {
        super("filter_starts_with_name", arg);
    }

    @Override
    public CommandResultDTO execute(DataManager dataManager, HistoryKeeper historyKeeper, String username) {
        historyKeeper.addNote(this.getName());
        String inputName;
        try {
            inputName = (String) arg;
        } catch (NullPointerException | IllegalArgumentException e) {
            return new CommandResultDTO("Your argument was incorrect", true);
        }
        if (dataManager.getMaxByIdOrganization() == null) {
            return new CommandResultDTO("Collection is empty", true);
        }
        String output = dataManager.filterStartsWithName(inputName);
        if (output.length() == 0) {
            return new CommandResultDTO("There is no elements with name value starts with the entered string", true);
        }
        return new CommandResultDTO(output, true);
    }

}
