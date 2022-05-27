package com.anyarusova.common.commands;

import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.CollectionManager;
import com.anyarusova.common.utility.HistoryKeeper;

import java.io.Serializable;
import java.util.StringJoiner;

public class FilterStartsWithNameCommand extends Command {

    public FilterStartsWithNameCommand(Serializable arg) {
        super("filter_starts_with_name", arg);
    }

    @Override
    public CommandResultDTO execute(CollectionManager collectionManager, HistoryKeeper historyKeeper) {
        historyKeeper.addNote(this.getName());
        StringJoiner output = new StringJoiner("\n\n");
        String inputName;
        try {
            inputName = (String) arg;
        } catch (NullPointerException | IllegalArgumentException e) {
            return new CommandResultDTO("Your argument was incorrect");
        }
        if (collectionManager.getMainData().isEmpty()) {
            return new CommandResultDTO("Collection is empty");
        }
        collectionManager.getMainData().stream().filter(it -> it.getName().startsWith(inputName)).
                forEach(it -> output.add(it.toString()));
        if (output.length() == 0) {
            return new CommandResultDTO("There is no elements with name value starts with the entered string");
        }
        return new CommandResultDTO(output.toString());
    }

}
