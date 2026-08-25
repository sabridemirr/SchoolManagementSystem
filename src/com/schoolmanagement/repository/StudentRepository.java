/*
 * Performs student and student-subject database operations using JDBC.
 *
 * Responsibilities:
 * - Opens database connections.
 * - Executes prepared SQL statements.
 * - Converts database rows into Student objects.
 * - Inserts, reads, updates and deletes student records.
 * - Manages subjects and their respective grades.
 *
 * This repository does not read console input and should not contain
 * user-interface decisions or business validation.
 */

package com.schoolmanagement.repository;

import com.schoolmanagement.database.DatabaseConnection;
import com.schoolmanagement.model.Student;

// JDBC classes used for database connections and parameterized SQL statements.
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentRepository {

    // ==================== ADD STUDENT ====================
    public void add(Student student) {

        String sql = """
                INSERT INTO students (student_id, name, age)
                VALUES (?, ?, ?)
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, student.getStudentId());
            statement.setString(2, student.getName());
            statement.setInt(3, student.getAge());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // ==================== ADD SUBJECT AND GRADE TO STUDENT ====================
    public void addSubjectToStudent(int studentId, String subjectName, double grade) {

        // Inserts one subject and its respective grade for one student.
        String sql = """
            INSERT INTO student_subjects (student_id, subject_name, grade)
            VALUES (?, ?, ?)
            """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, studentId);             // First ? is the student's ID.
            statement.setString(2, subjectName.trim()); // Second ? is the subject name.
            statement.setDouble(3, grade);              // Third ? is this subject's grade.

            statement.executeUpdate(); // Permanently inserts the row into H2.

        } catch (SQLException e) {
            throw new RuntimeException(
                    "The subject and grade could not be added.",
                    e
            );
        }
    }


    // ==================== CHECK WHETHER STUDENT HAS SUBJECT ====================
    public boolean studentHasSubject(int studentId, String subjectName) {

        // Counts matching subject rows for this specific student.
        String sql = """
            SELECT COUNT(*)
            FROM student_subjects
            WHERE student_id = ?
              AND LOWER(subject_name) = LOWER(?)
            """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, studentId);             // Searches for this student.
            statement.setString(2, subjectName.trim()); // Searches for this subject.

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; // True means the subject already exists.
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    "The student's subjects could not be checked.",
                    e
            );
        }
        return false;
    }


    // ==================== GET STUDENT SUBJECTS AND GRADES ====================
    public String findSubjectsByStudentId(int studentId) {

        // Builds readable output using rows retrieved from the database.
        StringBuilder subjectDetails = new StringBuilder();

        String sql = """
            SELECT subject_name, grade
            FROM student_subjects
            WHERE student_id = ?
            ORDER BY subject_name
            """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String subjectName = resultSet.getString("subject_name");
                    double grade = resultSet.getDouble("grade");
                    subjectDetails
                            .append(subjectName)
                            .append(": ")
                            .append(grade)
                            .append(System.lineSeparator());
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(
                    "The student's subjects and grades could not be loaded.",
                    e
            );
        }
        return subjectDetails.toString();
    }


    // ==================== UPDATE SUBJECT GRADE ====================
    public boolean updateSubjectGrade(
            int studentId,
            String subjectName,
            double newGrade
    ) {
        String sql = """
            UPDATE student_subjects
            SET grade = ?
            WHERE student_id = ?
              AND LOWER(subject_name) = LOWER(?)
            """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setDouble(1, newGrade);             // New grade for this subject.
            statement.setInt(2, studentId);               // Student who takes the subject.
            statement.setString(3, subjectName.trim());   // Subject whose grade will change.

            int updatedRows = statement.executeUpdate();
            return updatedRows > 0; // True means a matching subject was updated.

        } catch (SQLException e) {
            throw new RuntimeException("The subject grade could not be updated.", e);
        }
    }


    // ==================== DELETE SUBJECT FROM STUDENT ====================
    public boolean deleteSubjectFromStudent(
            int studentId,
            String subjectName
    ) {
        String sql = """
            DELETE FROM student_subjects
            WHERE student_id = ?
              AND LOWER(subject_name) = LOWER(?)
            """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.setInt(1, studentId);               // Student who takes the subject.
            statement.setString(2, subjectName.trim());   // Subject that will be removed.

            int deletedRows = statement.executeUpdate();
            return deletedRows > 0; // True means a matching subject was deleted.

        } catch (SQLException e) {
            throw new RuntimeException("The subject could not be removed.", e);
        }
    }


    // ==================== GET ALL STUDENTS ====================
    public ArrayList<Student> findAll() {
        ArrayList<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Student student = new Student(
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("student_id")
                );
                students.add(student);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return students;
    }

    // ==================== FIND STUDENT BY ID ====================
    public Student findById(int studentId) {

        String sql = "SELECT * FROM students WHERE student_id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, studentId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new Student(
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("student_id")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    // ==================== FIND STUDENT BY NAME ====================
    public Student findByName(String name) {

        String sql = "SELECT * FROM students WHERE LOWER(name) = LOWER(?)";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new Student(
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("student_id")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    // ==================== CHECK WHETHER ID EXISTS ====================
    public boolean existsById(int studentId) {

        return findById(studentId) != null;
    }


    // ==================== DELETE STUDENT ====================
    public boolean delete(Student student) {

        String sql = "DELETE FROM students WHERE student_id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, student.getStudentId());

            int deletedRows = statement.executeUpdate();

            return deletedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // ==================== UPDATE STUDENT ====================
    public boolean update(Student student) {

        String sql = """
            UPDATE students
            SET name = ?, age = ?
            WHERE student_id = ?
            """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, student.getName());
            statement.setInt(2, student.getAge());
            statement.setInt(3, student.getStudentId());

            int updatedRows = statement.executeUpdate();

            return updatedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    // ==================== CHECK WHETHER REPOSITORY IS EMPTY ====================
    public boolean isEmpty() {

        String sql = "SELECT COUNT(*) FROM students";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            if (resultSet.next()) {
                return resultSet.getInt(1) == 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }
}





/*package com.schoolmanagement.repository;

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
*/


