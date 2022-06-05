package com.anyarusova.common.dto;

import com.anyarusova.common.commands.Command;

import java.io.Serializable;
import java.util.Objects;

public class CommandDTO implements Serializable {

    private final Command command;
    private final String login;
    private final String password;

    public CommandDTO(Command command, String login, String password) {
        this.command = command;
        this.login = login;
        this.password = password;
    }

    public CommandDTO(Command command) {
        this.command = command;
        this.login = "";
        this.password = "";
    }

    public Command getCommand() {
        return command;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
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
        return Objects.equals(command, that.command) && Objects.equals(login, that.login) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command, login, password);
    }
}
