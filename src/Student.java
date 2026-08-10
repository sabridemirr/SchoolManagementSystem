class Student {
    String name;
    int age;
    int studentId;
    double grade;

    Student(String name, int age, int studentid, double grade) {
        this.name = name;
        this.age = age;
        this.studentId = studentid;
        this.grade = grade;

    }
    void displayStudent(){
        System.out.println("Name:" + name);
        System.out.println("Age:"+ age);
        System.out.println("StudentID:" + studentId);
        System.out.println("Grade:" + grade);
    }
}