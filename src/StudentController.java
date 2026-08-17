import java.util.Scanner;
import java.util.ArrayList;

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
                    addStudent();
                    break;

                case 2:
                    showStudents();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    deleteStudent();
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


    //==============================\\
//==========ADD STUDENT==========\\
//===============================\\
    private void addStudent() {
        System.out.println("Add Student:");

        String name = readValidName("Enter a Student Name: ");
        int age = readInteger("Enter Student's Age: ");
        int studentId = readInteger("Enter a Student ID: ");
        double grade = readDouble("Enter Student's Grade: ");

        try {
            Student student = studentService.addStudent(name, age, studentId, grade);  // The service validates and then stores the student.
            System.out.println("Student Successfully Added: ");
            student.displayStudent();  // to display the student returned by the service
        } catch (IllegalArgumentException e) {
            System.out.println("Student Couldn't Be Added: " + e.getMessage());  // displays the services valid message.
        }
    }

    private String readText(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

    // ==================== READ VALID NAME ====================
    private String readValidName(String message) {
        while (true) { // Keeps repeating until the name is valid.
            String name = readText(message).trim(); // Reads the name and removes extra surrounding spaces.

            if (name.isEmpty()) {
                System.out.println("Student name cannot be empty.");

            } else if (!name.matches("[a-zA-Z ]+")) {
                System.out.println("Student name must contain letters only.");

            } else {
                return name; // Ends the loop and returns the valid name.
            }
        }
    }


    private int readInteger(String messsage) {
        int value = 0;
        boolean validInput;

        do {
            validInput = true;
            System.out.println(messsage);

            try {
                value = scanner.nextInt();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Please Enter a Whole Number Only: ");
                scanner.nextLine();
                validInput = false;
            }
        } while (!validInput);
        return value;
    }


// ==================== READ VALID STUDENT ID ====================
    private int readValidStudentId(String message) {
        while (true) { // Keeps asking until the user enters a positive ID.
            int studentId = readInteger(message);

            if (studentId <= 0) {
                System.out.println("Student ID must be greater than 0.");
            } else {
                return studentId; // Returns only a valid positive ID.
            }
        }
    }


    private double readDouble(String message) {
        double value = 0;
        boolean validInput;

        do {
            validInput = true;
            System.out.println(message);

            try {
                value = scanner.nextDouble();
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Please Enter a Valid Grade: ");
                scanner.nextLine();
                validInput = false;
            }
        } while (!validInput);
        return value;
    }


    //=================================\\
//==========SHOW STUDENT============\\
//===================================\\
    private void showStudents() {
        ArrayList<Student> students = studentService.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("There are no Students to display.");
            return;
        }

        System.out.println("Students List.");
        for (Student student : students) {
            student.displayStudent();
        }
    }


    //=================================\\
//==========SEARCH STUDENT==========\\
//===================================\\
    private void searchStudent() {
        System.out.println("Search Student.");
        System.out.println("1.Search Student by ID");
        System.out.println("2.Search Student by Name:");
        System.out.println("3.Back.");

        int searchChoice = readInteger("Select an Option:"); // reads the search menu choice the user inputs.

        try {
            Student student; // this will be storing the result returned by the service.

            if (searchChoice == 1) {
                int studentId = readInteger("Enter a Student Id: ");
                student = studentService.getStudentById(studentId); // this is to search using student id.
            } else if (searchChoice == 2) {
                String name = readValidName("Enter a Student Name: ");
                student = studentService.getStudentByName(name);
            } else if (searchChoice == 3) {
                return;
            } else {
                System.out.println("Please Choose an Option from Above.");
                return;
            }
            if (student == null) {
                System.out.println("Student Was Not Found!.");
                return;
            }
            System.out.println("Student Was Found.");
            student.displayStudent();
        } catch (IllegalArgumentException e) {     // displays the validation errors thrown by Student Service.
            System.out.println("Search has failed: " + e.getMessage());
        }
    }

//=================================\\
//==========DELETE STUDENT==========\\
//===================================\\
    private void deleteStudent() {
        System.out.println("Delete Student.");

        int studentId = readValidStudentId("Enter the Student ID you want to delete.");  // reads the ID of the student that the user wants to delete.

        try {
            Student student = studentService.getStudentById(studentId);

            if (student == null) {
                System.out.println("Student Was Not Found:");  // the repo returns null if the id does not exist.
                return;
            }

            System.out.println("Student Found:");    // Shows the student before asking for confirmation.
            student.displayStudent();

            System.out.println("Are you sure you want to delete this student?");
            System.out.println("1. Yes");
            System.out.println("2. No");

            int confirm;

            do {            // Repeats until the user enters 1 or 2.
                confirm = readInteger("Select an option:");

                if (confirm != 1 && confirm != 2) {
                    System.out.println("Please enter 1 for Yes or 2 for No.");
                }
            } while (confirm != 1 && confirm != 2);

            if (confirm == 2) {    // Cancels the delete operation without deleting anything.
                System.out.println("Delete Process Cancelled.");
                return;
            }

            boolean deleted = studentService.deleteStudentById(studentId); // asks the service to delete the student with this ID.

            if (deleted) {
                System.out.println("Student deleted successfully.");
            } else {
                System.out.println("Student could not be deleted.");
            }

        } catch (IllegalArgumentException e) {
            // Displays validation errors thrown by StudentService.
            System.out.println("Delete failed: " + e.getMessage());
        }
    }
}



