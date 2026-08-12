import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;
import java.util.Scanner;

public class StudentManagement {
    public static void studentMenu(
        Scanner scanner,
        ArrayList<Student> students){

                int studentChoice;

                do {
                    System.out.println("~~~~~Welcome To The Student Management System~~~~~");
                    System.out.println("Please Select an Option:");
                    System.out.println("1.Add Student:");
                    System.out.println("2.Show Students:");
                    System.out.println("3.Search Student");
                    System.out.println("4.Delete Student");
                    System.out.println("5.Update Student");
                    System.out.println("6.Back");

                    try {
                        studentChoice = scanner.nextInt();    // here try will cover the whole switch block
                        scanner.nextLine();

                        switch (studentChoice) {

                            case 1: {
                                System.out.println("You Selected Add Student.");

                                String name;
                                do {

                                    System.out.println("Enter name:");
                                    name = scanner.nextLine();

                                    if (name.trim().isEmpty()) {  // trim removes spaces from the beginning and the end./ .isEmpty asks if the string empty
                                        System.out.println("Name cannot be empty please enter a name");
                                    }
                                    else if (!name.matches("[a-zA-Z ]+")) { // checks that the name contains only letters and spaces
                                        System.out.println("Invalid name. Please use letters only.");
                                    }


                                } while(name.trim().isEmpty() || !name.matches("[a-zA-Z ]+"));  // will keep asking for a name to be entered




                                // AGE CODE
                                int age = 0;
                                boolean validAge;

                                do {
                                    validAge = true;
                                    System.out.println("Enter age:");

                                    try {

                                        age = scanner.nextInt();
                                        if (age < 4 || age > 61) {  // age should be greater than 4 or less than 60
                                            System.out.println("Invalid age. Input a valid age.");
                                            validAge = false;
                                        }

                                    } catch (Exception e) {
                                        System.out.println("You cannot enter a variable. Please enter a Number");
                                        scanner.nextLine();
                                        validAge = false;
                                    }

                                } while (!validAge);







                                // STUDENT ID CODE
                                System.out.println("Enter a StudentId:");
                                int studentId = 0; // stores the student id given by the user
                                boolean duplicateId; // keeps track if the student id already exists or not
                                boolean validId;  // keeps track if you entered a valid id or not.

                                do {
                                    duplicateId = false; // to start assuming the id is available
                                    validId = true;  // assume input is valid.

                                    try {
                                        studentId = scanner.nextInt(); // read student id from the user but if we write letters it will throw an exception and break.

                                        if (studentId <= 0) {
                                        System.out.println("Student Id cannot be negative.");
                                        System.out.println("Enter a valid Student Id.");
                                        continue;  // it means stop the current round of loop and directly start the next round.
                                    }

                                    for (int i = 0; i < students.size(); i++) {   // goes through all students
                                        if (students.get(i).getStudentId() == studentId) {   // checks for the same id
                                            duplicateId = true;  // same id was found
                                            break;     // to stop the search
                                        }
                                    }

                                    if (duplicateId) {    // if the id is repeated
                                        System.out.println("The Student Id you have entered already exists.");
                                        System.out.println("Enter a Different Student Id: ");
                                    }

                                } catch(Exception e){
                                    System.out.println("Student ID must only contain numbers.");
                                    System.out.println("Enter a valid Student Id");
                                    scanner.nextLine();
                                    validId = false;
                                }

                                } while (!validId || duplicateId); //  repeats until a unique id is entered






                                //GRADE CODE.
                                double grade = 0;
                                boolean validGrade;

                                do {
                                    validGrade = true;
                                    System.out.println("Enter Grade:");

                                    try {

                                        grade = scanner.nextDouble();
                                        if (grade < 0 || grade > 100) {  // age should be greater than 4 or less than 60
                                            System.out.println("Invalid grade. Grade must be between 0 to 100.");
                                            validGrade = false;
                                        }

                                    } catch (Exception e) {
                                        System.out.println("You cannot enter a variable. Please enter a Number");
                                        scanner.nextLine();
                                        validGrade = false;
                                    }

                                } while (!validGrade);
                                scanner.nextLine(); // clears the Enter left behind by nextDouble()


                                Student s1 = new Student(name, age, studentId, grade);
                                students.add(s1);

                                System.out.println("You Have Succesfully Added The Student.");
                                break;
                            }




                            // Show case
                            case 2: {
                                System.out.println("You Selected Show Student.");
                                for (int i = 0; i < students.size(); i++)
                                    students.get(i).displayStudent();
                                break;
                            }



                            // Search case
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



                            // Delete case
                            case 4: {
                                System.out.println("Delete Student By:");
                                System.out.println("1. Student ID");
                                System.out.println("2. Name");

                                int deleteChoice = scanner.nextInt(); // stores whether user wants ID or name
                                scanner.nextLine();                    // clears leftover Enter

                                boolean found = false;                 // checks if the student exists
                                boolean deleted = false;



                                // To delete using id
                                if (deleteChoice == 1) {

                                    System.out.println("Enter Student ID:");
                                    int deleteId = scanner.nextInt(); // reads the ID
                                    scanner.nextLine();

                                    for (int i = 0; i < students.size(); i++) {

                                        if (students.get(i).getStudentId() == deleteId) {
                                            found = true;

                                            students.get(i).displayStudent();

                                            System.out.println("Are you sure you want to delete this student?");
                                            System.out.println("1. Yes");
                                            System.out.println("2. No");

                                            int confirm = scanner.nextInt();
                                            scanner.nextLine();

                                            if (confirm == 1) {
                                                students.remove(i);
                                                deleted = true;
                                                System.out.println("Student deleted successfully.");
                                            } else {
                                                System.out.println("Delete cancelled.");
                                            }

                                            break;
                                        }
                                    }
                                }


                                // To Search by name
                                else if (deleteChoice == 2) {

                                    System.out.println("Enter Student Name:");
                                    String deleteName = scanner.nextLine(); // reads the name

                                    for (int i = 0; i < students.size(); i++) {

                                        if (students.get(i).getName().equalsIgnoreCase(deleteName)) {    // it compares text regardless if it is capital or not
                                            found = true;

                                            students.get(i).displayStudent();

                                            System.out.println("Are you sure you want to delete this student?");
                                            System.out.println("1. Yes");
                                            System.out.println("2. No");

                                            int confirm = scanner.nextInt();
                                            scanner.nextLine();

                                            if (confirm == 1) {
                                                students.remove(i);
                                                deleted = true;
                                                System.out.println("Student deleted successfully.");
                                            } else {
                                                System.out.println("Delete cancelled.");
                                            }

                                            break;
                                        }
                                    }
                                }
                                if (!found) {
                                    System.out.println("Student not Found");
                                }
                                break; // cuts out the switch
                            }






                            // Update case
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
                    } catch(Exception e){
                        System.out.println("Invalid Choice. Please Enter a Number from 1 to 6");  //  the catch is at the end because first we use try to test the code if that fails then the catch handles the error.
                        scanner.nextLine();
                        studentChoice = 0;
                    }

                    if (studentChoice != 6) {
                        System.out.println("Press Enter to return to the Student Menu.");
                        scanner.nextLine();
                    }
                } while (studentChoice != 6);

        }
    }

