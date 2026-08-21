package com.schoolmanagement.controller;

import com.schoolmanagement.model.Teacher;
import com.schoolmanagement.service.TeacherService;
import com.schoolmanagement.util.InputHelper;

import java.util.ArrayList;
import java.util.Scanner;

public class TeacherController {

    private final TeacherService teacherService;
    private final InputHelper inputHelper;

    public TeacherController(Scanner scanner, TeacherService teacherService) {
        this.teacherService = teacherService; // stores the service received from main.
        this.inputHelper = new InputHelper(scanner);
    }

    public void showMenu() {

        int choice;

        do {
            printMenu();
            choice = readMenuChoice();

            switch (choice) {

                case 1:
                    addTeacher();
                    break;

                case 2:
                    showTeachers();
                    break;

                case 3:
                    searchTeacher();
                    break;

                case 4:
                    deleteTeacher();
                    break;

                case 5:
                    updateTeacher();
                    break;

                case 6:
                    System.out.println("Returning to Main Menu.");
                    break;
            }
        } while (choice != 6);
    }

    private void printMenu() {
        System.out.println("~~~~~ Teacher Management System ~~~~~");
        System.out.println("1. Add Teacher");
        System.out.println("2. Show Teachers");
        System.out.println("3. Search Teacher");
        System.out.println("4. Delete Teacher");
        System.out.println("5. Update Teacher");
        System.out.println("6. Back");
    }

    private int readMenuChoice() {
        return inputHelper.readIntegerInRange("Choose a Valid Option", 1, 6);
    }

    //==========ADD TEACHER==========\\
     void addTeacher() {
        System.out.println("Add Teacher:");

        String name = inputHelper.readName("Enter a Teacher Name: ");
        int age = inputHelper.readIntegerInRange("Enter Teacher's Age: ", 22, 75);
        int teacherId = readUniqueTeacherId("Enter a Teacher ID: ");
        String subject = inputHelper.readName("Enter Teacher's Subject:");


        try {
            Teacher teacher = teacherService.addTeacher(name, age, teacherId, subject);  // The service validates and then stores the teacher.
            System.out.println("Teacher Successfully Added: ");
            teacher.displayTeacher();  // to display the teacher returned by the service
        } catch (IllegalArgumentException e) {
            System.out.println("Teacher Couldn't Be Added: " + e.getMessage());  // displays the services valid message.
        }
    }

    // ==================== READ UNIQUE TEACHER ID ====================
    private int readUniqueTeacherId(String message) {
        while (true) {

            // First ensures that the ID is a positive whole number.
            int teacherId = inputHelper.readPositiveInteger(message);

            // Searches for an existing teacher with the entered ID.
            Teacher existingTeacher = teacherService.getTeacherById(teacherId);

            if (existingTeacher != null) {
                System.out.println("Teacher ID already exists. Please enter another ID.");
            } else {
                return teacherId; // Returns only an unused ID.
            }
        }
    }


    //==========SHOW TEACHERS============\\
     void showTeachers() {
        ArrayList<Teacher> teachers = teacherService.getAllTeachers();

        if (teachers.isEmpty()) {
            System.out.println("There are no Teachers to display.");
            return;
        }

        System.out.println("Teachers List.");
        for (Teacher teacher : teachers) {
            teacher.displayTeacher();
        }
    }


    //==========SEARCH TEACHER==========\\
     void searchTeacher() {
        System.out.println("Search Teacher.");
        System.out.println("1.Search Teacher by ID");
        System.out.println("2.Search Teacher by Name:");
        System.out.println("3.Back.");

        int searchChoice = inputHelper.readIntegerInRange("Select an Option:", 1, 3); // reads the search menu choice the user inputs.

        try {
            Teacher teacher; // this will be storing the result returned by the service.

            if (searchChoice == 1) {
                int teacherId = inputHelper.readPositiveInteger("Enter a Teacher Id: ");
                teacher = teacherService.getTeacherById(teacherId); // this is to search using teacher id.
            } else if (searchChoice == 2) {
                String name = inputHelper.readName("Enter a Teacher Name: ");
                teacher = teacherService.getTeacherByName(name);
            } else if (searchChoice == 3) {
                return;
            } else {
                return;
            }
            if (teacher == null) {
                System.out.println("Teacher Was Not Found!.");
                return;
            }
            System.out.println("Teacher Was Found.");
            teacher.displayTeacher();
        } catch (IllegalArgumentException e) {     // displays the validation errors thrown by Teacher Service.
            System.out.println("Search has failed: " + e.getMessage());
        }
    }

    //==========DELETE TEACHER==========\\
     void deleteTeacher() {
        System.out.println("Delete Teacher.");

        int teacherId = inputHelper.readPositiveInteger("Enter the Teacher ID you want to delete.");  // reads the ID of the Teacher that the user wants to delete.

        try {
            Teacher teacher = teacherService.getTeacherById(teacherId);

            if (teacher == null) {
                System.out.println("Teacher Was Not Found:");  // the repo returns null if the id does not exist.
                return;
            }

            System.out.println("Teacher Found:");    // Shows the teacher before asking for confirmation.
            teacher.displayTeacher();

            System.out.println("Are you sure you want to delete this teacher?");
            System.out.println("1. Yes");
            System.out.println("2. No");

            int confirm = inputHelper.readIntegerInRange("Select an option: ", 1, 2);
            if (confirm == 2) {
                System.out.println("Delete Process Cancelled.");
                return;
            }

            boolean deleted = teacherService.deleteTeacherById(teacherId); // asks the service to delete the teacher with this ID.

            if (deleted) {
                System.out.println("Teacher deleted successfully.");
            } else {
                System.out.println("Teacher could not be deleted.");
            }

        } catch (IllegalArgumentException e) {
            // Displays validation errors thrown by TeacherService.
            System.out.println("Delete has failed: " + e.getMessage());
        }
    }


    //==========UPDATE TEACHER==========\\
     void updateTeacher() {
        System.out.println("Update Teacher:");

        // Reads the ID and accepts only a positive number.
        int teacherId = inputHelper.readPositiveInteger("Enter the ID of the teacher you want to update:");

        try {  // Searches for the Teacher before requesting the new information.
            Teacher teacher = teacherService.getTeacherById(teacherId);

            if (teacher == null) {
                System.out.println("Teacher was not found!");
                return;
            }

            System.out.println("Current Teacher Information:");   // To display the current info of the existing teachers.
            teacher.displayTeacher();

            System.out.println("Choose What do you want to update.");   //to display the update submenu.
            System.out.println("1.Name.");
            System.out.println("2.Age.");
            System.out.println("3.Name and Age.");
            System.out.println("4.Cancel.");

            // Accepts only update-menu options from 1 to 4.
            int updateChoice = inputHelper.readIntegerInRange("Select an Option: ", 1, 4);

            if (updateChoice == 4) {
                System.out.println("Update Process has been Cancelled.");
                return;
            }

            // Starts with the teachers current info
            String newName = teacher.getName();
            int newAge = teacher.getAge();

            //only changes the values selected by the user.
            switch (updateChoice) {

                case 1:
                    newName = inputHelper.readName("Enter Teacher's New Name: ");
                    break;

                case 2:
                    newAge = inputHelper.readIntegerInRange("Enter Teacher's New Age: ", 22, 75);
                    break;

                case 3:
                    newName = inputHelper.readName("Enter Teacher's New Name: ");
                    newAge = inputHelper.readIntegerInRange("Enter Teacher's New Age: ", 22, 75);
                    break;
            }

            // Sends the new values and unchanged values to the service.
            Teacher updatedTeacher = teacherService.updateTeacher(teacherId, newName, newAge);

            if (updatedTeacher == null) {
                System.out.println("Teacher could not be updated.");
                return;
            }

            System.out.println("Teacher updated successfully:");
            updatedTeacher.displayTeacher();

        } catch (IllegalArgumentException e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }
}