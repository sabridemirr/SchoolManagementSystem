package com.schoolmanagement.controller;

import com.schoolmanagement.model.Student;
import com.schoolmanagement.service.StudentService;
import com.schoolmanagement.util.InputHelper;

import java.util.ArrayList;
import java.util.Scanner;

public class StudentController {

    private final StudentService studentService;   // Gives controller access to student operations and rules.
    private final InputHelper inputHelper;

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
        return inputHelper.readIntegerInRange("Choose a Valid Option", 1, 6);
    }


    //==============================\\
//==========ADD STUDENT==========\\
//===============================\\
    private void addStudent() {
        System.out.println("Add Student:");

        String name = inputHelper.readName("Enter a Student Name: ");
        int age = inputHelper.readIntegerInRange("Enter Student's Age: ", 4, 61);
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

        int studentId = inputHelper.readPositiveInteger("Enter the Student ID you want to delete.");  // reads the ID of the student that the user wants to delete.

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

//=================================\\
//==========UPDATE STUDENT==========\\
//===================================\\

    private void updateStudent() {
        System.out.println("Update Student:");

        // Reads the ID and accepts only a positive number.
        int studentId = inputHelper.readPositiveInteger("Enter the ID of the student you want to update:");

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

            // Accepts only update-menu options from 1 to 5.
            int updateChoice = inputHelper.readIntegerInRange("Select an Option: ", 1, 5);

            if (updateChoice == 5) {
                System.out.println("Update Process has been Cancelled.");
                return;
            }

            // Starts with the students current info
            String newName = student.getName();
            int newAge = student.getAge();
            double newGrade = student.getGrade();

            //only changes the values selected by the user.
            switch (updateChoice) {

                case 1:
                    newName = inputHelper.readName("Enter Student's New Name: ");
                    break;

                case 2:
                    newAge = inputHelper.readIntegerInRange("Enter Student's New Age: ", 4, 61);
                    break;

                case 3:
                    newGrade = inputHelper.readDoubleInRange("Enter Student's New Grade: ", 0, 100);
                    break;

                case 4:
                    newName = inputHelper.readName("Enter Student's New Name: ");
                    newAge = inputHelper.readIntegerInRange("Enter Student's New Age: ", 4, 61);
                    newGrade = inputHelper.readDoubleInRange("Enter Student's New Grade: ", 0, 100);
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
