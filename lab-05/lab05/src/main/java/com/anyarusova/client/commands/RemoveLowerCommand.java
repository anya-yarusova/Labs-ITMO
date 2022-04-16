package com.anyarusova.client.commands;

import com.anyarusova.client.data.Organization;
import com.anyarusova.client.utility.CollectionManager;
import com.anyarusova.client.utility.OutputManager;
import com.anyarusova.client.utility.OrganizationMaker;
import com.anyarusova.client.utility.UserInputManager;

public class RemoveLowerCommand extends Command {

    private final OutputManager outputManager;
    private final CollectionManager collectionManager;
    private final UserInputManager userInputManager;

    public RemoveLowerCommand(CollectionManager collectionManager, UserInputManager userInputManager, OutputManager outputManager) {
        super("remove_lower");
        this.outputManager = outputManager;
        this.collectionManager = collectionManager;
        this.userInputManager = userInputManager;
    }

    @Override
    public CommandResult execute(String arg) {
        Organization organization = new OrganizationMaker(userInputManager, outputManager, collectionManager).makeOrganization();
        collectionManager.getMainData().removeIf(x -> x.compareTo(organization) < 0);
        return new CommandResult(false, "Lower elements were removed successfully");
    }
}
