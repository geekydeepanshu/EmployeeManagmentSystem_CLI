import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Employee {
    String employeeId;
    String fName;
    String lName;
    String fatherName;
    String email;
    String dob;
    String  address;
    String designation;
    long salary;
    String contactNumber;
    String highestEducation;
    String aadhaarNumber;

    static String[] allowedDesignation = {"Developer","Manager","Owner"};
    static String[] validHigherEducation = {"bca","btech","mca","mtech","bba","mba"};

    public static boolean isValidDesignation(String inputEmployeeDesignation){
        for(String s:allowedDesignation){
            if(s.equals(inputEmployeeDesignation))
                return true;
        }
        return false;
    }

    public static boolean isHigherEducationValid(String employeeHigherEducationInput){
        for(String s: validHigherEducation){
            if(employeeHigherEducationInput.equals(s))
                return true;
        }
        return false;
    }

    public void addEmployee(Scanner sc) throws SQLException, InvalidInputForEmployee, InputMismatchException
    {
            // generate random employee id
            String generatedEmployeeId = (long)(Math.random() * 100000000)+"";
            this.employeeId = generatedEmployeeId.trim();

            // get user fName from user
            System.out.println("Enter Employee's First Name: ");
            String employeeFirstNameInput = sc.nextLine().trim();
            if(employeeFirstNameInput.isEmpty())
                throw new InvalidInputForEmployee("Error: The name cannot be empty.");
            if(employeeFirstNameInput.length()>13)
                throw new InvalidInputForEmployee("Error: The name must be fewer than 13 characters.");
            if(!employeeFirstNameInput.matches("[a-zA-Z]+"))
                throw new InvalidInputForEmployee("Error: The name can contain only alphabetic characters.");
            this.fName = employeeFirstNameInput;

            // get employee lName from user
            System.out.println("Enter Employee's Last Name: ");
            String employeeLastNameInput = sc.nextLine().trim();
            if(employeeLastNameInput.isEmpty())
                throw new InvalidInputForEmployee("Error: The name cannot be empty.");
            if(employeeLastNameInput.length()>13)
                throw new InvalidInputForEmployee("Error: The name must be fewer than 13 characters.");
            if(!employeeLastNameInput.matches("[a-zA-Z]+"))
                throw new InvalidInputForEmployee("Error: The name can contain only alphabetic characters.");
            this.lName = employeeLastNameInput;


            // get employee dob from user
            System.out.println("Enter Employee's Date-Of-Birth: ");
            String employeeDobInput = sc.nextLine().trim();
            if(!employeeDobInput.matches("^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$"))
                throw  new InvalidInputForEmployee("Error: The date format is invalid. Please use the 'yyyy-mm-dd' format.");
            this.dob = employeeDobInput;

            //
            System.out.println("Enter Employee's email address: ");
            String employeeEmailInput= sc.nextLine().trim();
            if(!employeeEmailInput.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$"))
                    throw new InvalidInputForEmployee("Error: The email address is invalid. Please enter a valid email in the format 'example@domain.com'.");
            this.email = employeeEmailInput;

            //
            System.out.println("Enter Employee's Contact Number: ");
            String contactNumberInput = sc.nextLine().trim();
            if(!contactNumberInput.matches("^\\d{10}$"))
                throw new InvalidInputForEmployee("Error: The contact number is invalid. Please enter a valid 10-digit phone number.");
            this.contactNumber = contactNumberInput;

            //
            System.out.println("Enter Employee's Address: ");
            String employeeAddressInput = sc.nextLine().trim();
            if(employeeAddressInput.isEmpty())
                throw new InvalidInputForEmployee("Error: The address cannot be empty. Please provide a valid address.");
            if(employeeAddressInput.length()>52)
                throw new InvalidInputForEmployee("Error: The address should be less than 52 characters. Please shorten your input.");
            this.address = employeeAddressInput;

            //
            System.out.println("Enter Employee's Designation: ");
            String employeeDesignationInput = sc.nextLine().trim();
            if(employeeDesignationInput.isEmpty())
                throw new InvalidInputForEmployee("Error: The designation cannot be empty. Please provide a valid designation.");
            if(!isValidDesignation(employeeDesignationInput))
                throw new InvalidInputForEmployee("Error: Please enter a valid designation. Accepted values are: [Manager, Developer, Owner].");
            this.designation = employeeDesignationInput;


            //
            System.out.println("Enter Employee's Salary: ");
            String employeeSalaryInput = sc.nextLine().trim();
            if(!employeeSalaryInput.matches("^[1-9]\\d*$"))
                throw  new InvalidInputForEmployee("Error: The salary must be a positive number. Please enter a valid salary.");
            this.salary = Long.parseLong(employeeSalaryInput);
//            System.out.println(employeeSalaryInput+"   "+salary);

            //
            System.out.println("Enter Employee's Highest Education: ");
            String employeeHighestEducationInput= sc.nextLine().trim();
//            System.out.println(employeeHighestEducationInput);
            if(employeeHighestEducationInput.isEmpty())
                throw new InvalidInputForEmployee("Error: Higher education cannot be empty. Please provide a valid entry.");
            if(!isHigherEducationValid(employeeHighestEducationInput))
                throw new InvalidInputForEmployee("Error: Please enter a valid higher education.");
            this.highestEducation = employeeHighestEducationInput;

            //
            System.out.println("Enter Employee's Aadhaar Card: ");
            String employeeAadhaarNumberInput = sc.nextLine();
            if(!employeeAadhaarNumberInput.matches("^\\d{11}$"))
                throw new InvalidInputForEmployee("Error: Please enter a valid 11-digit Aadhaar number.");
            this.aadhaarNumber = employeeAadhaarNumberInput;


    }


    public int saveEmployeeInDB(Connection connection) throws SQLException
    {

        String saveUserInDBQuery = "INSERT INTO employees(employeeId, fName, lName, dob, email, contactNumber, address, salary, designation, highestEducation, aadhaarNumber) VALUES(" + employeeId + ",'" + fName + "', '" + lName + "','" + dob + "','" + email + "','" + contactNumber + "','" + address + "'," + salary + ",'" + designation + "','" + highestEducation + "','" + aadhaarNumber + "');";

        Statement statement = null;
        statement = connection.createStatement();
        int noOfRowsChanged =  statement.executeUpdate(saveUserInDBQuery);
        statement.close();
        return noOfRowsChanged;
        // TODO: correct position of code for closing statement



    }

    public static void listAllEmployees(Connection connection) throws  SQLException
    {
        Statement statement = connection.createStatement();
        String getAllEmployeeFromDBQuery = "SELECT * FROM employees;";
        ResultSet getAllEmployee = statement.executeQuery(getAllEmployeeFromDBQuery);
        System.out.println("EmployeeId | First Name | Last Name | Date-Of-Birth | Email | Contact Number | Address | Designation | Salary | HighestEducation | Aadhaar Card Number");
        while(getAllEmployee.next()){
            System.out.print(getAllEmployee.getString("employeeId")+" | ");
            System.out.print(getAllEmployee.getString("fName")+" | ");
            System.out.print(getAllEmployee.getString("lName")+" | ");
            System.out.print(getAllEmployee.getDate("dob")+" | ");
            System.out.print(getAllEmployee.getString("email")+" | ");
            System.out.print(getAllEmployee.getLong("contactNumber")+" | ");
            System.out.print(getAllEmployee.getString("address")+" | ");
            System.out.print(getAllEmployee.getString("designation")+" | ");
            System.out.print(getAllEmployee.getString("salary")+" | ");
            System.out.print(getAllEmployee.getString("highestEducation")+" | ");
            System.out.print(getAllEmployee.getString("aadhaarNumber"));
            System.out.println();
        }
        getAllEmployee.close();
        statement.close();
    }

    public static void listAEmployee(Connection connection, Scanner sc) throws  SQLException, InputMismatchException, InvalidInputForEmployee
    {
        System.out.println("Please enter the employee ID you wish to search for: ");
        String employeeIdInput = sc.nextLine().trim();
        if(!employeeIdInput.matches("^\\d{8}$"))
            throw new InvalidInputForEmployee("Please enter a valid employee ID containing exactly 8 digits.");
        Statement statement = connection.createStatement();
        String listAEmployeeQuery = "SELECT * FROM employees WHERE employeeId='"+employeeIdInput+"'";
        ResultSet getAEmployee = statement.executeQuery(listAEmployeeQuery);

        if(!getAEmployee.next())
            System.out.println("Couldn't locate a user with that information in our database");
        else{
            System.out.println("EmployeeId | First Name | Last Name | Date-Of-Birth | Email | Contact Number | Address | Designation | Salary | HighestEducation | Aadhaar Card Number");
            System.out.print(getAEmployee.getString("employeeId")+" | ");
            System.out.print(getAEmployee.getString("fName")+" | ");
            System.out.print(getAEmployee.getString("lName")+" | ");
            System.out.print(getAEmployee.getDate("dob")+" | ");
            System.out.print(getAEmployee.getString("email")+" | ");
            System.out.print(getAEmployee.getLong("contactNumber")+" | ");
            System.out.print(getAEmployee.getString("address")+" | ");
            System.out.print(getAEmployee.getString("designation")+" | ");
            System.out.print(getAEmployee.getString("salary")+" | ");
            System.out.print(getAEmployee.getString("highestEducation")+" | ");
            System.out.print(getAEmployee.getString("aadhaarNumber"));
            System.out.println();
        }
        getAEmployee.close();
        statement.close();
    }

    public static void removeEmployee(Connection connection, Scanner sc ) throws SQLException, InputMismatchException,InvalidInputForEmployee, EmployeeNotFoundInDBException,Exception {
        System.out.println("Please enter the Employee ID of the employee you wish to delete: ");
        String employeeIdInput = sc.nextLine().trim();
        if(!employeeIdInput.matches("^\\d{8}$"))
            throw new InvalidInputForEmployee("Please enter a valid employee ID containing exactly 8 digits.");
        Statement statement = connection.createStatement();
        String listAEmployeeQuery = "SELECT * FROM employees WHERE employeeId='"+employeeIdInput+"'";
        ResultSet getAEmployee = statement.executeQuery(listAEmployeeQuery);
        if(!getAEmployee.next())
            throw new EmployeeNotFoundInDBException("Error: No employee found in the database with the provided Employee ID.");

        System.out.println("EmployeeId | First Name | Last Name ");
        System.out.print(getAEmployee.getString("employeeId")+" | ");
        System.out.print(getAEmployee.getString("fName")+" | ");
        System.out.print(getAEmployee.getString("lName"));

        System.out.println("\n" +
                "Are you sure you want to delete this employee? This action cannot be undone. [y/n]: ");
        char deletionContinueInput = sc.next().charAt(0);
        switch (deletionContinueInput){
            case 'y':
                        if(deleteEmployeeFromDB(connection,employeeIdInput)){
                            System.out.println("Employee has been deleted successfully.");
                        }
                        else{
                            throw new Exception("Error: Unable to delete the employee. Please try again later.");
                        }
                        break;
            case 'n':
                        System.out.println("Deletion cancelled...");
                        return;
            default:
                        throw new InvalidInputForEmployee("Error: Please select a valid choice - enter 'y' for Yes or 'n' for No.");
        }

     }

     private static boolean deleteEmployeeFromDB(Connection connection, String employeeId) throws SQLException
     {
         String deleteEmployeeFromDBQuery = "DELETE FROM employees WHERE employeeId='"+employeeId+"';";
         Statement statement = connection.createStatement();
         int numbersOfRowEffected = statement.executeUpdate(deleteEmployeeFromDBQuery);
         return numbersOfRowEffected != 0;
     }


}
