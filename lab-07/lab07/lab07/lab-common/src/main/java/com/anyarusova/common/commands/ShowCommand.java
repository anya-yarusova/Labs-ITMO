package com.anyarusova.common.commands;

import com.anyarusova.common.data.Organization;
import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.CollectionManager;
import com.anyarusova.common.utility.HistoryKeeper;

import java.io.Serializable;
import java.util.Comparator;
import java.util.stream.Collectors;

public class ShowCommand extends Command {

    public ShowCommand() {
        super("show", null);
    }

    @Override
    public CommandResultDTO execute(CollectionManager collectionManager, HistoryKeeper historyKeeper) {
        historyKeeper.addNote(this.getName());
        if (collectionManager.getMainData().isEmpty()) {
            return new CommandResultDTO("Collection is empty");
        }
        Serializable output = (Serializable) collectionManager.getMainData().stream().sorted(Comparator.comparing(Organization::getName)).collect(Collectors.toList());
        return new CommandResultDTO(output);
    }
}
