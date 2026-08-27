/*
 * Controls all student-related console interactions.
 *
 * Responsibilities:
 * - Displays the student menu.
 * - Reads and validates the format of user input through InputHelper.
 * - Calls StudentService to perform operations.
 * - Displays successful results and error messages.
 *
 * This controller does not contain SQL and does not directly access the
 * database. Business rules are delegated to StudentService.
 */

package com.schoolmanagement.controller;

import com.schoolmanagement.model.Student;
import com.schoolmanagement.service.StudentService;
import com.schoolmanagement.util.InputHelper;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentController {
    private final StudentService studentService;   // Gives controller access to student operations and rules.
    private final InputHelper inputHelper;   // Provides reusable methods for safely reading console input.

    /*
     * Receives the shared Scanner and StudentService from Main.
     * InputHelper wraps the Scanner so input-reading code is not repeated.
     */

    public StudentController(Scanner scanner, StudentService studentService) {
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
                    updateSubjectGrade(); // Opens the subject-grade update operation.
                    break;

                case 7:
                    deleteSubject(); // Opens the subject deletion operation.
                    break;

                case 8:
                    System.out.println("Returning to Main Menu.");
                    break;
            }
        } while (choice != 8);
    }

    private void printMenu() {
        System.out.println("~~~~~ Student Management System ~~~~~");
        System.out.println("1. Add Student");
        System.out.println("2. Show Students");
        System.out.println("3. Search Student");
        System.out.println("4. Delete Student");
        System.out.println("5. Update Student");
        System.out.println("6. Update Subject Grade");
        System.out.println("7. Delete Subject");
        System.out.println("8. Back");
    }

    private int readMenuChoice() {
        return inputHelper.readIntegerInRange("Choose a Valid Option", 1, 8);
    }

    // ==================== ADD STUDENT ====================
    void addStudent() {
        System.out.println("Add Student:");

        String name = inputHelper.readName("Enter a Student Name: ");
        int age = inputHelper.readIntegerInRange("Enter Student's Age: ", 4, 61);
        int studentId = readUniqueStudentId("Enter a Student ID: ");

        try {
            Student student = studentService.addStudent(name, age, studentId);             // First creates the basic student without a general grade.

            int subjectCount = inputHelper.readPositiveInteger("How many subjects does this student take? ");   // The number is not limited to two; any positive number is accepted.

            for (int i = 1; i <= subjectCount; i++) {               // Repeats once for every subject selected by the user.
                boolean subjectAdded = false;

                while (!subjectAdded) {                 // Repeats the current subject when a duplicate is entered.
                    String subjectName = inputHelper.readName("Enter subject " + i + " name: ");

                    double subjectGrade = inputHelper.readDoubleInRange("Enter the grade for " + subjectName + ": ", 0, 100);

                    try {
                        studentService.addSubjectToStudent(studentId, subjectName, subjectGrade);   // Stores the subject name and its respective grade in H2.
                        System.out.println(subjectName + " added successfully.");
                        subjectAdded = true;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Subject could not be added: " + e.getMessage()
                        );
                    }
                }
            }

            System.out.println("Student Successfully Added:");
            displayStudentWithSubjects(student);

        } catch (IllegalArgumentException e) {
            System.out.println("Student Couldn't Be Added: " + e.getMessage()
            );
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


    // ==================== DISPLAY STUDENT WITH SUBJECTS ====================
    private void displayStudentWithSubjects(Student student) {

        student.displayStudent();   // Displays the student's basic information.
        String subjectDetails = studentService.getStudentSubjects(student.getStudentId());  // Retrieves subjects and grades directly from the database.

        if (subjectDetails.isBlank()) {
            System.out.println("Subjects: No subjects assigned.");
        } else {
            System.out.println("Subjects and Grades:");
            System.out.print(subjectDetails);
        }
        System.out.println(); // Separates students when multiple records are displayed.
    }



    // ==================== SHOW STUDENT ====================
    void showStudents() {
        ArrayList<Student> students = studentService.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("There are no Students to display.");
            return;
        }

        System.out.println("Students List.");
        for (Student student : students) {
            displayStudentWithSubjects(student);
        }
    }


    // ==================== SEARCH STUDENT ====================
    void searchStudent() {
        System.out.println("Search Student.");
        System.out.println("1.Search Student by ID");
        System.out.println("2.Search Student by Name:");
        System.out.println("3.Back.");

        int searchChoice = inputHelper.readIntegerInRange("Select an Option:", 1, 3); // reads the search menu choice the user inputs.

        try {
            Student student; // this will be storing the result returned by the service.

            if (searchChoice == 1) {
                int studentId = inputHelper.readPositiveInteger("Enter a Student Id: ");
                student = studentService.getStudentById(studentId); // this is to search using student id.
            } else if (searchChoice == 2) {
                String name = inputHelper.readName("Enter a Student Name: ");
                student = studentService.getStudentByName(name);
            } else if (searchChoice == 3) {
                return;
            } else {
                return;
            }
            if (student == null) {
                System.out.println("Student Was Not Found!.");
                return;
            }
            System.out.println("Student Was Found.");
            displayStudentWithSubjects(student);
        } catch (IllegalArgumentException e) {     // displays the validation errors thrown by Student Service.
            System.out.println("Search has failed: " + e.getMessage());
        }
    }


    // ==================== DELETE STUDENT ====================
    void deleteStudent() {
        System.out.println("Delete Student.");

        int studentId = inputHelper.readPositiveInteger("Enter the Student ID you want to delete.");  // reads the ID of the student that the user wants to delete.

        try {
            Student student = studentService.getStudentById(studentId);

            if (student == null) {
                System.out.println("Student Was Not Found:");  // the repo returns null if the id does not exist.
                return;
            }

            System.out.println("Student Found:");    // Shows the student before asking for confirmation.
            displayStudentWithSubjects(student);

            System.out.println("Are you sure you want to delete this student?");
            System.out.println("1. Yes");
            System.out.println("2. No");

            int confirm = inputHelper.readIntegerInRange("Select an option: ", 1, 2);
            if (confirm == 2) {
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
    void updateStudent() {
        System.out.println("Update Student:");

        int studentId = inputHelper.readPositiveInteger("Enter the ID of the student you want to update:");

        try {
            Student student = studentService.getStudentById(studentId);

            if (student == null) {
                System.out.println("Student was not found!");
                return;
            }

            System.out.println("Current Student Information:");
            displayStudentWithSubjects(student);

            System.out.println("Choose what you want to update.");
            System.out.println("1. Name");
            System.out.println("2. Age");
            System.out.println("3. Name and Age");
            System.out.println("4. Cancel");

            int updateChoice = inputHelper.readIntegerInRange("Select an Option: ", 1, 4);

            if (updateChoice == 4) {
                System.out.println("Update Process has been Cancelled.");
                return;
            }

            // Starts with the student's current information.
            String newName = student.getName();
            int newAge = student.getAge();

            switch (updateChoice) {
                case 1:
                    newName = inputHelper.readName("Enter Student's New Name: ");
                    break;

                case 2:
                    newAge = inputHelper.readIntegerInRange("Enter Student's New Age: ", 4, 61);
                    break;

                case 3:
                    newName = inputHelper.readName("Enter Student's New Name: ");
                    newAge = inputHelper.readIntegerInRange("Enter Student's New Age: ", 4, 61);
                    break;
            }

            Student updatedStudent = studentService.updateStudent(studentId, newName, newAge);

            if (updatedStudent == null) {
                System.out.println("Student could not be updated.");
                return;
            }

            System.out.println("Student updated successfully:");
            displayStudentWithSubjects(updatedStudent);

        } catch (IllegalArgumentException e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }


    // ==================== UPDATE SUBJECT GRADE ====================
    private void updateSubjectGrade() {
        System.out.println("Update Subject Grade:");

        // Reads the student ID and accepts only a positive whole number.
        int studentId = inputHelper.readPositiveInteger("Enter the student's ID: ");

        try {
            // Finds the student before asking for the subject information.
            Student student = studentService.getStudentById(studentId);

            if (student == null) {
                System.out.println("Student was not found.");
                return;
            }

            // Displays the student and their existing subjects.
            System.out.println("Current Student Information:");
            displayStudentWithSubjects(student);

            // Reads the subject whose grade should be changed.
            String subjectName = inputHelper.readName("Enter the subject name you want to update: ");

            // Reads the new grade and accepts only values from 0 to 100.
            double newGrade = inputHelper.readDoubleInRange("Enter the new grade: ", 0, 100);

            // Service returns false if this student does not take that subject.
            boolean updated = studentService.updateSubjectGrade(studentId, subjectName, newGrade);

            if (!updated) {
                System.out.println("This student does not take the entered subject.");
                return;
            }
            System.out.println("Subject grade updated successfully:");

            // Displays the student again so the user can see the new grade.
            displayStudentWithSubjects(student);

        } catch (IllegalArgumentException e) {
            System.out.println("Subject grade could not be updated: " + e.getMessage());
        }
    }

    // ==================== DELETE SUBJECT ====================
    private void deleteSubject() {
        System.out.println("Delete Subject:");

        // Reads a positive student ID.
        int studentId = inputHelper.readPositiveInteger("Enter the student's ID: ");

        try {
            // Searches for the student before asking for a subject.
            Student student = studentService.getStudentById(studentId);

            if (student == null) {
                System.out.println("Student was not found.");
                return;
            }

            // Shows the student and their subjects before deletion.
            System.out.println("Current Student Information:");
            displayStudentWithSubjects(student);

            // Reads the subject that the user wants to remove.
            String subjectName = inputHelper.readName("Enter the subject name you want to delete: ");

            // Prevents accidental deletion.
            System.out.println("Are you sure you want to delete " + subjectName + "?");
            System.out.println("1. Yes");
            System.out.println("2. No");

            int confirmation = inputHelper.readIntegerInRange("Select an option: ", 1, 2);

            if (confirmation == 2) {
                System.out.println("Subject deletion cancelled.");
                return;
            }

            // Returns false if the student does not take the entered subject.
            boolean deleted = studentService.deleteSubjectFromStudent(studentId, subjectName);

            if (!deleted) {
                System.out.println("This student does not take the entered subject.");
                return;
            }

            System.out.println("Subject deleted successfully:");

            // Shows the remaining subjects after deletion.
            displayStudentWithSubjects(student);

        } catch (IllegalArgumentException e) {
            System.out.println("Subject could not be deleted: " + e.getMessage());
        }
    }
}
