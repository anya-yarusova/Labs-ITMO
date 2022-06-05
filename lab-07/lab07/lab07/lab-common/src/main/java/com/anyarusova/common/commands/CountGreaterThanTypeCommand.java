package com.anyarusova.common.commands;

import com.anyarusova.common.data.OrganizationType;
import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.DataManager;
import com.anyarusova.common.utility.HistoryKeeper;

import java.io.Serializable;

public class CountGreaterThanTypeCommand extends Command {

    public CountGreaterThanTypeCommand(Serializable arg) {
        super("count_greater_than_type", arg);
    }

    @Override
    public CommandResultDTO execute(DataManager dataManager, HistoryKeeper historyKeeper, String username) {
        historyKeeper.addNote(this.getName());
        OrganizationType inputType;
        try {
            inputType = OrganizationType.valueOf((String) arg);
        } catch (IllegalArgumentException e) {
            return new CommandResultDTO("Your argument was incorrect", true);
        }
        return new CommandResultDTO(dataManager.countGreaterThanType(inputType), true);
    }
}
