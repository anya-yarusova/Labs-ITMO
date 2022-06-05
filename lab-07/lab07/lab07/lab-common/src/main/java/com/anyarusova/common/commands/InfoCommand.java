package com.anyarusova.common.commands;

import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.utility.DataManager;
import com.anyarusova.common.utility.HistoryKeeper;

import java.io.Serializable;

public class InfoCommand extends Command {

    public InfoCommand() {
        super("info", null);
    }

    @Override
    public CommandResultDTO execute(DataManager dataManager, HistoryKeeper historyKeeper, String username) {
        historyKeeper.addNote(this.getName());
        return new CommandResultDTO(dataManager.getInfoAboutCollections(), true);
    }
    public static final class InfoCommandResult implements Serializable {
        private final int numberOfElements;
        private final long biggestAnnualTurnover;

        public InfoCommandResult(Integer numberOfElements, long biggestAnnualTurnover) {
            this.numberOfElements = numberOfElements;
            this.biggestAnnualTurnover = biggestAnnualTurnover;
        }

        @Override
        public String toString() {
            return "InfoCommandResult{"
                    + "numberOfElements='" + numberOfElements + '\''
                    + ", biggestAnnualTurnover=" + biggestAnnualTurnover
                    + '}';
        }
    }
}
