package com.schoolmanagement.controller;

import com.schoolmanagement.util.InputHelper;

import java.util.Scanner;

public class AdminController {

    // ==================== CONTROLLER CONNECTIONS ====================

    private final InputHelper inputHelper; // Safely reads Admin menu choices.
    private final StudentController studentController; // Provides Student CRUD operations.
    private final TeacherController teacherController; // Provides Teacher CRUD operations.
    private final ParentController parentController; // Provides Parent CRUD operations.

    // ==================== CONSTRUCTOR ====================

    public AdminController(
            Scanner scanner,
            StudentController studentController,
            TeacherController teacherController,
            ParentController parentController) {

        this.inputHelper = new InputHelper(scanner); // Uses the Scanner created in Main.
        this.studentController = studentController; // Reuses the existing StudentController.
        this.teacherController = teacherController; // Reuses the existing TeacherController.
        this.parentController = parentController; // Reuses the existing ParentController.
    }

    // ==================== ADMIN MENU ====================

    public void showMenu() {
        int choice;

        do {
            printMenu();
            choice = readMenuChoice();

            switch (choice) {

                // ==================== ADD OPERATIONS ====================

                case 1:
                    studentController.addStudent(); // Adds a Student through StudentController.
                    break;

                case 2:
                    teacherController.addTeacher(); // Adds a Teacher through TeacherController.
                    break;

                case 3:
                    parentController.addParent(); // Adds a Parent through ParentController.
                    break;

                // ==================== SHOW OPERATIONS ====================

                case 4:
                    studentController.showStudents(); // Shows all Students.
                    break;

                case 5:
                    teacherController.showTeachers(); // Shows all Teachers.
                    break;

                case 6:
                    parentController.showParents(); // Shows all Parents.
                    break;

                // ==================== SEARCH OPERATIONS ====================

                case 7:
                    studentController.searchStudent(); // Searches for a Student.
                    break;

                case 8:
                    teacherController.searchTeacher(); // Searches for a Teacher.
                    break;

                case 9:
                    parentController.searchParent(); // Searches for a Parent.
                    break;

                // ==================== UPDATE OPERATIONS ====================

                case 10:
                    studentController.updateStudent(); // Updates a Student.
                    break;

                case 11:
                    teacherController.updateTeacher(); // Updates a Teacher.
                    break;

                case 12:
                    parentController.updateParent(); // Updates a Parent.
                    break;

                // ==================== DELETE OPERATIONS ====================

                case 13:
                    studentController.deleteStudent(); // Deletes a Student.
                    break;

                case 14:
                    teacherController.deleteTeacher(); // Deletes a Teacher.
                    break;

                case 15:
                    parentController.deleteParent(); // Deletes a Parent.
                    break;

                // ==================== BACK ====================

                case 16:
                    System.out.println("Returning to Main Menu.");
                    break;
            }

            if (choice != 16) {
                inputHelper.readText("Press Enter to return to the Admin Menu.");
            }

        } while (choice != 16);
    }

    // ==================== PRINT ADMIN MENU ====================

    private void printMenu() {
        System.out.println("~~~~~ Admin Management System ~~~~~");

        System.out.println("1. Add Student");
        System.out.println("2. Add Teacher");
        System.out.println("3. Add Parent");

        System.out.println("4. Show Students");
        System.out.println("5. Show Teachers");
        System.out.println("6. Show Parents");

        System.out.println("7. Search Student");
        System.out.println("8. Search Teacher");
        System.out.println("9. Search Parent");

        System.out.println("10. Update Student");
        System.out.println("11. Update Teacher");
        System.out.println("12. Update Parent");

        System.out.println("13. Delete Student");
        System.out.println("14. Delete Teacher");
        System.out.println("15. Delete Parent");

        System.out.println("16. Back");
    }

    // ==================== READ ADMIN MENU CHOICE ====================

    private int readMenuChoice() {
        return inputHelper.readIntegerInRange(
                "Select an option:", 1, 16
        ); // Keeps asking until the Admin enters a valid option.
    }
}