package com.schoolmanagement.util;

import java.util.Scanner;


public class InputHelper {

    private final Scanner scanner;   //Scanner is used to read console input.

    // ==================== CONSTRUCTOR ====================
    public InputHelper(Scanner scanner) {
        this.scanner = scanner;    // Saves the Scanner received from Main.
    }

    // ==================== READ TEXT ====================
    public String readText(String message) {
        System.out.println(message);  // Displays what the user should enter.
        return scanner.nextLine();   // Controls whether the input loop repeats.
    }

    // ==================== READ WHOLE NUMBER ====================
    public int readInteger(String message) {
        int value;    // Stores the whole number entered by the user.
        boolean validInput;     // Controls whether the input loop repeats.

        do {
            validInput = true;  // Assumes the next input will be valid.
            System.out.println(message);  // Attempts to read a whole number.

            try {
                value = scanner.nextInt();
                scanner.nextLine(); // Removes the Enter left by nextInt().
            } catch (Exception e) {
                System.out.println("Please enter a whole number only.");
                scanner.nextLine();  // Removes invalid input such as "abc".
                value = 0;  // Gives value a temporary value required by Java.
                validInput = false;  // Causes the loop to ask again.
            }
        } while (!validInput);
        return value; // Returns the valid whole number.
    }


    // ==================== READ DECIMAL NUMBER ====================
    public double readDouble(String message) {
        double value;
        boolean validInput;

        do {
            validInput = true;
            System.out.println(message);

            try {
                value = scanner.nextDouble();
                scanner.nextLine(); // Removes the Enter left by nextDouble().
            } catch (Exception e) {
                System.out.println("Please enter a valid number.");
                scanner.nextLine(); // Removes the invalid input.
                value = 0;
                validInput = false;
            }
        } while (!validInput);
        return value;
    }


    // ==================== READ VALID NAME ====================
    public String readName(String message) {
        while (true) {
            String name = readText(message).trim();

            if (name.isEmpty()) {
                System.out.println("Name cannot be empty.");
            } else if (!name.matches("[a-zA-Z ]+")) {
                System.out.println("Name must contain letters only.");
            } else {
                return name;
            }
        }
    }


    // ==================== READ POSITIVE NUMBER ====================
    public int readPositiveInteger(String message) {
        while (true) {
            int value = readInteger(message);

            if (value <= 0) {
                System.out.println("The number must be greater than 0.");
            } else {
                return value;
            }
        }
    }


    // ==================== READ WHOLE NUMBER IN RANGE ====================
    public int readIntegerInRange(String message, int minimum, int maximum) {
        while (true) {
            int value = readInteger(message);

            if (value < minimum || value > maximum) {
                System.out.println("Please enter a number between " + minimum + " and " + maximum + ".");
            } else {
                return value;
            }
        }
    }


// ==================== READ DECIMAL NUMBER IN RANGE ====================

    public double readDoubleInRange(String message, double minimum, double maximum) {
        while (true) {
            double value = readDouble(message);

            if (value < minimum || value > maximum) {
                System.out.println("Please enter a number between " + minimum + " and " + maximum + ".");
            } else {
                return value;
            }
        }
    }
}