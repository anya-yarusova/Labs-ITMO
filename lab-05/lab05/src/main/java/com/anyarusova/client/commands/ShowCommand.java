package com.anyarusova.client.commands;

import com.anyarusova.client.data.Organization;
import com.anyarusova.client.utility.CollectionManager;

import java.util.StringJoiner;


public class ShowCommand extends Command {
    private final CollectionManager collectionManager;

    public ShowCommand(CollectionManager collectionManager) {
        super("show");
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResult execute(String arg) {
        StringJoiner output = new StringJoiner("\n\n");
        if (collectionManager.getMainData().isEmpty()) {
            return new CommandResult(false, "Collection is empty");
        }
        for (Organization organization : collectionManager.getMainData()) {
            output.add(organization.toString());
        }

        return new CommandResult(false, output.toString());
    }
}
