package com.schoolmanagement.repository;

import com.schoolmanagement.database.DatabaseConnection;
import com.schoolmanagement.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class StudentRepository {

    // ==================== ADD STUDENT ====================
    public void add(Student student) {

        String sql = """
                INSERT INTO students (student_id, name, age, grade)
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, student.getStudentId());
            statement.setString(2, student.getName());
            statement.setInt(3, student.getAge());
            statement.setDouble(4, student.getGrade());
            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
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
                        resultSet.getInt("student_id"),
                        resultSet.getDouble("grade")
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
                        resultSet.getInt("student_id"),
                        resultSet.getDouble("grade")
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
                        resultSet.getInt("student_id"),
                        resultSet.getDouble("grade")
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
            SET name = ?, age = ?, grade = ?
            WHERE student_id = ?
            """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, student.getName());
            statement.setInt(2, student.getAge());
            statement.setDouble(3, student.getGrade());
            statement.setInt(4, student.getStudentId());

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


