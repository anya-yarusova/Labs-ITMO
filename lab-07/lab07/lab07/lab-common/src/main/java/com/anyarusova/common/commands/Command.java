package com.anyarusova.common.commands;

import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.DataManager;
import com.anyarusova.common.utility.HistoryKeeper;

import java.io.Serializable;

/**
 * This is an abstract class for all the commands.
 */
public abstract class Command implements Serializable {

    protected final Serializable arg;
    private final String name;

    protected Command(String name, Serializable arg) {
        this.name = name;
        this.arg = arg;
    }

    public abstract CommandResultDTO execute(DataManager dataManager, HistoryKeeper historyKeeper, String username);

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Command{"
                + "name='" + name + '\''
                + ", arg=" + arg
                + '}';
    }
}
