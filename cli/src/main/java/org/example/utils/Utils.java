package org.example.utils;

import java.security.SecureRandom;

public class Utils {

    // ANSI color codes
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String CYAN = "\u001B[36m";

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
        String timestamp = java.time.LocalTime.now().toString();
        String threadName = Thread.currentThread().getName();
        // Print the log with color
        System.out.println(color + "[" + timestamp + "] [" + threadName + "] " + action + RESET);
    }

    public static synchronized void log(String action, String color, String user) {
        String timestamp = java.time.LocalTime.now().toString();
        // Print the log with color
        System.out.println(color + "[" + timestamp + "] [" + user + "] " + action + RESET);
    }

    // Overloaded method for default color (no specific color given)
    public static synchronized void log(String action) {
        log(action, RESET);
    }
}
