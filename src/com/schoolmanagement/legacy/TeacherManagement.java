package com.schoolmanagement.legacy;

import com.schoolmanagement.model.Teacher;

import java.util.ArrayList;
import java.util.Scanner;

public class TeacherManagement {
    public static void teacherMenu(
            Scanner scanner,
            ArrayList<Teacher> teachers) {


        int teacherChoice;

        do {
            System.out.println("~~~~~Welcome To The Teacher Management System~~~~~");
            System.out.println("Please Select an Option:");
            System.out.println("1.Add Teacher:");
            System.out.println("2.Show Teacher:");
            System.out.println("3.Search Teacher");
            System.out.println("4.Delete Teacher");
            System.out.println("5.Update Teacher");
            System.out.println("6.Back");

            try {
                teacherChoice = scanner.nextInt();
                scanner.nextLine();

                switch (teacherChoice) {

                    case 1: {
                        System.out.println("You Selected Add Teacher.");

                        System.out.println("Enter name:");
                        String name = scanner.nextLine();


                        System.out.println("Enter age:");
                        int age = scanner.nextInt();


                        System.out.println("Enter a TeacherId:");
                        int teacherId; // stores the teacher id given by the user
                        boolean duplicateId; // keeps track if the teacher id already exists or not

                        do {
                            teacherId = scanner.nextInt(); // read teacher id from the user
                            duplicateId = false; // to start assuming the id is available

                            for (int i = 0; i < teachers.size(); i++) {   // goes through all teachers
                                if (teachers.get(i).getTeacherId() == teacherId){   // checks for the same id
                                    duplicateId = true;  // same id was found
                                    break;     // to stop the search
                                }
                            }

                            if (duplicateId) {    // if the id is repeated
                                System.out.println("The Teacher Id you have entered already exists.");
                                System.out.println("Enter a Different Teacher Id: ");
                            }
                        } while(duplicateId); //  repeats until a unique id is entered

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

            } catch(Exception e){
                System.out.println("Invalid Choice. Please Enter a Number from 1 to 6");  //  the catch is at the end because first we use try to test the code if that fails then the catch handles the error.
                scanner.nextLine();
                teacherChoice = 0;
            }

            if (teacherChoice != 6) {
                System.out.println("Press Enter to return to the Teacher Menu.");
                scanner.nextLine();
            }

        } while (teacherChoice != 6);
    }
}
