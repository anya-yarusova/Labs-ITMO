package com.anyarusova.client.utility;

import com.google.gson.JsonSyntaxException;
import com.anyarusova.client.commands.CommandResult;
import com.anyarusova.client.data.Organization;

import java.io.IOException;
import java.util.Objects;
import java.util.PriorityQueue;


/**
 * The main class for app to be run
 */
public class Console {
    private final CommandManager commandManager;
    private final OutputManager outputManager;
    private final CollectionManager collectionManager;
    private final FileManager fileManager;
    private final UserInputManager userInputManager;

    public Console(FileManager fileManager, UserInputManager userInputManager,
                   CollectionManager collectionManager, OutputManager outputManager,
                   CommandManager commandManager) {
        this.fileManager = fileManager;
        this.userInputManager = userInputManager;
        this.collectionManager = collectionManager;
        this.outputManager = outputManager;
        this.commandManager = commandManager;
    }

    /**
     * Loads data into fileManager.mainData (a PriorityQueue) and starts listening to user command input
     */
    public void start() throws IllegalArgumentException, JsonSyntaxException, IOException {
        String stringData = fileManager.read();

        PriorityQueue<Organization> organizations = new JsonParser().deSerialize(stringData);
        collectionManager.initialiseData(organizations);

        startCommandCycle();
    }

    private String[] parseToNameAndArg(String input) {
        String[] arrayInput = input.trim().split("\\s+");
        String inputCommand = arrayInput[0];
        String inputArg = "";
        if (arrayInput.length >= 2) {
            inputArg = arrayInput[1];
        }
        return new String[]{inputCommand, inputArg};
    }

    private String readNextCommand() {
        outputManager.print(">>>");
        return userInputManager.nextLine();
    }

    private void startCommandCycle() {
        CommandResult commandResult;
        do {
            String name = "";
            String arg = "";
            String input = readNextCommand();
            String[] commandNameAndArg = parseToNameAndArg(input);
            if (commandNameAndArg.length >= 1) {
                name = commandNameAndArg[0];
            }
            if (commandNameAndArg.length >= 2) {
                arg = commandNameAndArg[1];
            }
            commandResult = commandManager.runCommand(name, arg);
            outputManager.println(commandResult.getOutput());
        } while (!Objects.requireNonNull(commandResult).isExit());
    }
}
