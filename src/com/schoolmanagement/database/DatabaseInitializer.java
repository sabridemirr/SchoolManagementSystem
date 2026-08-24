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
                    age INT
                )
                """;


        // Removes the old general grade because grades now belong to subjects.
        String removeOldStudentGradeColumn = """
                ALTER TABLE students
                DROP COLUMN IF EXISTS grade
                """;

        // ==================== STUDENT SUBJECTS TABLE ====================
        // Stores every subject taken by a student and that student's grade for it.
        String studentSubjectsTable = """
                CREATE TABLE IF NOT EXISTS student_subjects (
                    student_id INT NOT NULL,
                    subject_name VARCHAR(100) NOT NULL,
                    grade DOUBLE NOT NULL,
                
                    PRIMARY KEY (student_id, subject_name),
                
                    FOREIGN KEY (student_id)
                        REFERENCES students(student_id)
                        ON DELETE CASCADE
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

        // Adds the grade column when student_subjects already exists from an older run.
        String addSubjectGradeColumn = """
                ALTER TABLE student_subjects
                ADD COLUMN IF NOT EXISTS grade DOUBLE DEFAULT 0 NOT NULL
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
            statement.execute(studentSubjectsTable);
            statement.execute(addSubjectGradeColumn);
            statement.execute(teacherTable);
            statement.execute(parentTable);

            System.out.println("Database tables are ready.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}