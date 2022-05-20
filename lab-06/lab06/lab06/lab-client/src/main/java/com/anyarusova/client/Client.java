package com.anyarusova.client;

import com.anyarusova.client.utility.Console;
import com.anyarusova.client.utility.DatagramSocketManager;
import com.anyarusova.client.utility.InputManager;
import com.anyarusova.client.utility.OutputManager;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;

public final class Client {

    private static final HashSet<String> LIST_OF_COMMANDS = new HashSet<String>();
    private static final int MAX_PORT = 65535;
    private static final int MIN_PORT = 1;
    private static final int DEFAULT_PORT = 1812;

    private Client() {
        throw new UnsupportedOperationException("This is an utility class and can not be instantiated");
    }

    public static void main(String[] args) {
        initCommandList();
        InputManager inputManager = new InputManager(System.in);
        OutputManager outputManager = new OutputManager(System.out);
        InetSocketAddress serverAddress = getServerAddress(args, outputManager);
        if (Objects.isNull(serverAddress)) {
            outputManager.println("Found invalid arguments. Please use the program as \"java -jar <server address>:<server port>\"");
            inputManager.close();
            return;
        }
        try {
            DatagramSocketManager datagramSocketManager = new DatagramSocketManager(serverAddress);
            new Console(outputManager, inputManager, datagramSocketManager, LIST_OF_COMMANDS).start();
        } catch (ClassNotFoundException e) {
            outputManager.println("Found incorrect data from server.");
        } catch (IOException e) {
            outputManager.println("Something went wrong with IO, the message is: " + e.getMessage());
        }
    }

    public static InetSocketAddress getServerAddress(String[] args, OutputManager outputManager) {
        if (args.length < 1) {
            outputManager.println("Set default server address (localhost:1812)");
            return new InetSocketAddress("localhost", DEFAULT_PORT);
        }
        String[] ipAndPort = args[0].split(":");
        if (ipAndPort.length != 2) {
            outputManager.println("Invalid IP address");
            return null;
        }
        int port = DEFAULT_PORT;
        try {
            port = Integer.parseInt(ipAndPort[1]);
            if (port > MAX_PORT || port < MIN_PORT) {
                outputManager.println("Port number out of range");
                return null;
            }
        } catch (NumberFormatException e) {
            outputManager.println("Invalid port value, set default port(1812)");
        }
        InetSocketAddress serverAddress = null;
        if (!NumberUtils.isCreatable(ipAndPort[0])) {
            serverAddress = new InetSocketAddress(ipAndPort[0], port);
        } else {
            String[] bytes = ipAndPort[0].split("\\.");
            final byte ipByteCount = 4;
            if (bytes.length == ipByteCount && Arrays.stream(bytes)
                    .allMatch(x -> x.matches("((\\d{1,2})|((0|1)\\d{2})|(2[0-4]\\d)|(25[0-5]))"))) {
                outputManager.println("Set server address to " + args[0]);
                serverAddress = new InetSocketAddress(ipAndPort[0], port);
            } else {
                outputManager.println("Invalid IP address");
            }
        }
        return serverAddress;
    }

    public static void initCommandList() {
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
    }
}
