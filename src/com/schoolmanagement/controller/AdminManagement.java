package com.schoolmanagement.controller;

import com.schoolmanagement.model.Student;
import com.schoolmanagement.model.Teacher;
import com.schoolmanagement.model.Parent;
import com.schoolmanagement.service.StudentService;
import com.schoolmanagement.service.TeacherService;
import com.schoolmanagement.service.ParentService;
import com.schoolmanagement.util.InputHelper;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class AdminManagement {
    public static void adminMenu(Scanner scanner, StudentService studentService, TeacherService teacherService, ParentService parentService) {
        // Gives Admin access to the same Student, Teacher & Parent data as Student, Teacher & Parent Controller.

        InputHelper inputHelper = new InputHelper(scanner); // Handles and validates console input.
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
                        System.out.println("You Selected Show Students.");
                        ArrayList<Student> students = studentService.getAllStudents(); // Gets students from the same repository used by StudentController.
                        if (students.isEmpty()) { // Handles the situation where no students have been added.
                            System.out.println("There are no students to display.");
                        } else {
                            for (Student student : students) { // Goes through every student returned by the service.
                                student.displayStudent(); // Displays the current student.
                            }
                        }
                        break;
                    }

                    case 2: {
                        System.out.println("You Selected Show Teachers.");
                        ArrayList<Teacher> teachers = teacherService.getAllTeachers(); // Gets teachers from the same repository used by StudentController.
                        if (teachers.isEmpty()) { // Handles the situation where no teachers have been added.
                            System.out.println("There are no teachers to display.");
                        } else {
                            for (Teacher teacher : teachers) { // Goes through every teacher returned by the service.
                                teacher.displayTeacher(); // Displays the current teachers.
                            }
                        }
                        break;
                    }

                    case 3: {
                        System.out.println("You Selected Show Parents.");
                        ArrayList<Parent> parents = parentService.getAllParents(); // Gets all parents stored in ParentRepository.

                        if (parents.isEmpty()) { // Checks whether there are any parents to display.
                            System.out.println("There are no parents to display.");
                        } else {
                            for (Parent parent : parents) { // Goes through every Parent returned by the service.
                                parent.displayParent(); // Displays the current Parent's information.
                            }
                        }
                        break; // Ends case 3 and returns to the Admin menu.
                    }

                    case 4: {
                        System.out.println("You Selected Search Students.");
                        System.out.println("Enter a valid Student ID:");
                        int searchId = scanner.nextInt();
                        scanner.nextLine();

                        Student student = studentService.getStudentById(searchId); // Service searches the shared repository.

                        if (student == null) { // A null result means no student has this ID.
                            System.out.println("Student was not found.");
                        } else {
                            System.out.println("Student was found.");
                            student.displayStudent(); // Displays the student returned by the service.
                        }
                        break;
                    }

                    case 5: {
                        System.out.println("You Selected Search Teachers.");
                        System.out.println("Enter a valid Teacher ID:");
                        int searchId = scanner.nextInt();
                        scanner.nextLine();

                        Teacher teacher = teacherService.getTeacherById(searchId); // Service searches the shared repository.

                        if (teacher == null) { // A null result means no teacher has this ID.
                            System.out.println("Teacher was not found.");
                        } else {
                            System.out.println("Teacher was found.");
                            teacher.displayTeacher(); // Displays the teacher returned by the service.
                        }
                        break;
                    }

                    case 6: {
                        System.out.println("You Selected Search Parent.");
                        int searchId = inputHelper.readPositiveInteger("Enter a valid Parent ID:"); // Accepts only a positive whole number.

                        Parent parent = parentService.getParentById(searchId); // Searches ParentRepository using the entered ID.

                        if (parent == null) { // Null means no Parent has the entered ID.
                            System.out.println("Parent was not found.");
                        } else {
                            System.out.println("Parent was found.");
                            parent.displayParent(); // Displays the Parent returned by ParentService.
                        }
                        break; // Ends case 6 and returns to the Admin menu.
                    }

                    case 7: {
                        System.out.println("Enter the Student ID to update:");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();

                        Student student = studentService.getStudentById(updateId); // Searches the shared repository to check if the student exists.

                        if (student == null) {
                            System.out.println("Student was not found.");
                            break;
                        }

                        System.out.println("Current Student Information:");
                        student.displayStudent();

                        System.out.println("Enter the new name:");
                        String newName = scanner.nextLine();

                        System.out.println("Enter the new age:");
                        int newAge = scanner.nextInt();
                        scanner.nextLine();

                        System.out.println("Enter the new grade:");
                        double newGrade = scanner.nextDouble();
                        scanner.nextLine();

                        Student updatedStudent = studentService.updateStudent(updateId, newName, newAge, newGrade); // Service validates and updates the existing teacher.
                        System.out.println("Student updated successfully.");
                        updatedStudent.displayStudent();
                        break;
                    }

                    case 8: {
                        System.out.println("Enter a TeacherID to Update");
                        int updateId = scanner.nextInt();
                        scanner.nextLine();

                        Teacher teacher = teacherService.getTeacherById(updateId);

                        if (teacher == null) {
                            System.out.println("Teacher was not found.");
                            break;
                        }

                        System.out.println("Current Teacher Information:");
                        teacher.displayTeacher();

                        System.out.println("Enter the new name:");
                        String newName = scanner.nextLine();

                        System.out.println("Enter the new age:");
                        int newAge = scanner.nextInt();
                        scanner.nextLine();

                        Teacher updatedTeacher = teacherService.updateTeacher(updateId, newName, newAge); // Service validates and updates the existing student.
                        System.out.println("Student updated successfully.");
                        updatedTeacher.displayTeacher();
                        break;
                    }

                    case 9: {
                        System.out.println("Update Parent:");
                        int updateId = inputHelper.readPositiveInteger("Enter the Parent ID to update:"); // Reads a valid positive Parent ID.

                        Parent parent = parentService.getParentById(updateId); // Searches for the Parent before requesting new information.

                        if (parent == null) { // Stops the update when the Parent does not exist.
                            System.out.println("Parent was not found.");
                            break;
                        }

                        System.out.println("Current Parent Information:");
                        parent.displayParent(); // Shows the current information before changing it.

                        String newName = inputHelper.readName("Enter the new name:"); // Reads a name containing only letters and spaces.
                        int newAge = inputHelper.readIntegerInRange("Enter the new age:", 18, 100); // Accepts only the allowed Parent age range.
                        String newPhoneNumber = inputHelper.readText("Enter the new phone number:").trim(); // Reads phone input; ParentService validates its format.
                        String newEmail = inputHelper.readText("Enter the new email:").trim(); // Reads email input; ParentService validates its format.

                        Parent updatedParent = parentService.updateParent(updateId, newName, newAge, newPhoneNumber, newEmail); // Updates only name, age, phone, and email. Both IDs and relationship remain unchanged.

                        if (updatedParent == null) { // Provides protection if the Parent disappears or cannot be found.
                            System.out.println("Parent could not be updated.");
                            break;
                        }

                        System.out.println("Parent updated successfully.");
                        updatedParent.displayParent(); // Displays updated fields and unchanged IDs and relationship.
                        break; // Ends case 9 and returns to the Admin menu.
                    }

                    case 10: {
                        System.out.println("Select a Student to Delete:");
                        int deleteId = scanner.nextInt();  // to read the id selected by the user.
                        scanner.nextLine();

                        Student student = studentService.getStudentById(deleteId); // Searches the shared repository to check if the student exists.

                        if (student == null) { // Stops when the ID does not belong to a student.
                            System.out.println("Student was not found.");
                            break;
                        }

                        System.out.println("Student Found:");
                        student.displayStudent(); // Displays the student before deletion.

                        System.out.println("Are you sure you want to delete this student?");
                        System.out.println("1. Yes");
                        System.out.println("2. No");

                        int confirm = inputHelper.readIntegerInRange("Select an Option: ", 1, 2); // Repeats until the admin enters the number 1 or 2.

                        if (confirm == 2) {
                            System.out.println("Delete process cancelled.");
                            break;
                        }
                        boolean deleted = studentService.deleteStudentById(deleteId); // Deletes from the shared repository.

                        if (deleted) {
                            System.out.println("Student deleted successfully.");
                        } else {
                            System.out.println("Student could not be deleted.");
                        }
                        break;
                    }

                    case 11: {
                        System.out.println("Select a Teacher to Delete:");
                        int deleteId = scanner.nextInt();
                        scanner.nextLine();

                        Teacher teacher = teacherService.getTeacherById(deleteId); // Searches the shared repository to check if the teacher exists.

                        if (teacher == null) { // Stops when the ID does not belong to a teacher.
                            System.out.println("Teacher was not found.");
                            break;
                        }

                        System.out.println("Teacher Found:");
                        teacher.displayTeacher(); // Displays the student before deletion.

                        System.out.println("Are you sure you want to delete this Teacher?");
                        System.out.println("1. Yes");
                        System.out.println("2. No");

                        int confirm = inputHelper.readIntegerInRange("Select an Option: ", 1, 2); // Repeats until the admin enters the number 1 or 2.

                        if (confirm == 2) {
                            System.out.println("Delete process cancelled.");
                            break;
                        }
                        boolean deleted = teacherService.deleteTeacherById(deleteId); // Deletes from the shared repository.

                        if (deleted) {
                            System.out.println("Teacher deleted successfully.");
                        } else {
                            System.out.println("Teacher could not be deleted.");
                        }
                        break;
                    }

                        case 12: {
                            System.out.println("Delete Parent:");
                            int deleteId = inputHelper.readPositiveInteger("Enter the Parent ID to delete:"); // Reads a positive Parent ID.

                            Parent parent = parentService.getParentById(deleteId); // Searches for the Parent before deletion.

                            if (parent == null) { // Stops when no Parent has the entered ID.
                                System.out.println("Parent was not found.");
                                break;
                            }

                            System.out.println("Parent Found:");
                            parent.displayParent(); // Shows the Parent so the Admin can verify the correct record.

                            System.out.println("Are you sure you want to delete this parent?");
                            System.out.println("1. Yes");
                            System.out.println("2. No");

                            int confirm = inputHelper.readIntegerInRange("Select an Option:", 1, 2); // Keeps asking until the Admin enters 1 or 2.
                            if (confirm == 2) { // Option 2 cancels without changing repository data.
                                System.out.println("Delete process cancelled.");
                                break;
                            }

                            boolean deleted = parentService.deleteParentById(deleteId); // Asks ParentService to remove the Parent from ParentRepository.

                            if (deleted) { // True means the repository successfully removed the Parent.
                                System.out.println("Parent deleted successfully.");
                            } else {
                                System.out.println("Parent could not be deleted.");
                            }
                            break; // Ends case 12 and returns to the Admin menu.
                        }

                    case 13: {
                        System.out.println("Returning back to Main Menu.");
                        break;
                    }

                    default:
                        System.out.println("Invalid Choice.");


                }
            } catch (
                    InputMismatchException e) {  // a runtime exception thrown by the Scanner class when the data entered does not match the expected data type
                System.out.println("Invalid Choice. Please Enter a Number from 1 to 13");  //  the catch is at the end because first we use try to test the code if that fails then the catch handles the error.
                scanner.nextLine();
                adminChoice = 0;
            } catch (IllegalArgumentException e) { // Handles validation errors thrown by StudentService.
                System.out.println("Operation failed: " + e.getMessage());
                adminChoice = 0;
            }
            if (adminChoice != 13) {
                System.out.println("Press Enter to return to the Admin Menu.");
                scanner.nextLine();
            }
        } while (adminChoice != 13);
    }
}
