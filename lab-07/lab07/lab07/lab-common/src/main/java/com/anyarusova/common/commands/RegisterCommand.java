package com.anyarusova.common.commands;

import com.anyarusova.common.dto.CommandResultDTO;
import com.anyarusova.common.data.User;
import com.anyarusova.common.utility.DataManager;
import com.anyarusova.common.utility.HistoryKeeper;

import java.io.Serializable;
import java.util.Objects;

/**
 * This command doesn't even need a user to use correct login and password because it doesn't check on server side
 */
public class RegisterCommand extends Command {

    public RegisterCommand(Serializable arg) {
        super("register", arg);
    }

    @Override
    public CommandResultDTO execute(
            DataManager dataManager,
            HistoryKeeper historyKeeper,
            String username
    ) {
        String[] loginAndPassword = (String[]) arg;
        historyKeeper.addNote(this.getName());

        if (dataManager.checkIfUsernameUnique(loginAndPassword[0])) {
            dataManager.addUser(new User(-1, loginAndPassword[1], loginAndPassword[0]));
        } else {
            return new RegisterCommandResult(false);
        }

        return new RegisterCommandResult(true);
    }

    public static class RegisterCommandResult extends CommandResultDTO {
        private final boolean wasRegistered;

        public RegisterCommandResult(boolean wasRegistered) {
            super(wasRegistered ? "New user registered!" : "Username was not unique so the user was not registered.", true);
            this.wasRegistered = wasRegistered;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            RegisterCommandResult that = (RegisterCommandResult) o;
            return wasRegistered == that.wasRegistered;
        }

        @Override
        public int hashCode() {
            return Objects.hash(wasRegistered);
        }

        public boolean isWasRegistered() {
            return wasRegistered;
        }

        @Override
        public String toString() {
            return "RegisterCommandResult{"
                    + "wasRegistered=" + wasRegistered
                    + '}';
        }
    }

}
