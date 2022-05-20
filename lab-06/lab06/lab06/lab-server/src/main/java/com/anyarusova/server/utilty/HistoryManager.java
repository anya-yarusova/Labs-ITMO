package com.anyarusova.server.utilty;

import com.anyarusova.common.utility.HistoryKeeper;

import java.util.Queue;
import java.util.StringJoiner;
import java.util.concurrent.ArrayBlockingQueue;

public class HistoryManager implements HistoryKeeper {

    private static final int CAPACITY = 8;
    private final Queue<String> history = new ArrayBlockingQueue<>(CAPACITY);

    public void addNote(String note) {
        if (history.size() == CAPACITY) {
            history.remove();
        }
        history.add(note);
    }

    public String niceToString() {
        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.add("The last 8 commands were:");
        for (String commandName : history) {
            stringJoiner.add(commandName);
        }
        return stringJoiner.toString();
    }

}
