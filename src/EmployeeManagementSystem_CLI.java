import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class EmployeeManagementSystem_CLI {
    private static boolean serviceNextIteration = true;
    private static final Connection connection ;

    static {
        try {
            // create database connection
            connection = new DB_Connector().getPostgresConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){



        // create scanner class object
        Scanner scanner = new Scanner(System.in);

        // Welcome, Msg for user
        Menu.welcomeMsg();

        // show service menu to user
        while(serviceNextIteration){
            Menu.showMenu();
            int choice = scanner.nextInt();
            scanner.nextLine();
            try {
                switch (choice) {
                    case 1:
                            Employee employee = new Employee();
                            employee.addEmployee(scanner);
                            if(employee.saveEmployeeInDB(connection)>0)
                                System.out.println("Employee has been added to the database successfully.");
                            else
                                throw new Exception("Error: Employee could not be added to the database. Please try again later...");
                            break;

                    case 2:
                            Employee.listAllEmployees(connection);
                            break;
                    case 3:
                            Employee.listAEmployee(connection,scanner);
                            break;
                    case 4:
                            Employee.removeEmployee(connection,scanner);
                            break;

                    case 5:
                        serviceNextIteration = false;
                        break;
                    default:
                        System.out.println("Invalid Choice! Please try again...");
                }
            }
            catch (InputMismatchException e){
                System.out.println("Invalid Input");
            }
            catch (InvalidInputForEmployee e){
                System.out.println("\n"+e.getMessage()+"\n");
            }
            catch(SQLException e){
                System.out.println("\n"+e.getMessage()+"\n");
                e.printStackTrace();
            }
            catch(Exception e){
                System.out.println("\n"+e.getMessage()+"\n");
            }

        }

        // exit Msg
        Menu.exitMsg();
    }
}


//        try {
//            DB_Connector dbConnector = new DB_Connector();
//            Connection dbConnection = dbConnector.getPostgresConnection();
//            Statement sqlStatement = dbConnection.createStatement();
////            AddEmployee addEmployee = new AddEmployee();
////            addEmployee.saveUserInDataBase(sqlStatement);
//            ListAllEmployees listAllEmployees = new ListAllEmployees();
//            listAllEmployees.listAllEmployees(sqlStatement);
//            RemoveEmployee removeEmployee = new RemoveEmployee();
//            removeEmployee.showEmployeeBeforeDeletion(sqlStatement);
//            removeEmployee.deleteEmployeeFromDB(sqlStatement);
//            sqlStatement.close();
//            dbConnection.close();
//        }
//        catch(SQLException exception){
//            System.out.println("Database connection unsuccessful! Something went wrong...");
//            System.out.println(exception.getMessage());
//        }




