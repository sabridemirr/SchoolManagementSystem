import java.util.ArrayList;
import java.util.Scanner;

public class StudentManagement {
    public static void studentMenu(
            Scanner scanner,
            ArrayList<Student> students) {

        int studentChoice;

        do {
            showStudentMenu();

            try {
                studentChoice = scanner.nextInt();    // here try will cover the whole switch block
                scanner.nextLine();

                switch (studentChoice) {

                    case 1: {
                        System.out.println("You Selected Add Student.");

                        // TO ADD STUDENT NAME.
                        String name = readValidName(scanner, "Enter A Student Name.");  // This helps us call the helper method and store the valid result.


                        // AGE CODE
                        int age = readValidAge(scanner, "Enter Student Age:");

                        // STUDENT ID CODE
                        int studentId = readUniqueStudentId(scanner, students, "Enter Student ID:"); // Receives a valid, positive, and unused ID.

                        //GRADE CODE.
                        double grade = readValidGrade(scanner, "Enter Student Grade");

                        Student student = new Student(name, age, studentId, grade);
                        students.add(student);

                        System.out.println("You Have Succesfully Added a New Student.");
                        break;
                    }


                    // Show student case
                    case 2: {
                        if (students.isEmpty()) {
                            System.out.println("There are no students to show.");
                            break;
                        }

                        System.out.println("You Selected Show Student.");
                        for (int i = 0; i < students.size(); i++)
                            students.get(i).displayStudent();
                        break;
                    }


                    // Search case
                    case 3: {
                        if (students.isEmpty()) {
                            System.out.println("There are no students to search.");
                            break;
                        }

                        // Asking the user how they want to proceed with the show operation.
                        System.out.println("Show Student By:");
                        System.out.println("1. Student ID");
                        System.out.println("2. Name");

                        int showChoice = 0; // stores whether user wants ID or name
                        boolean validShowChoice; // to check if the input we enter is valid


                        // this part keeps asking the user to enter 1 or 2 until they do so.

                        do {
                            validShowChoice = true; // assumes the input is valid.

                            try {
                                showChoice = scanner.nextInt(); // tries to read number
                                scanner.nextLine();       // removes the left over enter

                                if (showChoice != 1 && showChoice != 2) {       // only 1 and 2 is allowed.
                                    System.out.println("Please choose a valid option.");
                                    validShowChoice = false;    // tells the loop you should ask again.
                                }
                            } catch (Exception e) {
                                // runs if the user enters a variable instead of a number.
                                System.out.println("Invalid choice of input. Please choose 1 or 2");
                                scanner.nextLine(); // removes wrong input like ABC
                                validShowChoice = false; // tells the loop you should ask again.
                            }
                        } while (!validShowChoice); // will repeat until the user enters the proper input.

                        boolean found = false;                 // assumes no student has been found
                        boolean shown = false;


                        // To show using student id
                        if (showChoice == 1) {

                            int showId = 0;   // to store the id entered by the user
                            boolean validChoiceId;   // to check if the entered id is valid


                            // keeps asking to user to enter a valid student id which isnt a negative number.
                            do {
                                validChoiceId = true;               // assume the id is valid
                                System.out.println("Enter Student ID:");

                                try {
                                    showId = scanner.nextInt();    // tries reading the student id
                                    scanner.nextLine();             // removes left over enter


                                    // student id cannot be less than or equal to 0
                                    if (showId <= 0) {
                                        System.out.println("Student Id can't be less than or equal to 0.");
                                        validChoiceId = false;     // repeats the loop
                                    }
                                } catch (Exception e) {
                                    // this runs when the user enters a variable.
                                    System.out.println("Student Id must only contain Numbers.");
                                    scanner.nextLine();
                                    validChoiceId = false;
                                }
                            } while (!validChoiceId);


                            for (int i = 0; i < students.size(); i++) {  // to search for every student in the array list.

                                if (students.get(i).getStudentId() == showId) {// to check if the current id matches the entered id.
                                    students.get(i).displayStudent();
                                    found = true;  // student exists.
                                    break;
                                }
                            }
                        } else if (showChoice == 2) {
                            String showName;    // stores the name.


                            // keeps asking until a proper name is entered.
                            do {
                                System.out.println("Enter Student Name:");
                                showName = scanner.nextLine(); // reads the name

                                if (showName.trim().isEmpty()) {      // doesnt allow the name to be empty you must enter a name
                                    System.out.println("Name cannot be empty.");
                                } else if (!showName.matches("[a-zA-Z ]+")) {       // only variables are allowed and nth else
                                    System.out.println("Invalid name. Please use letters only.");
                                }


                            } while (showName.trim().isEmpty() || !showName.matches("[a-zA-Z ]+")); // it will keep asking the loop until a proper input is entered.

                            for (int i = 0; i < students.size(); i++) {   // Search every student

                                // equalsIgnoreCase ignores uppercase/lowercase differences
                                // Sabri, sabri, SABRI will all match
                                if (students.get(i).getName().equalsIgnoreCase(showName)) {    // it compares text regardless if it is capital or not
                                    found = true;    // student exists

                                    students.get(i).displayStudent();  // to show the student before deleting.
                                    break;
                                }
                            }
                        }
                        if (!found) {        // If neither ID nor name matched any student
                            System.out.println("Student not Found");
                        }
                        break; // exits case 3
                    }


                    // Delete case
                    case 4: {
                        if (students.isEmpty()) {
                            System.out.println("There are no students to delete.");
                            break;
                        }

                        // Asking the user how they want to proceed with the delete operation.
                        System.out.println("Delete Student By:");
                        System.out.println("1. Student ID");
                        System.out.println("2. Name");

                        int deleteChoice = 0; // stores whether user wants ID or name
                        boolean validDeleteChoice; // to check if the input we enter is valid


                        // this part keeps asking the user to enter 1 or 2 until they do so.

                        do {
                            validDeleteChoice = true; // assumes the input is valid.

                            try {
                                deleteChoice = scanner.nextInt(); // tries to read number
                                scanner.nextLine();       // removes the left over enter

                                if (deleteChoice != 1 && deleteChoice != 2) {       // only 1 and 2 is allowed.
                                    System.out.println("Please choose a valid option.");
                                    validDeleteChoice = false;    // tells the loop you should ask again.
                                }
                            } catch (Exception e) {
                                // runs if the user enters a variable instead of a number.
                                System.out.println("Invalid choice of input. Please choose 1 or 2");
                                scanner.nextLine(); // removes wrong input like ABC
                                validDeleteChoice = false; // tells the loop you should ask again.
                            }
                        } while (!validDeleteChoice); // will repeat until the user enters the proper input.

                        boolean found = false;                 // assumes no student has been found
                        boolean deleted = false;


                        // To delete using student id
                        if (deleteChoice == 1) {

                            int deleteId = 0;   // to store the id entered by the user
                            boolean validDeleteId;   // to check if the entered id is valid


                            // keeps asking to user to enter a valid student id which isnt a negative number.
                            do {
                                validDeleteId = true;               // assume the id is valid
                                System.out.println("Enter Student ID:");

                                try {
                                    deleteId = scanner.nextInt();    // tries reading the student id
                                    scanner.nextLine();             // removes left over enter


                                    // student id cannot be less than or equal to 0
                                    if (deleteId <= 0) {
                                        System.out.println("Student Id can't be less than or equal to 0.");
                                        validDeleteId = false;     // repeats the loop
                                    } else {
                                        found = false; // Assume that the ID does not belong to a student.

                                        // Search the list to check whether the entered ID exists.
                                        for (int i = 0; i < students.size(); i++) {
                                            if (students.get(i).getStudentId() == deleteId) {
                                                found = true; // A student with this ID exists.
                                                break; // Stop searching.
                                            }
                                        }

                                        // A positive ID was entered, but it does not belong to a student.
                                        if (!found) {
                                            System.out.println("Student not found. Enter an existing Student ID.");
                                            validDeleteId = false; // Ask for the ID again.
                                        }
                                    }

                                } catch (Exception e) {
                                    // this runs when the user enters a variable.
                                    System.out.println("Student Id must only contain Numbers.");
                                    scanner.nextLine();
                                    validDeleteId = false;
                                }
                            } while (!validDeleteId);


                            for (int i = 0; i < students.size(); i++) {  // to search for every student in the array list.

                                if (students.get(i).getStudentId() == deleteId) {   // to check if the current id matches the entered id.
                                    found = true;       // student exists.

                                    students.get(i).displayStudent();      // to show the student before deleting.


                                    // to ask for confirmation.
                                    System.out.println("Are you sure you want to delete this student?");
                                    System.out.println("1. Yes");
                                    System.out.println("2. No");

                                    int confirm = 0;          // stores the user's confirmation choice
                                    boolean validConfirm;     // checks whether the user entered 1 or 2

                                    do {
                                        validConfirm = true;  // assume the input is valid

                                        try {
                                            confirm = scanner.nextInt(); // tries to read the confirmation
                                            scanner.nextLine();          // clears leftover Enter

                                            if (confirm != 1 && confirm != 2) { // only 1 or 2 are accepted
                                                System.out.println("Please enter 1 for Yes or 2 for No.");
                                                validConfirm = false;           // tells the loop to ask again
                                            }
                                        } catch (Exception e) {
                                            System.out.println("Invalid input. Please enter 1 or 2.");
                                            scanner.nextLine(); // removes bad input like "abc"
                                            validConfirm = false;
                                        }
                                    } while (!validConfirm); // repeats until 1 or 2 is entered


                                    // if the user confirms deletion.
                                    if (confirm == 1) {
                                        students.remove(i);
                                        deleted = true;
                                        System.out.println("Student deleted successfully.");  // if the user chooses 1.
                                    } else {      // if the user chooses 2.
                                        System.out.println("Delete cancelled.");
                                    }

                                    // Stop searching because the student was already found
                                    break;
                                }
                            }
                        }


                        // To delete by using name
                        else if (deleteChoice == 2) {
                            String deleteName;    // stores the name.
                            boolean validDeleteName;


                            // keeps asking until a proper name is entered.
                            do {
                                validDeleteName = true;
                                found = false;

                                System.out.println("Enter Student Name:");
                                deleteName = scanner.nextLine(); // reads the name

                                if (deleteName.trim().isEmpty()) {      // doesnt allow the name to be empty you must enter a name
                                    System.out.println("Name cannot be empty.");
                                    validDeleteName = false;

                                } else if (!deleteName.matches("[a-zA-Z ]+")) {       // only variables are allowed and nth else
                                    System.out.println("Invalid name. Please use letters only.");
                                    validDeleteName = false;


                                } else {// Check whether a student has this name.
                                    for (int i = 0; i < students.size(); i++) {
                                        if (students.get(i).getName().equalsIgnoreCase(deleteName)) {
                                            found = true;
                                            break;
                                        }
                                    }

                                    if (!found) {
                                        System.out.println("Student not found. Enter an existing name.");
                                        validDeleteName = false;
                                    }
                                }
                            } while (!validDeleteName);

                            // The loop above guarantees that a student was found.
                            for (int i = 0; i < students.size(); i++) {

                                if (students.get(i).getName().equalsIgnoreCase(deleteName)) {
                                    students.get(i).displayStudent();
                                    System.out.println("Are you sure you want to delete this student?");
                                    System.out.println("1. Yes");
                                    System.out.println("2. No");

                                    int confirm = 0;
                                    boolean validConfirm;

                                    do {
                                        validConfirm = true;

                                        try {
                                            confirm = scanner.nextInt();
                                            scanner.nextLine();

                                            if (confirm != 1 && confirm != 2) {
                                                System.out.println("Please enter 1 for Yes or 2 for No.");
                                                validConfirm = false;
                                            }

                                        } catch (Exception e) {
                                            System.out.println("Invalid input. Please enter 1 or 2.");
                                            scanner.nextLine();
                                            validConfirm = false;
                                        }

                                    } while (!validConfirm);

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
                        break;
                    }


                    // Update case
                    case 5: {
                        // This stops the update operation if there are no students to update.

                        if (students.isEmpty()) {
                            System.out.println("No Students to update.");
                            break;
                        }

                        // Student ID update
                        int updateId = 0;
                        Student studentToUpdate = null;  // it may be null which means nth but when a student is found it becomes studentToUpdate = students.get(i); which we can see in line 521


                        // To repeat until the user enters the id of an existing student
                        do {
                            System.out.println("Enter a Student ID to update:");

                            try {
                                updateId = scanner.nextInt(); // to try to read a number.
                                scanner.nextLine();  //clears the left over enter.

                                // student ids must be positive.
                                if (updateId <= 0) {
                                    System.out.println("Student id cant be 0 or less than 0.");
                                    continue;  // starts the next round of do-while so java doesnt search the list using an invalid negative ID.
                                }

                                // Search through all students for the entered ID.
                                for (int i = 0; i < students.size(); i++) {
                                    if (students.get(i).getStudentId() == updateId) {
                                        studentToUpdate = students.get(i); // Save the matching student.
                                        break;                             // Stop searching.
                                    }
                                }

                                // A valid number was entered but no student had that ID.
                                if (studentToUpdate == null) {
                                    System.out.println("Student not found. Enter an existing Student ID.");
                                }

                            } catch (Exception e) {
                                System.out.println("Student ID must only contain numbers.");
                                scanner.nextLine(); // Remove incorrect input such as "abc".
                            }

                        } while (studentToUpdate == null); // Repeat while no student has been found.

                        // Show the student before asking for new information.
                        System.out.println("Student selected for update:");
                        studentToUpdate.displayStudent();


                        // Name update.
                        String newName; // Stores the student's new name.

                        do {
                            System.out.println("Enter the new student name:");
                            newName = scanner.nextLine(); // Reads the complete name, including spaces.

                            if (newName.trim().isEmpty()) { // Rejects an empty name or spaces only.
                                System.out.println("Name cannot be empty.");
                            } else if (!newName.matches("[a-zA-Z ]+")) { // Allows only English letters and spaces.
                                System.out.println("Invalid name. Please use letters only.");
                            }
                        } while (newName.trim().isEmpty() || !newName.matches("[a-zA-Z ]+")); // Repeats until the name is valid.


                        // Age update
                        int newAge = 0; // Stores the new age after valid input is entered.
                        boolean validAge; // Controls whether the loop should repeat.

                        do {
                            validAge = true; // Begin each attempt by assuming the input is valid.
                            System.out.println("Enter the new student age:");

                            try {
                                newAge = scanner.nextInt(); // Attempts to read a whole number.

                                if (newAge < 4 || newAge > 61) { // Accepts only ages from 4 to 61.
                                    System.out.println("Invalid age. Age must be between 4 and 61.");
                                    validAge = false; // Makes the loop ask again.
                                }

                            } catch (Exception e) { // Runs when input is not a valid integer, such as "abc".
                                System.out.println("Age must only contain numbers.");
                                scanner.nextLine(); // Removes the invalid input from Scanner.
                                validAge = false; // Makes the loop ask again.
                            }

                        } while (!validAge); // Stops only after a valid age is entered.


                        // Grade update.
                        double newGrade = 0; // Stores the new grade after valid input is entered.
                        boolean validGrade; // Controls whether the grade loop should repeat.

                        do {
                            validGrade = true; // Begin each attempt by assuming the input is valid.
                            System.out.println("Enter the new student grade:");

                            try {
                                newGrade = scanner.nextDouble(); // Accepts whole or decimal numbers.

                                if (newGrade < 0 || newGrade > 100) { // Grade must stay within 0–100.
                                    System.out.println("Invalid grade. Grade must be between 0 and 100.");
                                    validGrade = false; // Makes the loop ask again.
                                }

                            } catch (Exception e) { // Runs for invalid input such as "abc".
                                System.out.println("Grade must only contain numbers.");
                                scanner.nextLine(); // Removes the invalid input from Scanner.
                                validGrade = false; // Makes the loop ask again.
                            }

                        } while (!validGrade); // Stops only after a valid grade is entered.
                        scanner.nextLine();


                        // Apply the new values only after every input has passed validation.
                        studentToUpdate.setName(newName);
                        studentToUpdate.setAge(newAge);
                        studentToUpdate.setGrade(newGrade);

                        System.out.println("Student updated successfully."); // Confirms that the update is complete.
                        studentToUpdate.displayStudent(); // Displays the student’s new information.

                        break;
                    }


                    // to return back to the menu.
                    case 6: {
                        System.out.println("Returning back to Main Menu.");
                        break;
                    }


                    // when you input the wrong option.
                    default: {
                        System.out.println("You have selected an invalid option.");
                        break;
                    }
                }


            } catch (Exception e) {
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

    // MENU METHOD
    private static void showStudentMenu() {
        System.out.println("~~~~~Welcome To The Student Management System~~~~~");
        System.out.println("Please Select an Option:");
        System.out.println("1.Add Student:");
        System.out.println("2.Show Students:");
        System.out.println("3.Search Student");
        System.out.println("4.Delete Student");
        System.out.println("5.Update Student");
        System.out.println("6.Back");
    }


     // CASE 1: ADD STUDENT.
    //For Adding Student Name.
    private static String readValidName(Scanner scanner, String message) {
        String name;        // Stores the name entered by the user

        do {
            System.out.println(message);   // to display whatever message we send to this method.
            name = scanner.nextLine();    // reads the name.

            if (name.trim().isEmpty()) {      // To check if the user entered only spaces or nothing at all.
                System.out.println("Name is Not Allowed Be Empty.");
            } else if (!name.matches("[a-zA-Z ]+")) {     // To check if the name has anything other than letters.
                System.out.println("Name Invalid. Please Only Use Letters.");
            }
        } while (name.trim().isEmpty() || !name.matches("[a-zA-Z ]+"));      // To repeat if name is empty or has anything other than letters.
        return name;    // sends the valid name back to whatever place the method was called.

    }


    // For Inputing Age.
    private static int readValidAge(Scanner scanner, String message) {
        int age = 0;
        boolean validAge;

        do {
            validAge = true;
            System.out.println(message);

            try {
                age = scanner.nextInt();

                if (age < 4 || age > 61) {  // age should be greater than 4 or less than 60
                    System.out.println("Invalid age. Input a valid age.");
                    validAge = false;
                }
            } catch (Exception e) {
                System.out.println("You cannot enter a letter. Please enter a Number");
                scanner.nextLine();
                validAge = false;
            }
        } while (!validAge);
        return age;
    }


    // For inputing Student ID.
    private static int readUniqueStudentId(Scanner scanner, ArrayList<Student> students, String message) {
        int studentId = 0;    // Stores the ID entered by the user.
        boolean validId;     // Decides whether the input loop should repeat

        do {
            validId = true;  // assume input is valid.
            System.out.println(message);

            try {
                studentId = scanner.nextInt(); // Attempts to read the ID as a whole number.

                if (studentId <= 0) {     // Id can't be 0 or less than 0.
                    System.out.println("Student Id cannot be 0 or less than 0.");
                    validId = false;
                } else {
                    for (Student student : students) {   // goes through all students
                        if (student.getStudentId() == studentId) {   // checks for the same id
                            System.out.println("This Student ID already exists. Enter a different ID."); // Explains why the ID was rejected.
                            validId = false;     // stop the search
                            break;
                        }
                    }
                }

            } catch (Exception e) {
                System.out.println("Student ID must only contain numbers.");
                scanner.nextLine();
                validId = false;
            }

        } while (!validId); //  repeats until a unique id is entered
        return studentId;
    }

    //For Entering Grade
    private static double readValidGrade(Scanner scanner, String message) {
        double grade = 0;
        boolean validGrade;

        do {
            validGrade = true;
            System.out.println(message);

            try {
                grade = scanner.nextDouble();     // Reads a whole or decimal number.

                if (grade < 0 || grade > 100) {
                    System.out.println("Invalid grade. Grade must be between 0 to 100.");
                    validGrade = false;    // Makes the loop ask again.
                }
            } catch (Exception e) {
                System.out.println("You cannot enter a variable. Please enter a Number");
                scanner.nextLine();
                validGrade = false;
            }
        } while (!validGrade);
        scanner.nextLine(); // clears the Enter left behind by nextDouble()
        return grade;
    }



//CASE 2: SHOW STUDENTS.
private static String showAllStudents()

