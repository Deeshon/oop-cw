package org.example.utils;

import java.util.List;
import java.util.Scanner;

public class InputValidator {

    /**
     * Continuously prompts the user to enter a positive integer.
     *
     * @param scanner       The Scanner object to read user input.
     * @param parameterName The name of the parameter being validated (for error messages).
     * @return A valid positive integer input.
     */
    public static int isPositive(Scanner scanner, String parameterName) {
        int value;
        while (true) {
            try {
                value = Integer.parseInt(scanner.nextLine());
                if (value > 0) {
                    return value;
                } else {
                    System.out.println(parameterName + " must be a positive integer. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for " + parameterName + ".");
            }
        }
    }

    /**
     * Prompts the user and validates if the provided number is within the allowed range.
     *
     * @param scanner The Scanner object for user input.
     * @param range The minimum allowable value.
     * @param parameterName The name of the parameter being validated (for error messages).
     * @return A valid integer within the specified range.
     */
    public static int isWithinRange(Scanner scanner, List<Integer> range, String parameterName) {
        int value;
        while (true) {
            try {
                value = scanner.nextInt(); // Read input
                if (!range.contains(value)) {
                    throw new IllegalArgumentException(parameterName + " must be between in range (" + range.toString() + ").");
                }
                return value; // Valid input, exit loop
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage()); // Display range error
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a valid integer.");
                scanner.next(); // Clear invalid input from scanner
            }
        }
    }

    /**
     * Validates if the provided string is not null or empty.
     *
     * @param scanner TThe Scanner object to read user input..
     * @param parameterName The name of the parameter being validated (for error messages).
     * @return a valid string.
     * @throws IllegalArgumentException if the string is null or empty.
     */
    public static String isNotEmpty(Scanner scanner, String parameterName) {
        while (true) {
            String value = scanner.nextLine().trim();
            if (value.isEmpty()) {
                System.out.println(parameterName + " cannot be empty. Please try again.");
            } else {
                return value; // Valid input
            }
        }
    }

}
