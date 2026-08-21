package com.schoolmanagement;

import com.schoolmanagement.controller.*;
import com.schoolmanagement.repository.ParentRepository;
import com.schoolmanagement.repository.StudentRepository;
import com.schoolmanagement.repository.TeacherRepository;
import com.schoolmanagement.service.ParentService;
import com.schoolmanagement.service.StudentService;
import com.schoolmanagement.service.TeacherService;
import java.util.Scanner;
import com.schoolmanagement.database.DatabaseInitializer;

public class Main {
    public static void main(String[] args) {

        DatabaseInitializer.initialize();

        Scanner scanner = new Scanner(System.in);

        // ==================== STUDENT ARCHITECTURE ====================
        StudentRepository studentRepository = new StudentRepository(); // Stores student data.
        StudentService studentService = new StudentService(studentRepository); // Handles student rules.
        StudentController studentController = new StudentController(scanner, studentService); // Controls the student menu.


        // ==================== TEACHER ARCHITECTURE ====================
        TeacherRepository teacherRepository = new TeacherRepository();
        TeacherService teacherService = new TeacherService(teacherRepository);
        TeacherController teacherController = new TeacherController(scanner, teacherService);


        // ==================== PARENT ARCHITECTURE ====================
        ParentRepository parentRepository = new ParentRepository();
        ParentService parentService = new ParentService(parentRepository, studentService);
        ParentController parentController = new ParentController(scanner, parentService);

        // ==================== ADMIN ARCHITECTURE ====================
        AdminController adminController = new AdminController(scanner, studentController, teacherController, parentController);

        // ==================== MAIN MENU ====================
        int choice;

        do {
            System.out.println("~~~~~Welcome To The School Management System~~~~~");
            System.out.println("Please Select an Option:");
            System.out.println("1.Admin Menu:");
            System.out.println("2.Student Menu:");
            System.out.println("3.Teacher Menu:");
            System.out.println("4.Parent Menu");
            System.out.println("5.Exit");

            try {
                choice = scanner.nextInt();  // this will work unless you enter a variable
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid Choice. Please Enter a Number from 1 to 5");  // catches the error and deals with it.
                scanner.nextLine();
                choice = 0;
            }

            // ==================== MAIN MENU CHOICES ====================
            switch (choice) {

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
        scanner.close();
    }
}