package com.schoolmanagement.repository;

import com.schoolmanagement.model.Student;
import java.util.ArrayList;

public class StudentRepository {

    // This creates the repositories internal student list
    private final ArrayList<Student> students = new ArrayList<>(); // The repository owns and stores the student objects.

    public void add(Student student) {
        students.add(student); // Adds the provided Student object to storage.
    }

    public ArrayList<Student> findAll() {
        return new ArrayList<>(students); // Returns a copy so outside code cannot directly damage the stored list. This way the repos list structure is protected
    }

    public Student findById(int studentId) {
        for (Student student : students) { // Checks every stored student.
            if (student.getStudentId() == studentId) { // Compares the existing ID with the requested ID.
                return student; // Immediately returns the matching Student object.
            }
        }

        return null; // No student had the requested ID.
    }

    public Student findByName(String name) {
        for (Student student : students) { // Checks every stored student.
            if (student.getName().equalsIgnoreCase(name)) { // Ignores uppercase and lowercase differences.
                return student; // Returns the first student with the matching name.
            }
        }

        return null; // No student had the requested name.
    }

    public boolean existsById(int studentId) {
        return findById(studentId) != null; // Returns true when findById finds a student.
    }

    public boolean delete(Student student) {
        return students.remove(student); // Removes the student and reports whether removal succeeded.
    }

    public boolean isEmpty() {
        return students.isEmpty(); // Returns true when no students are stored.
    }
}

