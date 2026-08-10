class Parent {

    String name;
    int age;
    String email;
    String phoneNumber;
    int parentId;
    int studentId;
    String relationship;

    Parent(String name, int age, String phoneNumber, String email, int parentId, int studentId, String relationship){
        this.name = name;
        this.age = age;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.parentId = parentId;
        this.studentId = studentId;
        this.relationship = relationship;
    }

    void displayParent(){
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Phone Number: " + phoneNumber);
        System.out.println("Email: " + email);
        System.out.println("Parent ID: " + parentId);
        System.out.println("Student ID: " + studentId);
        System.out.println("Relationship: " + relationship);
    }
}

