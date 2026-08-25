package com.schoolmanagement.model;
/*
 * Represents one student in the school management system.
 * This model stores only the student's basic information.
 * Subjects and grades are stored separately in the student_subjects
 * database table because one student can take many subjects.
 */

public class Student {
    private String name;
    private int age;
    private final int studentId;       // The ID uniquely identifies the student and cannot change after creation.

    /*
     * Creates a Student object using information received from the service
     * or information retrieved from the database.
     */

    public Student(String name, int age, int studentId) {
        this.name = name;
        this.age = age;
        this.studentId = studentId;
    }

    public void setName(String name){   // Changes the student's name after the service validates the new value.
        this.name = name;   // Returns the student's current name.
    }

    public String getName() {
        return name;
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