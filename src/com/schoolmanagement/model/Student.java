package com.schoolmanagement.model;

public class Student {
    private String name;
    private int age;
    private final int studentId;
    private double grade;

    public Student(String name, int age, int studentId, double grade) {
        this.name = name;
        this.age = age;
        this.studentId = studentId;
        this.grade = grade;
    }

    public void setName(String name){
        this.name = name;
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

    public void setGrade(double grade) {
        this.grade = grade;
    }

    public double getGrade() {
        return grade;
    }

    public int getStudentId() {
        return studentId;
    }

    public void displayStudent(){
        System.out.println("Name:" + name);
        System.out.println("Age:"+ age);
        System.out.println("StudentID:" + studentId);
        System.out.println("Grade:" + grade);
    }
}