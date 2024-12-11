package org.example.utils;

import java.security.SecureRandom;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Utils {

    // ANSI color codes
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";

    private static final ConcurrentLinkedQueue<String> logs = new ConcurrentLinkedQueue<>();

    /**
     * Generates a random string containing uppercase letters (A-Z) and digits (0-9).
     * @return A random string of specified length.
     */
    public static String generateId() {
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder stringBuilder = new StringBuilder(5);

        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(characters.length());
            stringBuilder.append(characters.charAt(randomIndex));
        }

        return stringBuilder.toString();
    }

    public static synchronized void log(String action, String color) {
        LocalTime time = LocalTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss:SSS");
        String timestamp = time.format(formatter);
        String threadName = Thread.currentThread().getName();

        String message = "[" + timestamp + "] " + action;

        // save the logs in in-memory storage
        logs.add(message);

        // Print the log to the cli
        System.out.println(color + message);
    }

    // Overloaded method for default color (no specific color given)
    public static synchronized void log(String action) {
        log(action, RESET);
    }

    /**
     * Retrieves all stored logs.
     *
     * @return List of log messages.
     */
    public static synchronized List<String> getLogs() {
        return new ArrayList<>(logs);
    }

    /**
     * Clears the in-memory log storage.
     */
    public static void clearLogs() {
        logs.clear();
    }
}
