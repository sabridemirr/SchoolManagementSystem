package com.schoolmanagement.service;

import com.schoolmanagement.model.Student;
import com.schoolmanagement.repository.StudentRepository;
import java.util.ArrayList;

public class StudentService {

    // ==================== REPOSITORY CONNECTION ====================
    private final StudentRepository studentRepository; // Stores the repository that this service will use, Also final because once it receives its repo it should keep using the same repo.

    // ==================== CONSTRUCTOR ====================
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository; // Saves the repository received from Main.
    }

    // ==================== ADD STUDENT ====================
    public Student addStudent(String name, int age, int studentId, double grade) {
        if (name == null || name.trim().isEmpty()) { // Rejects null, empty text, and spaces only.
            throw new IllegalArgumentException("Student name cannot be empty.");
        }

        if (!name.matches("[a-zA-Z ]+")) { // Rejects numbers and special characters in the name.
            throw new IllegalArgumentException("Student name must contain letters only.");
        }

        if (age < 4 || age > 61) { // Applies the project's accepted age range.
            throw new IllegalArgumentException("Student age must be between 4 and 61.");
        }

        if (studentId <= 0) { // Prevents zero and negative IDs.
            throw new IllegalArgumentException("Student ID must be greater than 0.");
        }

        if (studentRepository.existsById(studentId)) { // Asks the repository whether this ID is already stored.
            throw new IllegalArgumentException("Student ID already exists.");
        }

        if (grade < 0 || grade > 100) { // Applies the valid grade range.
            throw new IllegalArgumentException("Student grade must be between 0 and 100.");
        }

        Student student = new Student(name.trim(), age, studentId, grade); // Creates the Student only after every rule passes.
        studentRepository.add(student); // Sends the valid Student to the repository for storage.
        return student; // Returns the newly created Student to the future controller.
    }

    // ==================== GET ALL STUDENTS ===================
    public ArrayList<Student> getAllStudents() {
        return studentRepository.findAll(); // Gets all students stored in the repository.
    }

    // ==================== SEARCH STUDENT BY ID ====================
    public Student getStudentById(int studentId) {
        if (studentId <= 0) {
            throw new IllegalArgumentException("Student ID can't be 0 or less than 0");
        }
        return studentRepository.findById(studentId);  // it will either return the student or null if the student is not found.
    }

    // ==================== SEARCH STUDENT BY NAME ====================
    public Student getStudentByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name is not allowed to be Empty.");
        }
        if (!name.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Student name must only consist letter.");
        }
        return studentRepository.findByName(name.trim()); // Removes extra spaces before searching.
    }

    // ==================== DELETE STUDENT BY ID ====================
    public boolean deleteStudentById(int studentId) {

        if (studentId <= 0) {    // to prevent 0 or negative.
            throw new IllegalArgumentException("Student Id can't be 0 or less than 0.");
        }

        Student student = studentRepository.findById(studentId);    //This searches for the student before trying to delete it.

        if (student == null) {   // student cannot be deleted if the ID was not found.
            return false;
        }

        return studentRepository.delete(student);
    }

// ==================== UPDATE STUDENT ====================

    public Student updateStudent(int studentId, String newName, int newAge, double newGrade) {

        if (studentId <= 0) {     //Student ID must always be positive.
            throw new IllegalArgumentException("Student ID can't be 0 or less than 0");
        }

        Student student = studentRepository.findById(studentId);  // this finds the student that will be updated.

        if (student == null) {     // returns null if the student does not exist.
            return null;
        }

        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Student Name is not allowed to be empty.");
        }

        if (!newName.matches("[a-zA-Z ]+")){
            throw new IllegalArgumentException("Student Name cannot contain special letters it must contain only letters.");
        }

        if (newAge < 4 || newAge > 61) {
            throw new IllegalArgumentException("Student age must be between 4 and 61");
        }

        if (newGrade < 0 || newGrade > 100) {
            throw new IllegalArgumentException("Student Grade cannot be less than 0 and more than 100.");
        }

//=========CHANGES THE STUDENTS EDITABLE INFO.=========
        student.setName(newName.trim());
        student.setAge(newAge);
        student.setGrade(newGrade);
        studentRepository.update(student);

        return student;          // Returns the updated student so the controller can display it.
    }
}