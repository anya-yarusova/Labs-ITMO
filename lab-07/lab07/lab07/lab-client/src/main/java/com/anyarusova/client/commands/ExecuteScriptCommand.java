package com.anyarusova.client.commands;

import com.anyarusova.client.utility.InputManager;
import com.anyarusova.common.dto.CommandResultDTO;

import java.io.File;
import java.io.FileNotFoundException;

public class ExecuteScriptCommand {

    private final String arg;

    public ExecuteScriptCommand(String arg) {
        this.arg = arg;
    }

    public CommandResultDTO execute(InputManager inputManager) {
        try {
            inputManager.connectToFile(new File(this.arg));
            return new CommandResultDTO("Starting to execute script...", true);
        } catch (FileNotFoundException e) {
            return new CommandResultDTO("There was a problem opening the file. Check if it is available and you have written it in the command arg correctly.", false);
        } catch (UnsupportedOperationException e) {
            return new CommandResultDTO(e.getMessage(), false);
        }
    }
}
