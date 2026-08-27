package com.schoolmanagement.repository;

import com.schoolmanagement.database.DatabaseConnection;
import com.schoolmanagement.model.Parent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ParentRepository {

    // ==================== ADD PARENT ====================
    public void add(Parent parent) {

        String sql = """
                INSERT INTO parents
                (parent_id, name, age, email, phone_number, student_id, relationship)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, parent.getParentId());
            statement.setString(2, parent.getName());
            statement.setInt(3, parent.getAge());
            statement.setString(4, parent.getEmail());
            statement.setString(5, parent.getPhoneNumber());
            statement.setInt(6, parent.getStudentId());
            statement.setString(7, parent.getRelationship());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // ==================== GET ALL PARENTS ====================
    public ArrayList<Parent> findAll() {

        ArrayList<Parent> parents = new ArrayList<>();

        String sql = "SELECT * FROM parents";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {

            while (resultSet.next()) {

                Parent parent = new Parent(
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("parent_id"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"),
                        resultSet.getInt("student_id"),
                        resultSet.getString("relationship")
                );

                parents.add(parent);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return parents;
    }


    // ==================== FIND PARENT BY ID ====================
    public Parent findById(int parentId) {

        String sql = "SELECT * FROM parents WHERE parent_id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, parentId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new Parent(
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("parent_id"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"),
                        resultSet.getInt("student_id"),
                        resultSet.getString("relationship")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    // ==================== FIND PARENT BY NAME ====================
    public Parent findByName(String name) {

        String sql = "SELECT * FROM parents WHERE LOWER(name) = LOWER(?)";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, name);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {

                return new Parent(
                        resultSet.getString("name"),
                        resultSet.getInt("age"),
                        resultSet.getInt("parent_id"),
                        resultSet.getString("email"),
                        resultSet.getString("phone_number"),
                        resultSet.getInt("student_id"),
                        resultSet.getString("relationship")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    // ==================== CHECK WHETHER ID EXISTS ====================
    public boolean existsById(int parentId) {
        return findById(parentId) != null;
    }


    // ==================== DELETE PARENT ====================
    public boolean delete(Parent parent) {

        String sql = "DELETE FROM parents WHERE parent_id = ?";

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setInt(1, parent.getParentId());

            int deletedRows = statement.executeUpdate();

            return deletedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // ==================== UPDATE PARENT ====================
    public boolean update(Parent parent) {

        String sql = """
            UPDATE parents
            SET name = ?, age = ?, phone_number = ?, email = ?
            WHERE parent_id = ?
            """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {

            statement.setString(1, parent.getName());
            statement.setInt(2, parent.getAge());
            statement.setString(3, parent.getPhoneNumber());
            statement.setString(4, parent.getEmail());
            statement.setInt(5, parent.getParentId());

            int updatedRows = statement.executeUpdate();

            return updatedRows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }


    // ==================== CHECK WHETHER REPOSITORY IS EMPTY ====================
    public boolean isEmpty() {

        String sql = "SELECT COUNT(*) FROM parents";

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

import com.schoolmanagement.model.Parent;

import java.util.ArrayList;

public class ParentRepository {

    // ==================== PARENT STORAGE ====================
    private final ArrayList<Parent> parents = new ArrayList<>(); // The repository owns and stores the parent objects.
// This creates the repository's internal parent list

    // ==================== ADD PARENT ====================
    public void add(Parent parent) {
        parents.add(parent); // Adds the provided Parent object to storage.
    }

    // ==================== GET ALL PARENTS ====================
    public ArrayList<Parent> findAll() {
        return new ArrayList<>(parents); // Returns a copy so outside code cannot directly damage the stored list. This way the repos list structure is protected
    }

    // ==================== FIND PARENT BY ID ==================
    public Parent findById(int parentId) {
        for (Parent parent : parents) { // Checks every stored parent.
            if (parent.getParentId() == parentId) { // Compares the existing ID with the requested ID.
                return parent; // Immediately returns the matching Parent object.
            }
        }
        return null; // No Parent had the requested ID.
    }

    // ==================== FIND PARENT BY NAME ====================
    public Parent findByName(String name) {
        for (Parent parent : parents) { // Checks every stored parent.
            if (parent.getName().equalsIgnoreCase(name)) { // Ignores uppercase and lowercase differences.
                return parent; // Returns the first parent with the matching name.
            }
        }
        return null; // No parent had the requested name.
    }

    // ==================== CHECK WHETHER ID EXISTS ====================
    public boolean existsById(int parentId) {
        return findById(parentId) != null; // Returns true when findById finds a parent.
    }

    // ==================== DELETE PARENT ====================
    public boolean delete(Parent parent) {
        return parents.remove(parent); // Removes the parent and reports whether removal succeeded.
    }

    // ==================== CHECK WHETHER REPOSITORY IS EMPTY ====================
    public boolean isEmpty() {
        return parents.isEmpty(); // Returns true when no parents are stored.
    }
}
*/