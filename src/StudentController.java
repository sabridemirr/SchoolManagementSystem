import java.security.PrivateKey;
import java.util.Scanner;

public class StudentController {

    private final Scanner scanner;   // reads the input from the console.
    private final StudentService studentService;   // Gives controller access to student operations and rules.

    public StudentController(Scanner scanner, StudentService studentService) {
        this.scanner = scanner;  // stores the scanner received from Main.
        this.studentService = studentService; // stores the service received from main.
    }


    public void showMenu() {

        int choice;

        do {
            printMenu();
            choice = readMenuChoice();

            switch (choice) {

                case 1:
                    System.out.println("Add Student will be connected next.");
                    break;

                case 2:
                    System.out.println("Show Students will be connected later.");
                    break;

                case 3:
                    System.out.println("Search Student will be connected later.");
                    break;

                case 4:
                    System.out.println("Delete Student will be connected later.");
                    break;

                case 5:
                    System.out.println("Update Student will be connected later.");
                    break;

                case 6:
                    System.out.println("Returning to Main Menu.");
                    break;

            }
        } while (choice != 6);
    }

    private void printMenu() {
        System.out.println("~~~~~ Student Management System ~~~~~");
        System.out.println("1. Add Student");
        System.out.println("2. Show Students");
        System.out.println("3. Search Student");
        System.out.println("4. Delete Student");
        System.out.println("5. Update Student");
        System.out.println("6. Back");
    }

    private int readMenuChoice() {
        int choice = 0; // Temporary starting value.
        boolean validChoice; // Controls whether the input loop repeats.

        do {
            validChoice = true; // Assume the next input is valid.
            System.out.println("Select an option:");

            try {
                choice = scanner.nextInt(); // Attempts to read a whole number.
                scanner.nextLine(); // Clears the Enter left behind by nextInt().

                if (choice < 1 || choice > 6) { // Menu accepts only numbers from 1 to 6.
                    System.out.println("Please select an option from 1 to 6.");
                    validChoice = false;
                }

            } catch (Exception e) { // Runs if the user enters something like "abc".
                System.out.println("Menu choice must be a number.");
                scanner.nextLine(); // Removes the invalid input.
                validChoice = false;
            }

        } while (!validChoice); // Repeats until a valid menu option is entered.

        return choice; // Sends the accepted choice back to showMenu().
    }
}

