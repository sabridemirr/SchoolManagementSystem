package com.schoolmanagement.repository;

import com.schoolmanagement.database.DatabaseConnection;
import com.schoolmanagement.model.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class TeacherRepository {

    // ==================== ADD TEACHER ====================
    public void add(Teacher teacher) {

        String sql = """
                INSERT INTO teachers (teacher_id, name, age, subject)
                VALUES (?, ?, ?, ?)
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, teacher.getTeacherId());
            statement.setString(2, teacher.getName());
            statement.setInt(3, teacher.getAge());
            statement.setString(4, teacher.getSubject());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // ==================== GET ALL TEACHERS ====================
    public ArrayList<Teacher> findAll() {

        ArrayList<Teacher> teachers = new ArrayList<>();

        String sql = "SELECT * FROM teachers";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Teacher teacher = new Teacher(
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("teacher_id"),
                        resultSet.getString("subject")
                );

                teachers.add(teacher);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return teachers;
    }


    // ==================== FIND TEACHER BY ID ====================
    public Teacher findById(int teacherId) {

        String sql = "SELECT * FROM teachers WHERE teacher_id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, teacherId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new Teacher(
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("teacher_id"),
                        resultSet.getString("subject")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    // ==================== FIND TEACHER BY NAME ====================
    public Teacher findByName(String name) {

        String sql = "SELECT * FROM teachers WHERE LOWER(name) = LOWER(?)";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new Teacher(
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("teacher_id"),
                        resultSet.getString("subject")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    // ==================== CHECK WHETHER ID EXISTS ====================
    public boolean existsById(int teacherId) {
        return findById(teacherId) != null;
    }


    // ==================== DELETE TEACHER ====================
    public boolean delete(Teacher teacher) {

        String sql = "DELETE FROM teachers WHERE teacher_id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, teacher.getTeacherId());

            int deletedRows = statement.executeUpdate();

            return deletedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;

    }

    // ==================== UPDATE TEACHER ====================
    public boolean update(Teacher teacher) {

        String sql = """
            UPDATE teachers
            SET name = ?, age = ?
            WHERE teacher_id = ?
            """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, teacher.getName());
            statement.setInt(2, teacher.getAge());
            statement.setInt(3, teacher.getTeacherId());

            int updatedRows = statement.executeUpdate();

            return updatedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    // ==================== CHECK WHETHER REPOSITORY IS EMPTY ====================
    public boolean isEmpty() {

        String sql = "SELECT COUNT(*) FROM teachers";

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
*/