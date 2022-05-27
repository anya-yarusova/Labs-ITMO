package com.anyarusova.common.dto;

import com.anyarusova.common.commands.Command;

import java.io.Serializable;
import java.util.Objects;

public class CommandDTO implements Serializable {

    private final Command command;

    public CommandDTO(Command command) {
        this.command = command;
    }

    public Command getCommand() {
        return command;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommandDTO that = (CommandDTO) o;
        return Objects.equals(command, that.command);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command);
    }
}
