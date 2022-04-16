package com.anyarusova.client.utility;

import com.anyarusova.client.commands.AddCommand;
import com.anyarusova.client.commands.AddIfMaxCommand;
import com.anyarusova.client.commands.ClearCommand;
import com.anyarusova.client.commands.Command;
import com.anyarusova.client.commands.CommandResult;
import com.anyarusova.client.commands.CountGreaterThanTypeCommand;
import com.anyarusova.client.commands.ExecuteScriptCommand;
import com.anyarusova.client.commands.ExitCommand;
import com.anyarusova.client.commands.FilterLessThanEmployeesCountCommand;
import com.anyarusova.client.commands.HelpCommand;
import com.anyarusova.client.commands.HistoryCommand;
import com.anyarusova.client.commands.InfoCommand;
import com.anyarusova.client.commands.FilterStartsWithNameCommand;
import com.anyarusova.client.commands.RemoveByIdCommand;
import com.anyarusova.client.commands.RemoveLowerCommand;
import com.anyarusova.client.commands.SaveCommand;
import com.anyarusova.client.commands.ShowCommand;
import com.anyarusova.client.commands.UpdateCommand;

import java.util.HashSet;
import java.util.Queue;
import java.util.StringJoiner;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Class for storing commands objects.
 */
public class CommandManager {
    private final HashSet<Command> commands = new HashSet<>();
    private final int capacity = 8;
    private final Queue<String> history = new ArrayBlockingQueue<>(capacity);

    public CommandManager(FileManager fileManager, UserInputManager userInputManager,
                          CollectionManager collectionManager, OutputManager outputManager) {
        commands.add(new HelpCommand());
        commands.add(new AddCommand(collectionManager, userInputManager, outputManager));
        commands.add(new SaveCommand(fileManager, collectionManager));
        commands.add(new ShowCommand(collectionManager));
        commands.add(new UpdateCommand(collectionManager, userInputManager, outputManager));
        commands.add(new RemoveByIdCommand(collectionManager));
        commands.add(new ClearCommand(collectionManager));
        commands.add(new ExecuteScriptCommand(userInputManager));
        commands.add(new AddIfMaxCommand(collectionManager, userInputManager, outputManager));
        commands.add(new RemoveLowerCommand(collectionManager, userInputManager, outputManager));
        commands.add(new CountGreaterThanTypeCommand(collectionManager));
        commands.add(new FilterLessThanEmployeesCountCommand(collectionManager));
        commands.add(new FilterStartsWithNameCommand(collectionManager));
        commands.add(new InfoCommand(collectionManager));
        commands.add(new ExitCommand());
        commands.add(new HistoryCommand(this));
    }

    public HashSet<Command> getCommands() {
        return commands;
    }

    public CommandResult runCommand(String name, String arg) {
        CommandResult commandResult = null;
        for (Command command : this.getCommands()) {
            if (command.getName().equals(name)) {
                commandResult = command.execute(arg);
                this.addNote(command.getName());
                break;
            }
        }
        if (commandResult == null) {
            return new CommandResult(false, "This command was not found. Please use \"help\" to know about available commands");
        }
        return commandResult;
    }

    public void addNote(String note) {
        if (history.size() == capacity) {
            history.remove();
        }
        history.add(note);
    }

    public String niceToString() {
        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.add("The last 8 commands were:");
        for (String commandName : history) {
            stringJoiner.add(commandName);
        }
        return stringJoiner.toString();
    }
}
