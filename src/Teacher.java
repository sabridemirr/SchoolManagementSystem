class Teacher {

String name;
int age;
int teacherId;
String subject;

Teacher(String name, int age, int teacherId, String subject){
    this.name = name;
    this.age = age;
    this.teacherId = teacherId;
    this.subject = subject;
}

void displayTeacher(){
    System.out.println("Name: " + name);
    System.out.println("Age: " + age);
    System.out.println("Teacher ID: " + teacherId);
    System.out.println("Subject: " + subject);
}
}
