package com.anyarusova.client.commands;

import com.anyarusova.client.data.Organization;
import com.anyarusova.client.utility.CollectionManager;
import com.anyarusova.client.utility.OutputManager;
import com.anyarusova.client.utility.OrganizationMaker;
import com.anyarusova.client.utility.UserInputManager;

public class UpdateCommand extends Command {

    private final OutputManager outputManager;
    private final UserInputManager userInputManager;
    private final CollectionManager collectionManager;

    public UpdateCommand(CollectionManager collectionManager, UserInputManager userInputManager, OutputManager outputManager) {
        super("update_id");
        this.userInputManager = userInputManager;
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
    }

    @Override
    public CommandResult execute(String arg) {
        int intArg;
        try {
            intArg = Integer.parseInt(arg);
        } catch (NumberFormatException e) {
            return new CommandResult(false, "Your argument was incorrect. The command was not executed.");
        }

        if (collectionManager.getMainData().removeIf(x -> x.getId() == intArg)) {
            Organization organization = new OrganizationMaker(userInputManager, outputManager, collectionManager).makeOrganization();
            organization.setId(intArg);
            collectionManager.getMainData().add(organization);
            return new CommandResult(false, "The element was updated successfully");
        } else {
            return new CommandResult(false, "Written id was not found. The command was not executed");
        }
    }
}
