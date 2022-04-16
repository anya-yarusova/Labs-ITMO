package com.anyarusova.client.commands;


import com.anyarusova.client.data.Organization;
import com.anyarusova.client.data.OrganizationType;
import com.anyarusova.client.utility.CollectionManager;

import java.util.StringJoiner;

public class CountGreaterThanTypeCommand extends Command {

    private final CollectionManager collectionManager;

    public CountGreaterThanTypeCommand(CollectionManager collectionManager) {
        super("count_greater_than_type");
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResult execute(String arg) {
        StringJoiner output = new StringJoiner("\n\n");
        OrganizationType inpType;
        try {
            inpType = OrganizationType.valueOf(arg);
        } catch (IllegalArgumentException e) {
            return new CommandResult(false, "Your argument was incorrect");
        }

        for (Organization organization : collectionManager.getMainData()) {
            Integer counter = 0;
            OrganizationType type = organization.getType();
            if (type.compareTo(inpType) > 0) {
                counter++;
            }
            output.add(counter.toString());
        }

        return new CommandResult(false, output.toString());
    }
}
