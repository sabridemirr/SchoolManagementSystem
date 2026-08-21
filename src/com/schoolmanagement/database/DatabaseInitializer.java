package com.schoolmanagement.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseInitializer {

    public static void initialize() {

        // STUDENTS TABLE
        String studentTable = """
                CREATE TABLE IF NOT EXISTS students (
                    student_id INT PRIMARY KEY,
                    name VARCHAR(100),
                    age INT,
                    grade DOUBLE
                )
                """;

        // TEACHERS TABLE
        String teacherTable = """
                CREATE TABLE IF NOT EXISTS teachers (
                    teacher_id INT PRIMARY KEY,
                    name VARCHAR(100),
                    age INT,
                    subject VARCHAR(100)
                )
                """;

        // PARENTS TABLE
        String parentTable = """
                CREATE TABLE IF NOT EXISTS parents (
                    parent_id INT PRIMARY KEY,
                    name VARCHAR(100),
                    age INT,
                    email VARCHAR(100),
                    phone_number VARCHAR(30),
                    student_id INT,
                    relationship VARCHAR(50)
                )
                """;

        try (
                Connection connection = DatabaseConnection.getConnection();
                Statement statement = connection.createStatement()
        ) {

            statement.execute(studentTable);
            statement.execute(teacherTable);
            statement.execute(parentTable);

            System.out.println("Database tables are ready.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}