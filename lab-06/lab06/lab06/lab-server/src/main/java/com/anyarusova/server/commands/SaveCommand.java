package com.anyarusova.server.commands;

import com.anyarusova.common.commands.Command;
import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.CollectionManager;
import com.anyarusova.common.utility.HistoryKeeper;
import com.anyarusova.server.utilty.FileManager;
import com.anyarusova.server.utilty.JsonParser;

import java.io.FileNotFoundException;

public class SaveCommand extends Command {

    private final FileManager fileManager;

    public SaveCommand(FileManager fileManager) {
        super("save", null);
        this.fileManager = fileManager;
    }

    @Override
    public CommandResultDTO execute(CollectionManager collectionManager, HistoryKeeper historyKeeper) {
        try {
            fileManager.save(new JsonParser().serialize(collectionManager.getMainData()));
        } catch (FileNotFoundException e) {
            return new CommandResultDTO("There was a problem saving a file. Please restart the program with another one");
        }
        return new CommandResultDTO("The data was saved successfully");
    }

}
