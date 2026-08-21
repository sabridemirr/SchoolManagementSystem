package com.schoolmanagement.controller;

import com.schoolmanagement.model.Parent;
import com.schoolmanagement.service.ParentService;
import com.schoolmanagement.util.InputHelper;

import java.util.ArrayList;
import java.util.Scanner;

public class ParentController {

    private final ParentService parentService;
    private final InputHelper inputHelper;

    public ParentController(Scanner scanner, ParentService parentService) {
        this.parentService = parentService; // stores the service received from main.
        this.inputHelper = new InputHelper(scanner);
    }

    public void showMenu() {

        int choice;

        do {
            printMenu();
            choice = readMenuChoice();

            switch (choice) {

                case 1:
                    addParent();
                    break;

                case 2:
                    showParents();
                    break;

                case 3:
                    searchParent();
                    break;

                case 4:
                    deleteParent();
                    break;

                case 5:
                    updateParent();
                    break;

                case 6:
                    System.out.println("Returning to Main Menu.");
                    break;
            }
        } while (choice != 6);
    }

    private void printMenu() {
        System.out.println("~~~~~ Parent Management System ~~~~~");
        System.out.println("1. Add Parent");
        System.out.println("2. Show Parents");
        System.out.println("3. Search Parent");
        System.out.println("4. Delete Parent");
        System.out.println("5. Update Parent");
        System.out.println("6. Back");
    }

    private int readMenuChoice() {
        return inputHelper.readIntegerInRange("Choose a Valid Option", 1, 6);
    }

    //==========ADD PARENT==========\\
     void addParent() {
        System.out.println("Add Parent:");

        String name = inputHelper.readName("Enter a Parent Name: ");
        int age = inputHelper.readIntegerInRange("Enter Parent's Age: ", 18, 100);
        int parentId = readUniqueParentId("Enter a Parent ID: ");
        String email = inputHelper.readText("Enter a Parent email: ");
        String phoneNumber = inputHelper.readText("Enter Parent's phone number: ");
        int studentId = inputHelper.readPositiveInteger("Enter a Student Id: ");
        String relationship = inputHelper.readName("Enter Parent's RelationShip: ");

        try {
            Parent parent = parentService.addParent(name, age, parentId, email, phoneNumber, studentId, relationship);  // The service validates and then stores the parent.
            System.out.println("Parent Successfully Added: ");
            parent.displayParent();  // to display the parent returned by the service
        } catch (IllegalArgumentException e) {
            System.out.println("Parent Couldn't Be Added: " + e.getMessage());  // displays the services valid message.
        }
    }

    // ==================== READ UNIQUE PARENT ID ====================
    private int readUniqueParentId(String message) {
        while (true) {

            // First ensures that the ID is a positive whole number.
            int parentId = inputHelper.readPositiveInteger(message);

            // Searches for an existing parent with the entered ID.
            Parent existingParent = parentService.getParentById(parentId);

            if (existingParent != null) {
                System.out.println("Parent ID already exists. Please enter another ID.");
            } else {
                return parentId; // Returns only an unused ID.
            }
        }
    }

    //==========SHOW PARENTS============\\
     void showParents() {
        ArrayList<Parent> parents = parentService.getAllParents();

        if (parents.isEmpty()) {
            System.out.println("There are no Parents to display.");
            return;
        }

        System.out.println("Parents List.");
        for (Parent parent : parents) {
            parent.displayParent();
        }
    }

    //==========SEARCH PARENT==========\\
     void searchParent() {
        System.out.println("Search Parent.");
        System.out.println("1.Search Parent by ID");
        System.out.println("2.Search Parent by Name:");
        System.out.println("3.Back.");

        int searchChoice = inputHelper.readIntegerInRange("Select an Option:", 1, 3); // reads the search menu choice the user inputs.

        try {
            Parent parent; // this will be storing the result returned by the service.

            if (searchChoice == 1) {
                int parentId = inputHelper.readPositiveInteger("Enter a Parent Id: ");
                parent = parentService.getParentById(parentId); // this is to search using parent id.
            } else if (searchChoice == 2) {
                String name = inputHelper.readName("Enter a Parent Name: ");
                parent = parentService.getParentByName(name);
            } else if (searchChoice == 3) {
                return;
            } else {
                return;
            }
            if (parent == null) {
                System.out.println("Parent Was Not Found!.");
                return;
            }
            System.out.println("Parent Was Found.");
            parent.displayParent();
        } catch (IllegalArgumentException e) {     // displays the validation errors thrown by Parent Service.
            System.out.println("Search has failed: " + e.getMessage());
        }
    }

    //==========DELETE PARENT==========\\
     void deleteParent() {
        System.out.println("Delete Parent.");

        int parentId = inputHelper.readPositiveInteger("Enter the Parent ID you want to delete.");  // reads the ID of the Parent that the user wants to delete.

        try {
            Parent parent = parentService.getParentById(parentId);

            if (parent == null) {
                System.out.println("Parent Was Not Found:");  // the repo returns null if the id does not exist.
                return;
            }

            System.out.println("Parent Found:");    // Shows the Parent before asking for confirmation.
            parent.displayParent();

            System.out.println("Are you sure you want to delete this Parent?");
            System.out.println("1. Yes");
            System.out.println("2. No");

            int confirm = inputHelper.readIntegerInRange("Select an option: ", 1, 2);
            if (confirm == 2) {
                System.out.println("Delete Process Cancelled.");
                return;
            }

            boolean deleted = parentService.deleteParentById(parentId); // asks the service to delete the Parent with this ID.

            if (deleted) {
                System.out.println("Parent deleted successfully.");
            } else {
                System.out.println("Parent could not be deleted.");
            }

        } catch (IllegalArgumentException e) {
            // Displays validation errors thrown by ParentService.
            System.out.println("Delete has failed: " + e.getMessage());
        }
    }

    // ==================== UPDATE PARENT ====================
     void updateParent() {
        System.out.println("Update Parent:");

        // Reads a positive Parent ID to find the Parent being updated.
        int parentId = inputHelper.readPositiveInteger(
                "Enter the ID of the parent you want to update:"
        );

        try {
            Parent parent = parentService.getParentById(parentId); // Searches for the Parent.

            if (parent == null) {
                System.out.println("Parent was not found.");
                return;
            }

            System.out.println("Current Parent Information:");
            parent.displayParent(); // Displays all current information before updating.

            System.out.println("Choose what you want to update:");
            System.out.println("1. Name");
            System.out.println("2. Age");
            System.out.println("3. Phone Number");
            System.out.println("4. Email");
            System.out.println("5. All Editable Information");
            System.out.println("6. Cancel");

            int updateChoice = inputHelper.readIntegerInRange(
                    "Select an Option: ", 1, 6
            ); // Accepts only update options from 1 to 6.

            if (updateChoice == 6) {
                System.out.println("Update process has been cancelled.");
                return;
            }

            // Starts with the Parent's existing values.
            String newName = parent.getName();
            int newAge = parent.getAge();
            String newPhoneNumber = parent.getPhoneNumber();
            String newEmail = parent.getEmail();

            // Changes only the values selected by the user.
            switch (updateChoice) {
                case 1:
                    newName = inputHelper.readName("Enter Parent's New Name: ");
                    break;

                case 2:
                    newAge = inputHelper.readIntegerInRange(
                            "Enter Parent's New Age: ", 18, 100
                    );
                    break;

                case 3:
                    newPhoneNumber = inputHelper.readText(
                            "Enter Parent's New Phone Number: "
                    ).trim();
                    break;

                case 4:
                    newEmail = inputHelper.readText(
                            "Enter Parent's New Email: "
                    ).trim();
                    break;

                case 5:
                    newName = inputHelper.readName("Enter Parent's New Name: ");
                    newAge = inputHelper.readIntegerInRange(
                            "Enter Parent's New Age: ", 18, 100
                    );
                    newPhoneNumber = inputHelper.readText(
                            "Enter Parent's New Phone Number: "
                    ).trim();
                    newEmail = inputHelper.readText(
                            "Enter Parent's New Email: "
                    ).trim();
                    break;
            }

            // Parent ID, Student ID, and relationship are not passed because they cannot change.
            Parent updatedParent = parentService.updateParent(
                    parentId,
                    newName,
                    newAge,
                    newPhoneNumber,
                    newEmail
            );

            if (updatedParent == null) {
                System.out.println("Parent could not be updated.");
                return;
            }

            System.out.println("Parent updated successfully:");
            updatedParent.displayParent(); // Also displays the unchanged IDs and relationship.

        } catch (IllegalArgumentException e) {
            System.out.println("Update failed: " + e.getMessage());
        }
    }
}
