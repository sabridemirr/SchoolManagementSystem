import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminManagement {
    public static void adminMenu(
            Scanner scanner,
            ArrayList<Student> students,
            ArrayList<Teacher> teachers,
            ArrayList<Parent> parents) {

        int adminChoice;

        do {
            System.out.println("~~~~~Welcome To The Admin Management System~~~~~");
            System.out.println("Please Select an Option:");

            System.out.println("1.Show Students:");
            System.out.println("2.Show Teachers:");
            System.out.println("3.Show Parents:");

            System.out.println("4.Search Student:");
            System.out.println("5.Search Teacher:");
            System.out.println("6.Search Parent:");

            System.out.println("7.Update Student:");
            System.out.println("8.Update Teacher:");
            System.out.println("9.Update Parent:");

            System.out.println("10.Delete Student:");
            System.out.println("11.Delete Teacher:");
            System.out.println("12.Delete Parent:");

            System.out.println("13.Back.");


            try {
                adminChoice = scanner.nextInt();    // here try will cover the whole switch block
                scanner.nextLine();

                switch (adminChoice) {

                    case 1: {
                        System.out.println("You Selected Show Student.");
                        for (int i = 0; i < students.size(); i++) {
                            students.get(i).displayStudent();
                        }
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
                        System.out.println("You Selected Show Parent.");
                        for (int i = 0; i < parents.size(); i++) {
                            parents.get(i).displayParent();
                        }
                        break;
                    }

                    case 4: {
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

                    case 5: {
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

                    case 6: {
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

                    case 7: {
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

                    case 8: {
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

                    case 9: {
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

                        case 10: {
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

                        case 11: {
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

                        case 12: {
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

                        case 13: {
                            System.out.println("Returning back to Main Menu.");
                            break;
                        }

                        default:
                            System.out.println("Invalid Choice.");


                }
            }catch(InputMismatchException e){  // a runtime exception thrown by the Scanner class when the data entered does not match the expected data type
                    System.out.println("Invalid Choice. Please Enter a Number from 1 to 13");  //  the catch is at the end because first we use try to test the code if that fails then the catch handles the error.
                    scanner.nextLine();
                    adminChoice = 0;
                }

                if (adminChoice != 13) {
                    System.out.println("Press Enter to return to the Admin Menu.");
                    scanner.nextLine();
                }

            } while (adminChoice != 13) ;
        }
    }






