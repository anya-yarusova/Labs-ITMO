package com.anyarusova.client.commands;

import com.anyarusova.client.utility.CollectionManager;

public class ClearCommand extends Command {
    private final CollectionManager collectionManager;

    public ClearCommand(CollectionManager collectionManager) {
        super("clear");
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResult execute(String arg) {
        collectionManager.getMainData().clear();
        return new CommandResult(false, "The collection was cleared successfully.");
    }
}
