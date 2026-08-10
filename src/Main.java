import java.util.Scanner;
import java.util.ArrayList;

public class Main{
    public static void main(String[]args){
        Scanner scanner = new Scanner(System.in);

        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Teacher> teachers = new ArrayList<>();
        ArrayList<Parent> parents = new ArrayList<>();

       int choice;

do {
    System.out.println("~~~~~Welcome To The School Management System~~~~~");
    System.out.println("1.Student Menu");
    System.out.println("2.Teacher Menu:");
    System.out.println("3.Parent Menu");
    System.out.println("4.Exit");
    System.out.println("Please Select an Option:");
    choice = scanner.nextInt();

    switch (choice) {

        case 1:

            int studentChoice;

            do {
                System.out.println("~~~~~Welcome To The Student Management System~~~~~");
                System.out.println("1.Add Student:");
                System.out.println("2.Show Students:");
                System.out.println("3.Search Student");
                System.out.println("4.Delete Student");
                System.out.println("5.Update Student");
                System.out.println("6.Back");
                System.out.println("Please Select an Option:");
                studentChoice = scanner.nextInt();
                scanner.nextLine();

                switch (studentChoice) {

                    case 1: {
                        System.out.println("You Selected Add Student.");

                        System.out.println("Enter name:");
                        String name = scanner.nextLine();

                        System.out.println("Enter age:");
                        int age = scanner.nextInt();

                        System.out.println("Enter StudentId:");
                        int studentId = scanner.nextInt();

                        System.out.println("Enter Grade:");
                        double grade = scanner.nextDouble();
                        scanner.nextLine();

                        Student s1 = new Student(name, age, studentId, grade);
                        students.add(s1);
                        break;
                    }

                    case 2: {
                        System.out.println("You Selected Show Student.");
                        for (int i = 0; i < students.size(); i++)
                            students.get(i).displayStudent();
                        break;
                    }

                    case 3: {
                        System.out.println("You Selected Search Students.");
                        System.out.println("Enter a valid Student ID:");
                        int searchId = scanner.nextInt();
                        scanner.nextLine();

                        boolean found = false;
                        for (int i = 0; i < students.size(); i++) {
                            if (students.get(i).getStudentId() == searchId) {
                                students.get(i).displayStudent();
                                found = true;
                                break;
                            }
                        }
                        if (found != true)
                            System.out.println("Not found");
                        break;
                    }

                    case 4: {
                        System.out.println("Select a Student to Delete:");
                        int deleteId = scanner.nextInt();
                        scanner.nextLine();

                        boolean deleted = false;
                        for (int i = 0; i < students.size(); i++) {
                            if (students.get(i).getStudentId() == deleteId) {
                                students.remove(i);
                                deleted = true;
                                break;
                            }
                        }
                        if (deleted != true) {
                            System.out.println("Not deleted");
                        }
                        break; // cuts out the switch
                    }

                    case 5: {
                        System.out.println("Enter a StudentID to Update");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();

                        boolean updated = false;

                        for (int i = 0; i < students.size(); i++) {

                            if (students.get(i).getStudentId() == updateId) {

                                System.out.println("Enter name:");
                                String name = scanner.nextLine();
                                students.get(i).setName(name);

                                System.out.println("Enter age:");
                                int age = scanner.nextInt();
                                students.get(i).setAge(age);

                                System.out.println("Enter Grade:");
                                double grade = scanner.nextDouble();
                                students.get(i).setGrade(grade);
                                scanner.nextLine();

                                updated = true;

                                System.out.println("You have successfully completed the update.");

                                break;
                            }
                        }

                        if (updated != true) {
                            System.out.println("Not updated");
                        }

                        break;
                    }

                    case 6: {
                        System.out.println("Returning back to Main Menu.");
                        break;
                    }

                    default: {
                        System.out.println("You have selected an invalid option.");
                        break;
                    }
                }

                    if (studentChoice != 6) {
                        System.out.println("Press Enter to return to the Student Menu.");
                        scanner.nextLine();
                    }

            } while (studentChoice != 6);
            break;

        case 2:
            int teacherChoice;

            do {
                System.out.println("~~~~~Welcome To The Teacher Management System~~~~~");
                System.out.println("1.Add Teacher:");
                System.out.println("2.Show Teacher:");
                System.out.println("3.Search Teacher");
                System.out.println("4.Delete Teacher");
                System.out.println("5.Update Teacher");
                System.out.println("6.Back");
                System.out.println("Please Select an Option:");
                teacherChoice = scanner.nextInt();
                scanner.nextLine();

                switch (teacherChoice) {

                    case 1: {
                        System.out.println("You Selected Add Teacher.");

                        System.out.println("Enter name:");
                        String name = scanner.nextLine();

                        System.out.println("Enter age:");
                        int age = scanner.nextInt();

                        System.out.println("Enter TeacherId:");
                        int teacherId = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Enter Subject:");
                        String subject = scanner.nextLine();

                        Teacher t1 = new Teacher(name, age, teacherId, subject);
                        teachers.add(t1);
                        break;
                    }

                    case 2: {
                        System.out.println("You Selected Show Teacher.");
                        for (int i = 0; i < teachers.size(); i++) {
                            teachers.get(i).displayTeacher();
                        }
                        break;
                    }

                    case 3: {
                        System.out.println("You Selected Search Teachers.");
                        System.out.println("Enter a valid Teacher ID:");
                        int searchId = scanner.nextInt();
                        scanner.nextLine();

                        boolean found = false;
                        for (int i = 0; i < teachers.size(); i++) {
                            if (teachers.get(i).getTeacherId() == searchId) {
                                teachers.get(i).displayTeacher();
                                found = true;
                                break;
                            }
                        }
                        if (found != true)
                            System.out.println("Not found");
                        break;
                    }

                    case 4: {
                        System.out.println("Select a Teacher to Delete:");
                        int deleteId = scanner.nextInt();
                        scanner.nextLine();

                        boolean deleted = false;
                        for (int i = 0; i < teachers.size(); i++) {
                            if (teachers.get(i).getTeacherId() == deleteId) {
                                teachers.remove(i);
                                deleted = true;
                                break;
                            }
                        }
                        if (deleted != true) {
                            System.out.println("Not deleted");
                        }
                        break;
                    }

                    case 5: {
                        System.out.println("Enter a TeacherID to Update");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();

                        boolean updated = false;

                        for (int i = 0; i < teachers.size(); i++) {

                            if (teachers.get(i).getTeacherId() == updateId) {

                                System.out.println("Enter name:");
                                String name = scanner.nextLine();
                                teachers.get(i).setName(name);

                                System.out.println("Enter age:");
                                int age = scanner.nextInt();
                                teachers.get(i).setAge(age);

                                scanner.nextLine();

                                System.out.println("Enter Subject:");
                                String subject = scanner.nextLine();
                                teachers.get(i).setSubject(subject);

                                updated = true;

                                System.out.println("You have successfully completed the update.");

                                break;
                            }
                        }

                        if (updated != true) {
                            System.out.println("Not updated");
                        }

                        break;
                    }

                    case 6: {
                        System.out.println("Returning back to Main Menu.");
                        break;
                    }

                    default: {
                        System.out.println("You have selected an invalid option.");
                        break;
                    }
                }

                    if (teacherChoice != 6) {
                        System.out.println("Press Enter to return to the Teacher Menu.");
                        scanner.nextLine();
                }

            } while (teacherChoice != 6);
            break;

        case 3:
            int parentChoice;

            do {
                System.out.println("~~~~~Welcome To The Parent Management System~~~~~");
                System.out.println("1.Add Parent:");
                System.out.println("2.Show Parent:");
                System.out.println("3.Search Parent");
                System.out.println("4.Delete Parent");
                System.out.println("5.Update Parent");
                System.out.println("6.Back");
                System.out.println("Please Select an Option:");
                parentChoice = scanner.nextInt();
                scanner.nextLine();

                switch (parentChoice) {

                    case 1: {
                        System.out.println("You Selected Add Parent.");

                        System.out.println("Enter name:");
                        String name = scanner.nextLine();

                        System.out.println("Enter age:");
                        int age = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Enter Phone Number:");
                        String phoneNumber = scanner.nextLine();

                        System.out.println("Enter Email:");
                        String email = scanner.nextLine();

                        System.out.println("Enter ParentID:");
                        int parentId = scanner.nextInt();

                        System.out.println("Enter StudentID:");
                        int studentId = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Enter Relationship:");
                        String relationship = scanner.nextLine();

                        Parent p1 = new Parent(name, age, phoneNumber, email, parentId, studentId, relationship);
                        parents.add(p1);
                        break;
                    }


                    case 2: {
                        System.out.println("You Selected Show Parent.");
                        for (int i = 0; i < parents.size(); i++) {
                            parents.get(i).displayParent();
                        }
                        break;
                    }


                    case 3: {
                        System.out.println("You Selected Search Parents.");
                        System.out.println("Enter a valid Parent ID:");
                        int searchId = scanner.nextInt();
                        scanner.nextLine();

                        boolean found = false;
                        for (int i = 0; i < parents.size(); i++) {
                            if (parents.get(i).getParentId() == searchId) {
                                parents.get(i).displayParent();
                                found = true;
                                break;
                            }
                        }
                        if (found != true)
                            System.out.println("Not found");
                        break;
                    }

                    case 4: {
                        System.out.println("Select a Parent to Delete:");
                        int deleteId = scanner.nextInt();
                        scanner.nextLine();

                        boolean deleted = false;
                        for (int i = 0; i < parents.size(); i++) {
                            if (parents.get(i).getParentId() == deleteId) {
                                parents.remove(i);
                                deleted = true;
                                break;
                            }
                        }
                        if (deleted != true) {
                            System.out.println("Not deleted");
                        }
                        break;
                    }

                    case 5: {
                        System.out.println("Enter a ParentID to Update");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();

                        boolean updated = false;

                        for (int i = 0; i < parents.size(); i++) {

                            if (parents.get(i).getParentId() == updateId) {

                                System.out.println("Enter name:");
                                String name = scanner.nextLine();
                                parents.get(i).setName(name);

                                System.out.println("Enter age:");
                                int age = scanner.nextInt();
                                parents.get(i).setAge(age);

                                scanner.nextLine();

                                System.out.println("Enter Phone Number:");
                                String phoneNumber = scanner.nextLine();
                                parents.get(i).setPhoneNumber(phoneNumber);

                                System.out.println("Enter Email");
                                String email = scanner.nextLine();
                                parents.get(i).setEmail(email);

                                System.out.println("Enter Student ID:");
                                int studentId = scanner.nextInt();
                                parents.get(i).setStudentId(studentId);

                                scanner.nextLine();

                                System.out.println("Enter RelationShip:");
                                String relationship = scanner.nextLine();
                                parents.get(i).setRelationship(relationship);
                                updated = true;

                                System.out.println("You have successfully completed the update.");

                                break;
                            }
                        }

                        if (updated != true) {
                            System.out.println("Not updated");
                        }

                        break;
                    }


                    case 6: {
                        System.out.println("Returning back to Main Menu.");
                        break;
                    }

                    default: {
                        System.out.println("You have selected an invalid option.");
                        break;
                    }
                }

                    if (parentChoice != 6) {
                        System.out.println("Press Enter to return to the Parent Menu.");
                        scanner.nextLine();
                    }

            } while (parentChoice != 6);
            break;

        case 4:
            System.out.println("You Have Exited The System.");
            break;

        default:
            System.out.println("Invalid Choice.");
    }

} while(choice != 4);
scanner.close();
    }
}