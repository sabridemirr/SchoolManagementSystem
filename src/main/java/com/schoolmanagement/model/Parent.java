package com.schoolmanagement.model;

public class Parent {

    private String name;
    private int age;
    private int parentId;
    private String email;
    private String phoneNumber;
    private int studentId;
    private String relationship;

    public Parent(String name, int age, int parentId, String email, String phoneNumber, int studentId, String relationship) {
        this.name = name;
        this.age = age;
        this.parentId = parentId;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.studentId = studentId;
        this.relationship = relationship;
    }

    public void setName(String name) {
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getRelationship() {
        return relationship;
    }

    public void displayParent() {
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Email: " + email);
        System.out.println("Parent ID: " + parentId);
        System.out.println("Student ID: " + studentId);
        System.out.println("Relationship: " + relationship);
    }
}

