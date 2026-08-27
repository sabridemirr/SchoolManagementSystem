/*
 * Contains student-related business rules and validation.
 *
 * Responsibilities:
 * - Validates student information before database operations.
 * - Prevents duplicate student IDs and duplicate subjects.
 * - Confirms that a student exists before subject operations.
 * - Coordinates Student objects with StudentRepository.
 *
 * The service does not read console input and does not contain SQL.
 */


package com.schoolmanagement.service;

import com.schoolmanagement.model.Student;
import com.schoolmanagement.repository.StudentRepository;
import java.util.ArrayList;

public class StudentService {

    // ==================== REPOSITORY CONNECTION ====================
    private final StudentRepository studentRepository; // Stores the repository that this service will use &  final means the service keeps the same repository after construction.

    // ==================== CONSTRUCTOR ====================
    /* Constructor injection: Main provides the repository that this service uses.*/
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository; // Saves the repository received from Main.
    }

    // ==================== ADD STUDENT ====================
    public Student addStudent(String name, int age, int studentId) {
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

        Student student = new Student(name.trim(), age, studentId); // Creates the Student only after every rule passes.
        studentRepository.add(student); // Sends the valid Student to the repository for storage.
        return student; // Returns the newly created Student to the future controller.
    }


    // ==================== ADD SUBJECT AND GRADE TO STUDENT ====================
    public void addSubjectToStudent(int studentId, String subjectName, double grade) {

        if (studentId <= 0) { // Student IDs must be positive.
            throw new IllegalArgumentException(
                    "Student ID must be greater than 0."
            );
        }

        Student student = studentRepository.findById(studentId); // Confirms that the student exists in the database.
        if (student == null) {
            throw new IllegalArgumentException(
                    "Student was not found."
            );
        }

        if (subjectName == null || subjectName.trim().isEmpty()) {  // Rejects null, empty text and spaces only.
            throw new IllegalArgumentException(
                    "Subject name cannot be empty."
            );
        }

        String cleanSubjectName = subjectName.trim(); // Removes extra spaces around the name.
        if (!cleanSubjectName.matches("[a-zA-Z ]+")) {  // Allows names such as Mathematics and Computer Science.
            throw new IllegalArgumentException("Subject name must contain letters only.");
        }

        if (grade < 0 || grade > 100) {  // Every subject grade must be between 0 and 100.
            throw new IllegalArgumentException("Subject grade must be between 0 and 100.");
        }

        if (studentRepository.studentHasSubject(studentId, cleanSubjectName)) { // Prevents the same student from receiving the same subject twice.
            throw new IllegalArgumentException("The student already takes this subject.");
        }
        studentRepository.addSubjectToStudent(studentId, cleanSubjectName, grade);  // Permanently inserts the subject and grade into student_subjects.
    }


    // ==================== GET STUDENT SUBJECTS AND GRADES ====================
    public String getStudentSubjects(int studentId) {

        if (studentId <= 0) {
            throw new IllegalArgumentException("Student ID must be greater than 0.");
        }
        Student student = studentRepository.findById(studentId);  // Subjects cannot be requested for a nonexistent student.

        if (student == null) {
            throw new IllegalArgumentException("Student was not found.");
        }
        return studentRepository.findSubjectsByStudentId(studentId);  // Reads the subject names and respective grades from H2.
    }


    // ==================== UPDATE SUBJECT GRADE ====================
    public boolean updateSubjectGrade(int studentId, String subjectName, double newGrade) {

        // The student ID must be positive.
        if (studentId <= 0) {
            throw new IllegalArgumentException("Student ID must be greater than 0.");
        }

        // Makes sure the student exists before trying to update a subject.
        Student student = studentRepository.findById(studentId);

        if (student == null) {
            throw new IllegalArgumentException("Student was not found.");
        }

        // The subject name cannot be null, empty, or spaces only.
        if (subjectName == null || subjectName.trim().isEmpty()) {
            throw new IllegalArgumentException("Subject name cannot be empty.");
        }

        // The subject name can contain only letters and spaces.
        if (!subjectName.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Subject name must contain letters only.");
        }

        // The new grade must be between 0 and 100.
        if (newGrade < 0 || newGrade > 100) {
            throw new IllegalArgumentException("Grade must be between 0 and 100.");
        }

        // Updates the grade and returns true if the subject was found.
        return studentRepository.updateSubjectGrade(
                studentId,
                subjectName.trim(),
                newGrade
        );
    }


    // ==================== DELETE SUBJECT FROM STUDENT ====================
    public boolean deleteSubjectFromStudent(int studentId, String subjectName) {

        // The student ID must be positive.
        if (studentId <= 0) {
            throw new IllegalArgumentException("Student ID must be greater than 0.");
        }

        // Makes sure the student exists before trying to delete a subject.
        Student student = studentRepository.findById(studentId);

        if (student == null) {
            throw new IllegalArgumentException("Student was not found.");
        }

        // The subject name cannot be null, empty, or spaces only.
        if (subjectName == null || subjectName.trim().isEmpty()) {
            throw new IllegalArgumentException("Subject name cannot be empty.");
        }

        // The subject name can contain only letters and spaces.
        if (!subjectName.matches("[a-zA-Z ]+")) {
            throw new IllegalArgumentException("Subject name must contain letters only.");
        }

        // Deletes the subject and returns true if it was found.
        return studentRepository.deleteSubjectFromStudent(
                studentId,
                subjectName.trim()
        );
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
    public Student updateStudent(int studentId, String newName, int newAge) {

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


//=========CHANGES THE STUDENTS EDITABLE INFO.=========
        student.setName(newName.trim());
        student.setAge(newAge);
        studentRepository.update(student);
        return student;          // Returns the updated student so the controller can display it.
    }
}