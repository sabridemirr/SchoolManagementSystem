/*
 * Handles student business logic using Spring Data JPA.
 * This class is temporary during the migration.
 * After the console version is removed, it can replace the old StudentService.
 */

package com.schoolmanagement.service;

import com.schoolmanagement.repository.StudentJpaRepository;
import org.springframework.stereotype.Service;
import com.schoolmanagement.model.Student; // The entity created and saved by this service.

import java.util.List;

@Service // Tells Spring to create and manage an object of this service automatically.
// With @Service, Spring creates StudentJpaService and connects its repository automatically.

public class StudentJpaService {
    private final StudentJpaRepository studentRepository; // Gives the service access to student database operations.

    /*
     * Spring automatically provides StudentJpaRepository through this constructor.
     * This process is called dependency injection.
     */
    public StudentJpaService(StudentJpaRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

/*
* Validates the supplied information and saves a new student.
*/
    public Student addStudent(String name, int age, int studentId) {

        // Rejects null, empty text and text containing only spaces.
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Student name cannot be empty.");
        }

        // Allows letters and spaces only.
        if (!name.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Student name must contain letters only.");
        }

        // Applies the accepted student age range.
        if (age < 4 || age > 61) {
            throw new IllegalArgumentException("Student age must be between 4 and 61.");
        }

        // Prevents zero and negative student IDs.
        if (studentId <= 0) {
            throw new IllegalArgumentException("Student ID must be greater than 0.");
        }

        // Prevents save() from replacing a student that already uses this ID.
        if (studentRepository.existsById(studentId)) {
            throw new IllegalArgumentException("Student ID already exists.");
        }

        // Creates the entity only after all validation rules pass.
        Student student = new Student(name.trim(), age, studentId);

        // JPA inserts the student into the students table and returns the saved entity.
        return studentRepository.save(student);
    }

/* Retrieves every student stored in the students table.*/
    public List<Student> getAllStudents() {
        return studentRepository.findAll(); // JPA performs the SELECT query automatically.
    }
}
