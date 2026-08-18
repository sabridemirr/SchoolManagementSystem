package com.schoolmanagement.controller;

import com.schoolmanagement.model.Student;
import com.schoolmanagement.service.StudentService;
import com.schoolmanagement.util.InputHelper;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentController {

    private final Scanner scanner;   // reads the input from the console.
    private final StudentService studentService;   // Gives controller access to student operations and rules.
    private final InputHelper inputHelper;

    public StudentController(Scanner scanner, StudentService studentService) {
        this.scanner = scanner;  // stores the scanner received from Main.
        this.studentService = studentService; // stores the service received from main.
        this.inputHelper = new InputHelper(scanner);
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
                    updateStudent();
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

        String name = inputHelper.readName("Enter a Student Name: ");
        int age = inputHelper.readIntegerInRange("Enter Student's Age: ", 4,61);
        int studentId = readUniqueStudentId("Enter a Student ID: ");
        double grade = inputHelper.readDoubleInRange("Enter Student's Grade: ", 0, 100);

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

    // ==================== READ UNIQUE STUDENT ID ====================

    private int readUniqueStudentId(String message) {
        while (true) {

            // First ensures that the ID is a positive whole number.
            int studentId = inputHelper.readPositiveInteger(message);

            // Searches for an existing student with the entered ID.
            Student existingStudent = studentService.getStudentById(studentId);

            if (existingStudent != null) {
                System.out.println("Student ID already exists. Please enter another ID.");
            } else {
                return studentId; // Returns only an unused ID.
            }
        }
    }


    // ==================== READ VALID AGE ====================

    private int readValidAge(String message) {
        while (true) {
            int age = readInteger(message); // Ensures the input is a whole number.

            if (age < 4 || age > 61) {
                System.out.println("Student age must be between 4 and 61.");
            } else {
                return age; // Returns only an age inside the accepted range.
            }
        }
    }


// ==================== READ VALID GRADE ====================

    private double readValidGrade(String message) {
        while (true) {
            double grade = readDouble(message); // Ensures the input is numeric.

            if (grade < 0 || grade > 100) {
                System.out.println("Student grade must be between 0 and 100.");
            } else {
                return grade; // Returns only a grade inside the accepted range.
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

        int searchChoice = inputHelper.readIntegerInRange("Select an Option:", 1, 3); // reads the search menu choice the user inputs.

        try {
            Student student; // this will be storing the result returned by the service.

            if (searchChoice == 1) {
                int studentId =  inputHelper.readPositiveInteger("Enter a Student Id: ");
                student = studentService.getStudentById(studentId); // this is to search using student id.
            } else if (searchChoice == 2) {
                String name = inputHelper.readName("Enter a Student Name: ");
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
            System.out.println("Delete has failed: " + e.getMessage());
        }
    }

// ==================== UPDATE STUDENT ====================

    private void updateStudent() {
        System.out.println("Update Student:");

        // Reads the ID and accepts only a positive number.
        int studentId = readValidStudentId("Enter the ID of the student you want to update:");

        try {  // Searches for the student before requesting the new information.
            Student student = studentService.getStudentById(studentId);

            if (student == null) {
                System.out.println("Student was not found!");
                return;
            }

            System.out.println("Current Student Information:");   // To display the current info of the existing students.
            student.displayStudent();

            System.out.println("Choose What do you want to update.");   //to display the update submenu.
            System.out.println("1.Name.");
            System.out.println("2.Age.");
            System.out.println("3.Grade.");
            System.out.println("4.All the Information.");
            System.out.println("5.Cancel.");
            int updateChoice;

            // Accepts only update-menu options from 1 to 5.
            do {
                updateChoice = readInteger("Please Select an Option:");

                if (updateChoice < 1 || updateChoice > 5) {
                    System.out.println("Please Choose an Option from 1 to 5!");
                }
            } while (updateChoice < 1 || updateChoice > 5);

            if (updateChoice == 5) {
                System.out.println("Update has been cancelled.");
                return;
            }

            // Starts with the students current info
            String newName = student.getName();
            int newAge = student.getAge();
            double newGrade = student.getGrade();

            //only changes the values selected by the user.
            switch (updateChoice) {

                case 1:
                    newName = readValidName("Enter Student's New Name: ");
                    break;

                case 2:
                    newAge = readValidAge("Enter Student's New Age: ");
                    break;

                case 3:
                    newGrade = readValidGrade("Enter Student's New Grade: ");
                    break;

                case 4:
                    newName = readValidName("Enter Student's New Name: ");
                    newAge = readValidAge("Enter Student's New Age: ");
                    newGrade = readValidGrade("Enter Student's New Grade: ");
                    break;
            }

            // Sends the new values and unchanged values to the service.
            Student updatedStudent = studentService.updateStudent(studentId, newName, newAge, newGrade);

            if (updatedStudent == null) {
                System.out.println("Student could not be updated.");
                return;
            }

            System.out.println("Student updated successfully:");
            updatedStudent.displayStudent();

        } catch (IllegalArgumentException e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }
}
