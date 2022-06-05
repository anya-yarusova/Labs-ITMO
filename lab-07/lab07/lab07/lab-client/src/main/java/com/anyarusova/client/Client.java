package com.anyarusova.client;

import com.anyarusova.client.utility.Console;
import com.anyarusova.client.utility.ConnectionManager;
import com.anyarusova.client.utility.InputManager;
import com.anyarusova.client.utility.OutputManager;
import com.anyarusova.common.utility.DataCantBeSentException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.UnresolvedAddressException;
import java.util.HashSet;
import java.util.function.Function;
import java.util.function.Predicate;

public final class Client {
    private static final BufferedReader BUFFERED_READER = new BufferedReader(new InputStreamReader(System.in));
    private static final OutputManager OUTPUT_MANAGER = new OutputManager(System.out);
    private static final int MAX_PORT = 65635;
    private static final int MIN_PORT = 1024;
    private static final HashSet<String> LIST_OF_COMMANDS = new HashSet<>();
    private static int serverPort;
    private static int clientPort;
    private static String clientIp;
    private static String serverIp;


    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        initCommandList();
        try {
            initMainInfoForConnection();
        } catch (IOException e) {
            OUTPUT_MANAGER.println("Something went wrong during initialisation client info");
            return;
        }
        try {
            ConnectionManager connectionManager = new ConnectionManager(clientPort, serverPort, clientIp, serverIp, OUTPUT_MANAGER);
            new Console(OUTPUT_MANAGER, new InputManager(System.in), connectionManager, LIST_OF_COMMANDS).start();
        } catch (ClassNotFoundException e) {
            OUTPUT_MANAGER.println("Found incorrect data from server.");
        } catch (IOException e) {
            OUTPUT_MANAGER.println("Something went wrong with IO, the message is: " + e.getMessage());
        } catch (DataCantBeSentException e) {
            OUTPUT_MANAGER.println("Some error happened and we could not sent your registration request to the server. Try again later");
        } catch (UnresolvedAddressException e) {
            OUTPUT_MANAGER.println("You entered incorrect Inet address");
        }
    }

    private static void initCommandList() {
        LIST_OF_COMMANDS.add("add");
        LIST_OF_COMMANDS.add("add_if_max");
        LIST_OF_COMMANDS.add("clear");
        LIST_OF_COMMANDS.add("count_greater_than_type");
        LIST_OF_COMMANDS.add("filter_less_than_employees_count");
        LIST_OF_COMMANDS.add("filter_starts_with_name");
        LIST_OF_COMMANDS.add("help");
        LIST_OF_COMMANDS.add("history");
        LIST_OF_COMMANDS.add("info");
        LIST_OF_COMMANDS.add("remove_by_id");
        LIST_OF_COMMANDS.add("remove_lower");
        LIST_OF_COMMANDS.add("show");
        LIST_OF_COMMANDS.add("update_id");
        LIST_OF_COMMANDS.add("execute_script");
        LIST_OF_COMMANDS.add("register");
    }


    private static void initMainInfoForConnection() throws IOException {
        serverPort = ask(
                value -> (value >= MIN_PORT && value <= MAX_PORT),
                "Enter server port",
                "Server port must be an int number",
                "Sever port must be between 1024 and 65535",
                Integer::parseInt
        );

        serverIp = ask("Enter server IP");

        clientPort = ask(
                value -> (value >= MIN_PORT && value <= MAX_PORT),
                "Enter client port",
                "Client port must be an int number",
                "Client port must be between 1024 and 65535",
                Integer::parseInt
        );

        clientIp = ask("Enter client IP");
    }

    private static <T> T ask(Predicate<T> predicate,
                             String askMessage,
                             String errorMessage,
                             String wrongValueMessage,
                             Function<String, T> converter
    ) throws IOException {
        OUTPUT_MANAGER.println(askMessage);
        String input;
        T value;
        do {
            try {
                input = BUFFERED_READER.readLine();
                value = converter.apply(input);
            } catch (IllegalArgumentException e) {
                OUTPUT_MANAGER.println(errorMessage);
                continue;
            }
            if (predicate.test(value)) {
                return value;
            } else {
                OUTPUT_MANAGER.println(wrongValueMessage);
            }
        } while (true);
    }

    private static String ask(
            String askMessage
    ) throws IOException {
        OUTPUT_MANAGER.println(askMessage);
        String input;
        input = BUFFERED_READER.readLine();
        return input;
    }

}
