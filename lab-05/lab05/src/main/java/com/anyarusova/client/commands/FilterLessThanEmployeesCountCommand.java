package com.anyarusova.client.commands;


import com.anyarusova.client.data.Organization;
import com.anyarusova.client.utility.CollectionManager;

import java.util.StringJoiner;

public class FilterLessThanEmployeesCountCommand extends Command {

    private final CollectionManager collectionManager;

    public FilterLessThanEmployeesCountCommand(CollectionManager collectionManager) {
        super("filter_less_than_employees_count");
        this.collectionManager = collectionManager;
    }

    @Override
    public CommandResult execute(String arg) {
        StringJoiner output = new StringJoiner("\n\n");
        Long inpEmployeesCount;
        try {
            inpEmployeesCount = Long.parseLong(arg);
        } catch (IllegalArgumentException e) {
            return new CommandResult(false, "Your argument was incorrect");
        }

        for (Organization organization : collectionManager.getMainData()) {
            Long employeesCount = organization.getEmployeesCount();
            if (employeesCount.compareTo(inpEmployeesCount) < 0) {
                output.add(organization.toString());
            }
        }

        return new CommandResult(false, output.toString());
    }

}
