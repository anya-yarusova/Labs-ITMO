package com.anyarusova.client.commands;

import com.anyarusova.client.utility.CommandManager;

public class HistoryCommand extends Command {
    private final CommandManager commandManager;

    public HistoryCommand(CommandManager commandManager) {
        super("history");
        this.commandManager = commandManager;
    }

    @Override
    public CommandResult execute(String arg) {
        return new CommandResult(false, commandManager.niceToString());
    }
}
