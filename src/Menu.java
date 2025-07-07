import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    public static void showMenu(){
//        Scanner sc = new Scanner(System.in);
        System.out.println("\nPlease Select Services:\n");
        System.out.println("Type-1: Add Employee");
        System.out.println("Type-2: List All Employees");
        System.out.println("Type-3: List Employees");
        System.out.println("Type-4: Remove Employee");
        System.out.println("Type-5: Exit");
        System.out.println();
        System.out.println("\nYour choice:");
//        try{
//            return sc.nextInt();
//        }
//        catch(InputMismatchException exception){
//            System.out.println("Please select a valid choice!");
//            return -1;
//        }
//        catch (Exception exception){
//            System.out.println("Sorry! Something went wrong....");
//            return -1;
//        }
    }

    public static void welcomeMsg(){
        System.out.println("Welcome to Employee Management System....");
    }

    public static void exitMsg(){
        System.out.println("Thank you for using our services....");
    }
}
