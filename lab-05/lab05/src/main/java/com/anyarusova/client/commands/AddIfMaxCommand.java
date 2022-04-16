package com.anyarusova.client.commands;

import com.anyarusova.client.data.Organization;
import com.anyarusova.client.utility.CollectionManager;
import com.anyarusova.client.utility.OutputManager;
import com.anyarusova.client.utility.OrganizationMaker;
import com.anyarusova.client.utility.UserInputManager;


public class AddIfMaxCommand extends Command {
    private final OutputManager outputManager;
    private final CollectionManager collectionManager;
    private final UserInputManager userInputManager;

    public AddIfMaxCommand(CollectionManager collectionManager, UserInputManager userInputManager, OutputManager outputManager) {
        super("add_if_max");
        this.collectionManager = collectionManager;
        this.userInputManager = userInputManager;
        this.outputManager = outputManager;
    }

    @Override
    public CommandResult execute(String arg) {
        Organization organization = new OrganizationMaker(userInputManager, outputManager, collectionManager).makeOrganization();
        if (collectionManager.getMainData().isEmpty() || organization.compareTo(collectionManager.getMainData().peek()) < 0) {
            collectionManager.getMainData().add(organization);
            return new CommandResult(false, "The element was added successfully");
        } else {
            return new CommandResult(false, "The element was not min, so it was not added");
        }
    }
}
