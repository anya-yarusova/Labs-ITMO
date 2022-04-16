package com.anyarusova.client.commands;


import com.anyarusova.client.data.Organization;
import com.anyarusova.client.utility.CollectionManager;
import com.anyarusova.client.utility.OutputManager;
import com.anyarusova.client.utility.OrganizationMaker;
import com.anyarusova.client.utility.UserInputManager;


public class AddCommand extends Command {
    private final UserInputManager userInputManager;
    private final OutputManager outputManager;
    private final CollectionManager collectionManager;

    public AddCommand(CollectionManager collectionManager, UserInputManager userInputManager, OutputManager outputManager) {
        super("add");
        this.collectionManager = collectionManager;
        this.userInputManager = userInputManager;
        this.outputManager = outputManager;
    }

    @Override
    public CommandResult execute(String arg) {
        Organization organization = new OrganizationMaker(userInputManager, outputManager, collectionManager).makeOrganization();
        collectionManager.getMainData().add(organization);
        return new CommandResult(false, "The element was added successfully");
    }
}
