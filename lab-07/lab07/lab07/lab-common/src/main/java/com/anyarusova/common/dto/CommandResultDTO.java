package com.anyarusova.common.dto;

import java.io.Serializable;
import java.util.Objects;

public class CommandResultDTO implements Serializable {

    private final Serializable output;
    private final boolean wasExecutedCorrectly;

    public CommandResultDTO(Serializable output, boolean wasExecutedCorrectly) {
        this.output = output;
        this.wasExecutedCorrectly = wasExecutedCorrectly;
    }

    public boolean isWasExecutedCorrectly() {
        return wasExecutedCorrectly;
    }

    public Serializable getOutput() {
        return output;
    }

    @Override
    public String toString() {
        return "CommandResultDto{"
                + "output=" + output
                + ", wasExecutedCorrectly=" + wasExecutedCorrectly
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CommandResultDTO that = (CommandResultDTO) o;
        return wasExecutedCorrectly == that.wasExecutedCorrectly && Objects.equals(output, that.output);
    }

    @Override
    public int hashCode() {
        return Objects.hash(output, wasExecutedCorrectly);
    }

}
