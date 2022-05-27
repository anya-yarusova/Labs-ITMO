package com.anyarusova.client.utility;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.Stack;

/**
 * This class is used for all the user input: keyboard and script execution
 */
public class InputManager {

    private final Scanner scanner;
    private final Stack<BufferedReader> currentFilesReaders = new Stack<>();
    private final Stack<File> currentFiles = new Stack<>();

    public InputManager(InputStream inputStream) {
        this.scanner = new Scanner(inputStream);
    }

    public String nextLine() throws IOException {
        if (!currentFilesReaders.isEmpty()) {
            String input = currentFilesReaders.peek().readLine();
            if (input == null) {
                currentFiles.pop();
                currentFilesReaders.pop().close();
                return nextLine();
            } else {
                return input;
            }
        } else {
            return scanner.nextLine();
        }
    }

    public void connectToFile(File file) throws FileNotFoundException {
        if (currentFiles.contains(file)) {
            throw new UnsupportedOperationException("The file was not executed due to recursion");
        } else {
            if (!file.exists()) {
                throw new FileNotFoundException("The file doesn't exist");
            }
            FileReader fileReader = new FileReader(file);
            BufferedReader newReader = new BufferedReader(fileReader);
            currentFiles.push(file);
            currentFilesReaders.push(newReader);
        }
    }

    public void close() {
        this.scanner.close();
    }
}
