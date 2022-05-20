package com.anyarusova.client.commands;


public class HelpCommand extends Command {
    public HelpCommand() {
        super("help");
    }

    @Override
    public CommandResult execute(String arg) {
        return new CommandResult(false,
                "help : gives information about available commands\n"
                        + "info : gives information about collection\n"
                        + "show : shows every element in collection with string\n"
                        + "add {element} : adds new element to collection\n"
                        + "update_id {element} : update element info by it's id\n"
                        + "remove_by_id id : delete element by it's id\n"
                        + "clear : clears collection\n"
                        + "save : saves collection to a file\n"
                        + "execute_script file_name : executes script entered in a file\n"
                        + "exit : exits the program (!!!does not save data!!!)\n"
                        + "add_if_max {element} : adds new element to the collection if it's value more than max element's value\n"
                        + "remove_lower {element} : deletes every element in the collection with value less than entered element's value\n"
                        + "history : shows last 8 command usages\n"
                        + "count_greater_than_type type: shows count of elements with type value more than entered\n"
                        + "filter_less_than_employees_count employeesCount : shows every element with employeesCount value less than entered\n"
                        + "filter_starts_with_name name : shows every element with name value starts with entered string");
    }
}
