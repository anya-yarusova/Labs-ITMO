package com.anyarusova.server.utility;

import com.anyarusova.common.utility.HistoryKeeper;

import java.util.Queue;
import java.util.StringJoiner;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class HistoryManager implements HistoryKeeper {

    private static final int CAPACITY = 8;
    private final Queue<String> history = new ArrayBlockingQueue<>(CAPACITY);
    private final ReadWriteLock lock = new ReentrantReadWriteLock(true);

    public void addNote(String note) {
        Lock writeLock = lock.writeLock();
        try {
            writeLock.lock();
            if (history.size() == CAPACITY) {
                history.remove();
            }
            history.add(note);
        } finally {
            writeLock.unlock();
        }
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
