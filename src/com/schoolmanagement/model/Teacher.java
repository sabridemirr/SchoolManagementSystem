package com.schoolmanagement.model;

public class Teacher {

private String name;
private int age;
private int teacherId;
private String subject;

public Teacher(String name, int age, int teacherId, String subject){
    this.name = name;
    this.age = age;
    this.teacherId = teacherId;
    this.subject = subject;
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

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSubject() {
        return subject;
    }

    public void displayTeacher(){
    System.out.println("Name: " + name);
    System.out.println("Age: " + age);
    System.out.println("Teacher ID: " + teacherId);
    System.out.println("Subject: " + subject);
}
}
