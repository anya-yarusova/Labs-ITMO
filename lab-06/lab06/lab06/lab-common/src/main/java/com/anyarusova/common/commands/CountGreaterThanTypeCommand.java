package com.anyarusova.common.commands;

import com.anyarusova.common.data.OrganizationType;
import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.CollectionManager;
import com.anyarusova.common.utility.HistoryKeeper;

import java.io.Serializable;

public class CountGreaterThanTypeCommand extends Command {

    public CountGreaterThanTypeCommand(Serializable arg) {
        super("count_greater_than_type", arg);
    }

    @Override
    public CommandResultDTO execute(CollectionManager collectionManager, HistoryKeeper historyKeeper) {
        historyKeeper.addNote(this.getName());
        OrganizationType inputType;
        try {
            inputType = OrganizationType.valueOf((String) arg);
        } catch (IllegalArgumentException e) {
            return new CommandResultDTO("Your argument was incorrect");
        }
        Long counter = collectionManager.getMainData().stream().filter(it -> it.getType().compareTo(inputType) > 0).count();
        return new CommandResultDTO(counter.toString());
    }
}
