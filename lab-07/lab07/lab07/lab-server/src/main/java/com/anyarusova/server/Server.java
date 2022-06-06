package com.anyarusova.server;

import com.anyarusova.common.utility.DataManager;
import com.anyarusova.common.utility.State;
import com.anyarusova.server.data.Database;
import com.anyarusova.server.utility.Console;
import com.anyarusova.server.data.DataManagerImpl;
import com.anyarusova.server.utility.ServerRunner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.channels.UnresolvedAddressException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Function;
import java.util.function.Predicate;

public final class Server {
    private static final BufferedReader BUFFERED_READER = new BufferedReader(new InputStreamReader(System.in));
    private static int serverPort;
    private static String serverIp;
    private static final int MAX_PORT = 65535;
    private static final int MIN_PORT = 1024;
    private static String dbHost;
    private static String dbName;
    private static String username;
    private static String password;
    private static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();
    private static final ExecutorService CACHED_THREAD_POOL = Executors.newCachedThreadPool();

    private Server() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        try {
            initMainInfoForConnection();
            Connection connection;
            try {
                connection = DriverManager.getConnection(
                        "jdbc:postgresql://" + dbHost + "/" + dbName,
                        username,
                        password
                );
            } catch (SQLException e) {
                System.out.println("Could not connect to the server. Please check if your login and password were correct.");
                return;
            }
            System.out.println("Successfully made a connection with the database");
            State<Boolean> serverIsWorkingState = new State<>(true);
            Database database = new Database(connection);
            DataManager dataManager = new DataManagerImpl(database);
            dataManager.initialiseData();
            Console console = new Console(serverIsWorkingState);
            ServerRunner serverApp;
            serverApp = new ServerRunner(
                    serverPort,
                    serverIp,
                    CACHED_THREAD_POOL,
                    FORK_JOIN_POOL,
                    dataManager);
            CACHED_THREAD_POOL.submit(console::start);
            serverApp.start(serverIsWorkingState);

        } catch (IOException e) {
            System.out.println("An unexpected IO error occurred. The message is: " + e.getMessage());
        } catch (UnresolvedAddressException e) {
            System.out.println("Could not resolve the address you entered. Please re-start the server with another one");
        } finally {
            CACHED_THREAD_POOL.shutdown();
            FORK_JOIN_POOL.shutdown();
        }
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
        dbHost = ask("Enter database host");

        dbName = ask("Enter database name");

        username = ask("Enter username");
        password = ask("Enter password");
    }

    private static <T> T ask(
            Predicate<T> predicate,
            String askMessage,
            String errorMessage,
            String wrongValueMessage,
            Function<String, T> converter
    ) throws IOException {
        System.out.println(askMessage);
        String input;
        T value;
        do {
            try {
                input = BUFFERED_READER.readLine();
                value = converter.apply(input);
            } catch (IllegalArgumentException e) {
                System.out.println(errorMessage);
                continue;
            }
            if (predicate.test(value)) {
                return value;
            } else {
                System.out.println(wrongValueMessage);
            }
        } while (true);
    }

    private static String ask(
            String askMessage
    ) throws IOException {
        System.out.println(askMessage);
        String input;
        input = BUFFERED_READER.readLine();
        return input;
    }
}
