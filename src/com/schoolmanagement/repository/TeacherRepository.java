package com.schoolmanagement.repository;

import com.schoolmanagement.model.Teacher;
import java.util.ArrayList;

public class TeacherRepository {

    // ==================== TEACHER STORAGE ====================
    private final ArrayList<Teacher> teachers = new ArrayList<>(); // The repository owns and stores the teacher objects.
    // This creates the repository's internal teacher list

    // ==================== ADD TEACHER ====================
    public void add(Teacher teacher) {
        teachers.add(teacher); // Adds the provided Teacher object to storage.
    }

    // ==================== GET ALL TEACHERS ====================
    public ArrayList<Teacher> findAll() {
        return new ArrayList<>(teachers); // Returns a copy so outside code cannot directly damage the stored list. This way the repos list structure is protected
    }

    // ==================== FIND TEACHER BY ID ==================
    public Teacher findById(int teacherId) {
        for (Teacher teacher : teachers) { // Checks every stored teacher.
            if (teacher.getTeacherId() == teacherId) { // Compares the existing ID with the requested ID.
                return teacher; // Immediately returns the matching teacher object.
            }
        }
        return null; // No teacher had the requested ID.
    }

    // ==================== FIND TEACHER BY NAME ====================
    public Teacher findByName(String name) {
        for (Teacher teacher : teachers) { // Checks every stored teacher.
            if (teacher.getName().equalsIgnoreCase(name)) { // Ignores uppercase and lowercase differences.
                return teacher; // Returns the first teacher with the matching name.
            }
        }
        return null; // No teacher had the requested name.
    }

    // ==================== CHECK WHETHER ID EXISTS ====================
    public boolean existsById(int teacherId) {
        return findById(teacherId) != null; // Returns true when findById finds a teacher.
    }

    // ==================== DELETE TEACHER ====================
    public boolean delete(Teacher teacher) {
        return teachers.remove(teacher); // Removes the teacher and reports whether removal succeeded.
    }

    // ==================== CHECK WHETHER REPOSITORY IS EMPTY ====================
    public boolean isEmpty() {
        return teachers.isEmpty(); // Returns true when no teachers are stored.
    }
}
