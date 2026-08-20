package com.schoolmanagement.service;

import com.schoolmanagement.model.Parent;
import com.schoolmanagement.repository.ParentRepository;

import java.util.ArrayList;

public class ParentService {

    // ==================== REPOSITORY CONNECTION ====================
    private final ParentRepository parentRepository; // Stores the repository that this service will use. Also final because once it receives its repo it should keep using the same repo.
    private final StudentService studentService; // Gives ParentService access to the existing students.

    // ==================== CONSTRUCTOR ====================
    public ParentService(ParentRepository parentRepository, StudentService studentService) {
        this.parentRepository = parentRepository; // Saves the repository received from Main.
        this.studentService = studentService; // Stores the same StudentService used by StudentController.
    }

    // ==================== ADD PARENT ====================
    public Parent addParent(String name, int age, int parentId, String email, String phoneNumber, int studentId, String relationship) {

        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Parent name cannot be empty.");
        }

        if (!name.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Parent name must contain letters only.");
        }

        if (age < 18 || age > 100) {
            throw new IllegalArgumentException("Parent age must be between 18 and 100.");
        }

        if (phoneNumber == null || phoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty.");
        }

        if (!phoneNumber.matches("[0-9]{10,15}")) {
            throw new IllegalArgumentException("Phone number must contain between 10 and 15 digits.");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }

        if (!email.matches("[a-zA-Z0-9._+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            throw new IllegalArgumentException("Please enter a valid email address.");
        }

        if (parentId <= 0) {
            throw new IllegalArgumentException("Parent ID must be greater than 0.");
        }

        if (parentRepository.existsById(parentId)) {
            throw new IllegalArgumentException("Parent ID already exists.");
        }

        if (studentId <= 0) {
            throw new IllegalArgumentException("Student ID must be greater than 0.");
        }

        if (studentService.getStudentById(studentId) == null) { // Verifies that the parent is linked to an existing student.
            throw new IllegalArgumentException("No student exists with this ID.");
        }

        if (relationship == null || relationship.trim().isEmpty()) {
            throw new IllegalArgumentException("Relationship cannot be empty.");
        }

        if (!relationship.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Relationship must contain letters only.");
        }

        Parent parent = new Parent(name.trim(), age, parentId, email.trim(), phoneNumber.trim(), studentId, relationship.trim()); // Creates the Parent only after every rule passes.
        parentRepository.add(parent); // Stores the valid parent.
        return parent; // Returns the new parent to the controller.
    }

    // ==================== GET ALL PARENTS ===================
    public ArrayList<Parent> getAllParents() {
        return parentRepository.findAll(); // Gets all parents stored in the repository.
    }

    // ==================== SEARCH PARENT BY ID ====================
    public Parent getParentById(int parentId) {
        if (parentId <= 0) {
            throw new IllegalArgumentException("Parent ID can't be 0 or less than 0");
        }
        return parentRepository.findById(parentId);  // it will either return the Parent or null if the Parent is not found.
    }

    // ==================== SEARCH PARENT BY NAME ====================
    public Parent getParentByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Parent name is not allowed to be Empty.");
        }
        if (!name.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Parent name must only consist letter.");
        }
        return parentRepository.findByName(name.trim()); // Removes extra spaces before searching.
    }

    // ==================== DELETE PARENT BY ID ====================
    public boolean deleteParentById(int parentId) {

        if (parentId <= 0) {    // to prevent 0 or negative.
            throw new IllegalArgumentException("Parent Id can't be 0 or less than 0.");
        }

        Parent parent = parentRepository.findById(parentId);    //This searches for the parent before trying to delete it.

        if (parent == null) {   // parent cannot be deleted if the ID was not found.
            return false;
        }
        return parentRepository.delete(parent);
    }

    // ==================== UPDATE PARENT ====================
    public Parent updateParent(int parentId, String newName, int newAge, String newPhoneNumber, String newEmail) {

        if (parentId <= 0) { // Parent ID identifies the parent and must be positive.
            throw new IllegalArgumentException("Parent ID must be greater than 0.");
        }

        Parent parent = parentRepository.findById(parentId); // Finds the parent that will be updated.

        if (parent == null) { // Returns null when the parent does not exist.
            return null;
        }

        // ==================== VALIDATE NEW NAME ====================
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Parent name cannot be empty.");
        }

        if (!newName.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Parent name must contain letters only.");
        }

        // ==================== VALIDATE NEW AGE ====================
        if (newAge < 18 || newAge > 100) {
            throw new IllegalArgumentException("Parent age must be between 18 and 100.");
        }

        // ==================== VALIDATE NEW PHONE NUMBER ====================
        if (newPhoneNumber == null || newPhoneNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty.");
        }
        if (!newPhoneNumber.matches("[0-9]{10,15}")) {
            throw new IllegalArgumentException("Phone number must contain between 10 and 15 digits.");
        }

        // ==================== VALIDATE NEW EMAIL ====================
        if (newEmail == null || newEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }
        if (!newEmail.matches("[a-zA-Z0-9._+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}")) {
            throw new IllegalArgumentException("Please enter a valid email address.");
        }

        // ==================== CHANGE EDITABLE INFORMATION ====================
        parent.setName(newName.trim());
        parent.setAge(newAge);
        parent.setPhoneNumber(newPhoneNumber.trim());
        parent.setEmail(newEmail.trim());

        return parent; // Returns the updated parent to the controller.
    }
}



    