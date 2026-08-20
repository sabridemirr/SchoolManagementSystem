package com.schoolmanagement.service;

import com.schoolmanagement.model.Teacher;
import com.schoolmanagement.repository.TeacherRepository;

import java.util.ArrayList;

public class TeacherService {

    // ==================== REPOSITORY CONNECTION ====================
    private final TeacherRepository teacherRepository; // Stores the repository that this service will use. Also final because once it receives its repo it should keep using the same repo.

    // ==================== CONSTRUCTOR ====================
    public TeacherService(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository; // Saves the repository received from Main.
    }

    // ==================== ADD TEACHER ====================
    public Teacher addTeacher(String name, int age, int teacherId, String subject) {
        if (name == null || name.trim().isEmpty()) { // Rejects null, empty text, and spaces only.
            throw new IllegalArgumentException("Teacher name cannot be empty.");
        }

        if (!name.matches("[a-zA-Z ]+")) { // Rejects numbers and special characters in the name.
            throw new IllegalArgumentException("Teacher name must contain letters only.");
        }

        if (age < 22 || age > 75) { // Applies the project's accepted age range.
            throw new IllegalArgumentException("Teacher age must be between 22 and 75.");
        }

        if (teacherId <= 0) { // Prevents zero and negative IDs.
            throw new IllegalArgumentException("Teacher ID must be greater than 0.");
        }

        if (teacherRepository.existsById(teacherId)) { // Asks the repository whether this ID is already stored.
            throw new IllegalArgumentException("Teacher ID already exists.");
        }

        if (subject == null || subject.trim().isEmpty()) { // Rejects null, empty text, and spaces only.
            throw new IllegalArgumentException("Teacher subject cannot be empty.");
        }

        if (!subject.matches("[a-zA-Z ]+")) { // Allows letters and spaces, such as "Computer Science".
            throw new IllegalArgumentException("Teacher subject must contain letters only.");
        }

        Teacher teacher = new Teacher(name.trim(), age, teacherId, subject.trim()); // Creates the Teacher only after every rule passes and removes extra space from name & subject.
        teacherRepository.add(teacher); // Sends the valid Teacher to the repository for storage.
        return teacher; // Returns the newly created Teacher to the future controller.
    }

    // ==================== GET ALL TEACHERS ===================
    public ArrayList<Teacher> getAllTeachers() {
        return teacherRepository.findAll(); // Gets all teachers stored in the repository.
    }

    // ==================== SEARCH TEACHER BY ID ====================
    public Teacher getTeacherById(int teacherId) {
        if (teacherId <= 0) {
            throw new IllegalArgumentException("Teacher ID can't be 0 or less than 0");
        }
        return teacherRepository.findById(teacherId);  // it will either return the Teacher or null if the teacher is not found.
    }

    // ==================== SEARCH TEACHER BY NAME ====================
    public Teacher getTeacherByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Teacher name is not allowed to be Empty.");
        }
        if (!name.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Teacher name must only consist letter.");
        }
        return teacherRepository.findByName(name.trim()); // Removes extra spaces before searching.
    }

    // ==================== DELETE TEACHER BY ID ====================
    public boolean deleteTeacherById(int teacherId) {

        if (teacherId <= 0) {    // to prevent 0 or negative.
            throw new IllegalArgumentException("Teacher Id can't be 0 or less than 0.");
        }

        Teacher teacher = teacherRepository.findById(teacherId);    //This searches for the teacher before trying to delete it.

        if (teacher == null) {   // teacher cannot be deleted if the ID was not found.
            return false;
        }
        return teacherRepository.delete(teacher);
    }

    // ==================== UPDATE TEACHER ====================

    public Teacher updateTeacher(int teacherId, String newName, int newAge) {

        if (teacherId <= 0) {     //Teacher ID must always be positive.
            throw new IllegalArgumentException("Teacher ID can't be 0 or less than 0");
        }

        Teacher teacher = teacherRepository.findById(teacherId);  // this finds the teacher that will be updated.

        if (teacher == null) {     // returns null if the teacher does not exist.
            return null;
        }

        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("Teacher Name is not allowed to be empty.");
        }

        if (!newName.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Teacher Name cannot contain special letters it must contain only letters.");
        }

        if (newAge < 22 || newAge > 75) {
            throw new IllegalArgumentException("Teacher age must be between 22 and 75.");
        }

        //=========CHANGES THE TEACHERS EDITABLE INFO.=========
        teacher.setName(newName.trim());
        teacher.setAge(newAge);
        return teacher;          // Returns the updated teacher so the controller can display it.
    }
}
