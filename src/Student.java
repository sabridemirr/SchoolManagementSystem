class Student {
    private String name;
    private int age;
    private int studentId;
    private double grade;

    Student(String name, int age, int studentid, double grade) {
        this.name = name;
        this.age = age;
        this.studentId = studentid;
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

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getStudentId() {
        return studentId;
    }

    void displayStudent(){
        System.out.println("Name:" + name);
        System.out.println("Age:"+ age);
        System.out.println("StudentID:" + studentId);
        System.out.println("Grade:" + grade);
    }
}