package com.anyarusova.common.dto;

import java.io.Serializable;

public class CommandResultDTO implements Serializable {

    private final Serializable output;

    public CommandResultDTO(Serializable output) {
        this.output = output;
    }

    public Serializable getOutput() {
        return output;
    }

    @Override
    public String toString() {
        return "CommandResult{"
                + "output='" + output + '\''
                + '}';
    }

}
