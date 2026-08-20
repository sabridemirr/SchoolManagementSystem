package com.schoolmanagement.repository;

import com.schoolmanagement.model.Student;
import java.util.ArrayList;

public class StudentRepository {

    // ==================== STUDENT STORAGE ====================
    private final ArrayList<Student> students = new ArrayList<>(); // The repository owns and stores the student objects.     // This creates the repositories internal student list

    // ==================== ADD STUDENT ====================
    public void add(Student student) {
        students.add(student); // Adds the provided Student object to storage.
    }

    // ==================== GET ALL STUDENTS ====================
    public ArrayList<Student> findAll() {
        return new ArrayList<>(students); // Returns a copy so outside code cannot directly damage the stored list. This way the repos list structure is protected
    }

    // ==================== FIND STUDENT BY ID ====================
    public Student findById(int studentId) {
        for (Student student : students) { // Checks every stored student.
            if (student.getStudentId() == studentId) { // Compares the existing ID with the requested ID.
                return student; // Immediately returns the matching Student object.
            }
        }
        return null; // No student had the requested ID.
    }

    // ==================== FIND STUDENT BY NAME ====================
    public Student findByName(String name) {
        for (Student student : students) { // Checks every stored student.
            if (student.getName().equalsIgnoreCase(name)) { // Ignores uppercase and lowercase differences.
                return student; // Returns the first student with the matching name.
            }
        }
        return null; // No student had the requested name.
    }

    // ==================== CHECK WHETHER ID EXISTS ====================
    public boolean existsById(int studentId) {
        return findById(studentId) != null; // Returns true when findById finds a student.
    }

    // ==================== DELETE STUDENT ====================
    public boolean delete(Student student) {
        return students.remove(student); // Removes the student and reports whether removal succeeded.
    }

    // ==================== CHECK WHETHER REPOSITORY IS EMPTY ====================
    public boolean isEmpty() {
        return students.isEmpty(); // Returns true when no students are stored.
    }
}

