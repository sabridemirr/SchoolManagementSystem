package com.schoolmanagement;

import com.schoolmanagement.controller.*;   // Imports the controller classes responsible for the application menus.

// Imports repositories that communicate directly with the database.
import com.schoolmanagement.repository.ParentRepository;
import com.schoolmanagement.repository.StudentRepository;
import com.schoolmanagement.repository.TeacherRepository;

// Imports services containing validation and business rules.
import com.schoolmanagement.service.ParentService;
import com.schoolmanagement.service.StudentService;
import com.schoolmanagement.service.TeacherService;

// Prepares the required database tables when the program starts.
import com.schoolmanagement.database.DatabaseInitializer;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {       // The JVM starts running the application from this method.

        DatabaseInitializer.initialize();  // Creates missing database tables and applies required table updates. & // This must run before repositories attempt to access the database.

        Scanner scanner = new Scanner(System.in);     // One shared Scanner is used by all controllers for console input.
        // Sharing one Scanner prevents different classes from competing for System.in.

        // ==================== STUDENT ARCHITECTURE ====================
        StudentRepository studentRepository = new StudentRepository(); // Stores student data.
        StudentService studentService = new StudentService(studentRepository); // Handles student rules.
        StudentController studentController = new StudentController(scanner, studentService); // Controls the student menu.


        // ==================== TEACHER ARCHITECTURE ====================
        TeacherRepository teacherRepository = new TeacherRepository();
        TeacherService teacherService = new TeacherService(teacherRepository);
        TeacherController teacherController = new TeacherController(scanner, teacherService);


        // ==================== PARENT ARCHITECTURE ====================
        ParentRepository parentRepository = new ParentRepository();           // Repository performs parent-related SQL and database operations.
        ParentService parentService = new ParentService(parentRepository, studentService);    // ParentService needs ParentRepository to manage parents. & // It also needs StudentService to confirm that a parent's student exists.
        ParentController parentController = new ParentController(scanner, parentService);    // Controller manages the parent menu and communicates with the service.

        // ==================== ADMIN ARCHITECTURE ====================
        AdminController adminController = new AdminController(scanner, studentController, teacherController, parentController);
        // AdminController receives the other controllers because an administrator
        // is allowed to open and use student, teacher, and parent operations.


        // ==================== MAIN MENU ====================
        int choice;            // Stores the user's selected main-menu option.

        do {     // Repeats the main menu until the user selects option 5.
            System.out.println("~~~~~Welcome To The School Management System~~~~~");
            System.out.println("Please Select an Option:");
            System.out.println("1.Admin Menu:");
            System.out.println("2.Student Menu:");
            System.out.println("3.Teacher Menu:");
            System.out.println("4.Parent Menu");
            System.out.println("5.Exit");

            try {
                choice = scanner.nextInt();         // Attempts to read the user's numerical menu selection.
                scanner.nextLine();                // Removes the Enter character left behind by nextInt().
            } catch (Exception e) {               // Runs when the user enters invalid input such as letters.
                System.out.println("Invalid Choice. Please Enter a Number from 1 to 5");  // catches the error and deals with it.
                scanner.nextLine();             // Removes the invalid input so the Scanner can be used again.
                choice = 0;                    // Zero causes the switch's default case to run.
            }

            // ==================== MAIN MENU CHOICES ====================
            switch (choice) {               // Opens the controller that matches the selected option.

                case 1:
                    adminController.showMenu(); // Opens the new object-based Admin menu.
                    break;

                case 2:
                    studentController.showMenu();
                    break;

                case 3:
                    teacherController.showMenu();
                    break;

                case 4:
                    parentController.showMenu();
                    break;

                case 5:
                    System.out.println("You Have Exited The System.");
                    break;

                default:
                    System.out.println("Invalid Choice.");
            }

        } while (choice != 5);
        scanner.close();            // Releases the console input resource after the program finishes.
    }
}