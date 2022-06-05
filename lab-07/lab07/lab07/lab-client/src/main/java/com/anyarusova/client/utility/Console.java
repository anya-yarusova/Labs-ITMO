package com.anyarusova.client.utility;

import com.anyarusova.client.commands.ExecuteScriptCommand;
import com.anyarusova.common.commands.Command;
import com.anyarusova.common.commands.AddIfMaxCommand;
import com.anyarusova.common.commands.AddCommand;
import com.anyarusova.common.commands.ClearCommand;
import com.anyarusova.common.commands.CountGreaterThanTypeCommand;
import com.anyarusova.common.commands.FilterStartsWithNameCommand;
import com.anyarusova.common.commands.FilterLessThanEmployeesCountCommand;
import com.anyarusova.common.commands.HelpCommand;
import com.anyarusova.common.commands.HistoryCommand;
import com.anyarusova.common.commands.InfoCommand;
import com.anyarusova.common.commands.RegisterCommand;
import com.anyarusova.common.commands.RemoveLowerCommand;
import com.anyarusova.common.commands.RemoveByIdCommand;
import com.anyarusova.common.commands.ShowCommand;
import com.anyarusova.common.commands.UpdateCommand;
import com.anyarusova.common.dto.CommandDTO;
import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.DataCantBeSentException;

import java.nio.channels.UnresolvedAddressException;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.NoSuchElementException;

/**
 * The main class for app to be run
 */
public class Console {

    private static final int MAX_STRING_LENGTH = 100;
    private final OutputManager outputManager;
    private final InputManager inputManager;
    private final ConnectionManager connectionManager;
    private final HashSet<String> listOfCommands;
    private String username;
    private String password;


    public Console(
            OutputManager outputManager,
            InputManager inputManager,
            ConnectionManager connectionManager,
            HashSet<String> listOfCommands
    ) {
        this.outputManager = outputManager;
        this.inputManager = inputManager;
        this.connectionManager = connectionManager;
        this.listOfCommands = listOfCommands;
    }

    public void start() throws ClassNotFoundException, IOException, DataCantBeSentException, UnresolvedAddressException {
        initUsernameAndPassword();
        OrganizationMaker organizationMaker = new OrganizationMaker(inputManager, outputManager, username);
        String input;
        do {
            input = readNextCommand();
            if ("exit".equals(input)) {
                break;
            }
            final String[] parsedInp = parseToNameAndArg(input);
            final String commandName = parsedInp[0];
            Serializable commandArg = parsedInp[1];
            String commandArg2 = ""; // only for update command in this case
            if (listOfCommands.contains(commandName)) {
                if ("add".equals(commandName) || "add_if_min".equals(commandName) || "remove_greater".equals(commandName)) {
                    commandArg = organizationMaker.makeOrganization();
                }
                if ("update".equals(commandName)) {
                    commandArg2 = (String) commandArg;
                    commandArg = organizationMaker.makeOrganization();
                }
//                if ("register".equals(commandName)) {
//                    commandArg = dataObjectsMaker.makeLoginAndPassword();
//                }
                if ("execute_script".equals(commandName)) {
                    new ExecuteScriptCommand((String) commandArg).execute(inputManager);
                } else {
                    try {
                        outputManager.println(
                                connectionManager.sendCommand(new CommandDTO(getCommandObjectByName(commandName, commandArg, commandArg2), username, password)).getOutput().toString()
                        );
                    } catch (DataCantBeSentException e) {
                        outputManager.println("Could not send a command");
                    }
                }
            } else {
                outputManager.println("The command was not found. Please use \"help\" to know about commands.");
            }
        } while (true);
    }

    private void initUsernameAndPassword() throws IOException, DataCantBeSentException, UnresolvedAddressException {
        outputManager.println("Would you like to register first? (type \"yes\" to register or something else to continue with your own password+login).");
        final String answer = inputManager.nextLine();
        if ("yes".equals(answer)) {
            outputManager.println("Enter your new login");
            final String loginToRegister = inputManager.nextLine();
            outputManager.println("Enter new password");
            final String passwordToRegister = inputManager.nextLine();

            if (loginToRegister.length() > MAX_STRING_LENGTH || passwordToRegister.length() > MAX_STRING_LENGTH) {
                outputManager.println("Your password or login was too long");
                return;
            }

            CommandResultDTO registerCommandResult = connectionManager.sendCommand(new CommandDTO(new RegisterCommand(new String[]{loginToRegister, passwordToRegister})));
            if (registerCommandResult.isWasExecutedCorrectly()) {
                if (!((RegisterCommand.RegisterCommandResult) registerCommandResult).isWasRegistered()) {
                    outputManager.println("User was not registered because the username was not unique.");
                    initUsernameAndPassword();
                } else {
                    password = passwordToRegister;
                    username = loginToRegister;
                }
            } else {
                throw new DataCantBeSentException();
            }
        } else {
            outputManager.println("Enter login");
            username = inputManager.nextLine();
            outputManager.println("Enter password");
            password = inputManager.nextLine();
        }
    }

    private String[] parseToNameAndArg(String input) {
        String[] arrayInput = input.split(" ");
        String inputCommand = arrayInput[0];
        String inputArg = "";

        if (arrayInput.length >= 2) {
            inputArg = arrayInput[1];
        }

        return new String[]{inputCommand, inputArg};
    }

    private String readNextCommand() throws IOException {
        outputManager.print(">>>");
        try {
            return inputManager.nextLine();
        } catch (NoSuchElementException e) {
            return "exit";
        }
    }

    private Command getCommandObjectByName(String commandName, Serializable arg, String arg2) {
        Command command;
        switch (commandName) {
            case "add": command = new AddCommand(arg);
                break;
            case "add_if_max": command = new AddIfMaxCommand(arg);
                break;
            case "clear": command = new ClearCommand();
                break;
            case "count_greater_than_type": command = new CountGreaterThanTypeCommand(arg);
                break;
            case "filter_less_than_employees_count": command = new FilterLessThanEmployeesCountCommand(arg);
                break;
            case "filter_starts_with_name": command = new FilterStartsWithNameCommand(arg);
                break;
            case "history": command = new HistoryCommand();
                break;
            case "info": command = new InfoCommand();
                break;
            case "remove_by_id": command = new RemoveByIdCommand(arg);
                break;
            case "remove_lower": command = new RemoveLowerCommand(arg);
                break;
            case "show": command = new ShowCommand();
                break;
            case "update_id": command = new UpdateCommand(arg, arg2);
                break;
            case "help": command = new HelpCommand();
                break;
            default: command = null;
                break;
        }
        return command;
    }

}
