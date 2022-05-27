package com.anyarusova.common.commands;

import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.CollectionManager;
import com.anyarusova.common.utility.HistoryKeeper;

import java.io.Serializable;
import java.util.StringJoiner;

public class FilterLessThanEmployeesCountCommand extends Command {

    public FilterLessThanEmployeesCountCommand(Serializable arg) {
        super("filter_less_than_employees_count", arg);
    }

    @Override
    public CommandResultDTO execute(CollectionManager collectionManager, HistoryKeeper historyKeeper) {
        historyKeeper.addNote(this.getName());
        StringJoiner output = new StringJoiner("\n\n");
        Long input;
        try {
            input = Long.parseLong((String) arg);
        } catch (IllegalArgumentException e) {
            return new CommandResultDTO("Your argument was incorrect");
        }
        if (collectionManager.getMainData().isEmpty()) {
            return new CommandResultDTO("Collection is empty");
        }
        collectionManager.getMainData().stream().filter(it -> it.getEmployeesCount().
                compareTo(input) < 0).forEach(it -> output.add(it.toString()));
        if (output.length() == 0) {
            return new CommandResultDTO("There is no elements with employeesCount value less than entered");
        }
        return new CommandResultDTO(output.toString());
    }

}
