import java.util.ArrayList;
import java.util.Scanner;

public class ParentManagement {
    public static void parentMenu(
            Scanner scanner,
            ArrayList<Parent> parents) {

        int parentChoice;

        do {
            System.out.println("~~~~~Welcome To The Parent Management System~~~~~");
            System.out.println("Please Select an Option:");
            System.out.println("1.Add Parent:");
            System.out.println("2.Show Parent:");
            System.out.println("3.Search Parent");
            System.out.println("4.Delete Parent");
            System.out.println("5.Update Parent");
            System.out.println("6.Back");

           try {
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

                       System.out.println("Enter a ParentID:");
                       int parentId; // stores the parent id given by the user
                       boolean duplicateId; // keeps track if the parent id already exists or not

                       do {
                           parentId = scanner.nextInt(); // read parent id from the user
                           duplicateId = false; // to start assuming the id is available

                           for (int i = 0; i < parents.size(); i++) {   // goes through all parents
                               if (parents.get(i).getParentId() == parentId){   // checks for the same id
                                   duplicateId = true;  // same id was found
                                   break;     // to stop the search
                               }
                           }

                           if (duplicateId) {    // if the id is repeated
                               System.out.println("The Parent Id you have entered already exists.");
                               System.out.println("Enter a Different Parent Id: ");
                           }
                       } while(duplicateId); //  repeats until a unique id is entered


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

           } catch(Exception e){
               System.out.println("Invalid Choice. Please Enter a Number from 1 to 6");  //  the catch is at the end because first we use try to test the code if that fails then the catch handles the error.
               scanner.nextLine();
               parentChoice = 0;
           }

            if (parentChoice != 6) {
                System.out.println("Press Enter to return to the Parent Menu.");
                scanner.nextLine();
            }

        } while (parentChoice != 6);
    }
}
