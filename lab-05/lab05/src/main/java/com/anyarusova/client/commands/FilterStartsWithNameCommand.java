package com.anyarusova.client.commands;


import com.anyarusova.client.data.Organization;
import com.anyarusova.client.utility.CollectionManager;

import java.util.StringJoiner;

public class FilterStartsWithNameCommand extends Command {

    private final CollectionManager collectionManager;

    public FilterStartsWithNameCommand(CollectionManager collectionManager) {
        super("filter_starts_with_name");
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResult execute(String arg) {
        StringJoiner output = new StringJoiner("\n\n");
        String inpName;
        try {
            inpName = arg;
        } catch (IllegalArgumentException e) {
            return new CommandResult(false, "Your argument was incorrect");
        }

        for (Organization organization : collectionManager.getMainData()) {
            String name = organization.getName();
            if (name.startsWith(inpName)) {
                output.add(organization.toString());
            }
        }

        return new CommandResult(false, output.toString());
    }

}
