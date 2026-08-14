import java.util.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Student> students = new ArrayList<>();
        ArrayList<Teacher> teachers = new ArrayList<>();
        ArrayList<Parent> parents = new ArrayList<>();

        int choice;

        do {
            System.out.println("~~~~~Welcome To The School Management System~~~~~");
            System.out.println("Please Select an Option:");
            System.out.println("1.Admin Menu:");
            System.out.println("2.Student Menu:");
            System.out.println("3.Teacher Menu:");
            System.out.println("4.Parent Menu");
            System.out.println("5.Exit");

            try {
                choice = scanner.nextInt();  // this will work unless you enter a variable
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Invalid Choice. Please Enter a Number from 1 to 5");  // catches the error and deals with it.
                scanner.nextLine();
                choice = 0;
            }

            switch (choice) {

                case 1:
                    AdminManagement.adminMenu(scanner, students, teachers, parents);
                    break;

                case 2:
                    StudentManagement.studentMenu(scanner, students);
                    break;

                case 3:
                    TeacherManagement.teacherMenu(scanner, teachers);
                    break;

                case 4:
                    ParentManagement.parentMenu(scanner, parents);
                    break;

                case 5:
                    System.out.println("You Have Exited The System.");
                    break;

                default:
                    System.out.println("Invalid Choice.");
            }

        } while (choice != 5);
        scanner.close();
    }
}