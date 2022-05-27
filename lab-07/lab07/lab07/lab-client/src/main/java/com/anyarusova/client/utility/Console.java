package com.anyarusova.client.utility;

import com.anyarusova.client.commands.ExecuteScriptCommand;
import com.anyarusova.common.commands.Command;
import com.anyarusova.common.commands.AddCommand;
import com.anyarusova.common.commands.AddIfMaxCommand;
import com.anyarusova.common.commands.ClearCommand;
import com.anyarusova.common.commands.CountGreaterThanTypeCommand;
import com.anyarusova.common.commands.FilterLessThanEmployeesCountCommand;
import com.anyarusova.common.commands.FilterStartsWithNameCommand;
import com.anyarusova.common.commands.HelpCommand;
import com.anyarusova.common.commands.HistoryCommand;
import com.anyarusova.common.commands.InfoCommand;
import com.anyarusova.common.commands.RemoveByIdCommand;
import com.anyarusova.common.commands.RemoveLowerCommand;
import com.anyarusova.common.commands.ShowCommand;
import com.anyarusova.common.commands.UpdateCommand;
import com.anyarusova.common.dto.CommandDTO;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.NoSuchElementException;

/**
 * The main class for app to be run
 */
public class Console {

    private final OutputManager outputManager;
    private final InputManager inputManager;
    private final OrganizationMaker organizationMaker;
    private final DatagramSocketManager datagramSocketManager;
    private final HashSet<String> listOfCommands;

    public Console(OutputManager outputManager, InputManager inputManager,
                   DatagramSocketManager datagramSocketManager, HashSet<String> listOfCommands) {
        this.outputManager = outputManager;
        this.inputManager = inputManager;
        this.datagramSocketManager = datagramSocketManager;
        this.listOfCommands = listOfCommands;
        this.organizationMaker = new OrganizationMaker(inputManager, outputManager);
    }

    public void start() throws ClassNotFoundException, IOException {
        String input;
        do {
            input = readNextCommand();
            final String[] parsedInp = parseToNameAndArg(input);
            final String commandName = parsedInp[0];
            Serializable commandArg = parsedInp[1];
            Serializable commandArg2 = null;
            if (listOfCommands.contains(commandName)) {
                if ("add".equals(commandName) || "add_if_max".equals(commandName) || "remove_lower".equals(commandName)) {
                    commandArg = organizationMaker.makeOrganization();
                }
                if ("update_id".equals(commandName)) {
                    commandArg2 = commandArg;
                    commandArg = organizationMaker.makeOrganization();
                }
                if ("execute_script".equals(commandName)) {
                    outputManager.println((new ExecuteScriptCommand((String) commandArg).execute(inputManager).getOutput().toString()));
                } else {
                    try {
                        datagramSocketManager.sendCommand(new CommandDTO(getCommandObjectByName(commandName, commandArg, commandArg2)));
                        outputManager.println(datagramSocketManager.receiveCommandResult().getOutput().toString());
                    } catch (IOException e) {
                        outputManager.println("Could not send a command");
                    }
                }
            } else {
                outputManager.println("The command was not found. Please use \"help\" to know about commands.");
            }
        } while (!"exit".equals(input));
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

    private String readNextCommand() throws IOException {
        outputManager.print(">>>");
        try {
            return inputManager.nextLine();
        } catch (NoSuchElementException e) {
            return "exit";
        }
    }

    private Command getCommandObjectByName(String commandName, Serializable arg, Serializable arg2) {
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
