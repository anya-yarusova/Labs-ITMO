package com.anyarusova.common.commands;

import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.DataManager;
import com.anyarusova.common.utility.HistoryKeeper;

import java.io.Serializable;

public class FilterLessThanEmployeesCountCommand extends Command {

    public FilterLessThanEmployeesCountCommand(Serializable arg) {
        super("filter_less_than_employees_count", arg);
    }

    @Override
    public CommandResultDTO execute(DataManager dataManager, HistoryKeeper historyKeeper, String username) {
        historyKeeper.addNote(this.getName());
        Long input;
        try {
            input = Long.parseLong((String) arg);
        } catch (IllegalArgumentException e) {
            return new CommandResultDTO("Your argument was incorrect", true);
        }
        if (dataManager.getMaxByIdOrganization() == null) {
            return new CommandResultDTO("Collection is empty", true);
        }
        String output = dataManager.filterLessThanEmployeesCount(input);
        if (output.length() == 0) {
            return new CommandResultDTO("There is no elements with employeesCount value less than entered", true);
        }
        return new CommandResultDTO(output, true);
    }

}
