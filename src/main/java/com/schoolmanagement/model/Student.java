/*
 * Represents one student in the school management system.
 * This model stores only the student's basic information.
 * Subjects and grades are stored separately in the student_subjects
 * database table because one student can take many subjects.
 */

package com.schoolmanagement.model;

import jakarta.persistence.Column; // Connects a Java field to a database column.
import jakarta.persistence.Entity; // Marks this class as a database entity.
import jakarta.persistence.Id;     // Marks the field containing the primary key.
import jakarta.persistence.Table;  // Connects this class to a specific database table.

@Entity // Tells JPA/Hibernate that Student represents data stored in the database.
@Table(name = "students") // Connects this class to the existing "students" table.

public class Student {

    @Column(name = "name", nullable = false, length = 100) // Maps this field to NAME and prevents null database values.
    private String name;

    //- Explicit mapping makes the relationship between Java and the database easy to understand.

    @Column(name = "age", nullable = false) // Maps this field to the AGE database column.
    private int age;

    @Id // Marks studentId as the primary key that uniquely identifies each student.
    @Column(name = "student_id") // Connects studentId to the student_id database column.
    private int studentId;//  Cannot be final because Hibernate must be able to load its value from the database.

    /*  Creates a Student object using information received from the service or information retrieved from the database. */

    /* Required by JPA/Hibernate.
     * Hibernate uses this constructor when rebuilding a Student from database data. */
    protected Student() {   // We did it protected so Hibernate cn access it.
    }

    public Student(String name, int age, int studentId) {
        this.name = name;
        this.age = age;
        this.studentId = studentId;
    }

    public void setName(String name){   // Changes the student's name after the service validates the new value.
        this.name = name;   // Stores the student's new name after validation.
    }

    public String getName() {
        return name;  // Returns the student's current name.
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }

    public int getStudentId() {
        return studentId;
    }

    public void displayStudent(){  // Displays the student's basic information in the console. & // Subjects are displayed separately because they come from another table.
        System.out.println("Name:" + name);
        System.out.println("Age:"+ age);
        System.out.println("StudentID:" + studentId);
    }
}