/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carmsclient;

import ejb.session.stateless.EmployeeSessionBeanRemote;
import entity.Employee;
import exception.InvalidLoginCredentialException;
import exception.InvalidMenuOptionException;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.ejb.EJB;

/**
 *
 * @author ongyongen
 */
public class LoginOperation {
    
    
    private static EmployeeSessionBeanRemote employeeSessionBeanRemote;
    
    private static Employee employee;
    
    private boolean logOutAfterLogIn;
        
    public LoginOperation() {
        
    }

    public LoginOperation(EmployeeSessionBeanRemote employeeSessionBeanRemote) {
        this.employeeSessionBeanRemote = employeeSessionBeanRemote;   
        this.logOutAfterLogIn = false;
    }
    
    public boolean isLogOutAfterLogIn() {
        return logOutAfterLogIn;
    }

    public void setLogOutAfterLogIn(boolean logOutAfterLogIn) {
        this.logOutAfterLogIn = logOutAfterLogIn;
    }
    
    public void doLogin()  {
        while (true) {
            try {
                System.out.println(" ");
                System.out.println("Welcome to the CaRMS Management Client!");
                System.out.println("1: Log in");
                System.out.println("2: Exit");
                System.out.print(">");

                Scanner scanner = new Scanner(System.in);

                Integer response = scanner.nextInt();

                if (response == 1) {
                    System.out.print("Enter your username:\n>");
                    String username = scanner.next().trim();

                    System.out.print("Enter your password:\n>");
                    String password = scanner.next().trim();

                    Employee employee = employeeSessionBeanRemote.retrieveEmployeeByUsernameAndPassword(username, password);
                    this.setEmployee(employee);
                    System.out.println("Credentials are correct. You are logged in.");
                    break;
                }

                if (response == 2) {
                    this.setLogOutAfterLogIn(true);
                    System.out.println("You have exited the client");
                    break;
                }
                
                else {
                    throw new InvalidMenuOptionException("Invalid menu option");
                }
             
            } catch (NullPointerException | InputMismatchException | InvalidMenuOptionException ex) {
                System.out.println("Menu option is invalid. Please select a valid option");
            } catch (InvalidLoginCredentialException ex) {
                System.out.println("Your input login credentials are incorrect");
            }
        }
   
    }
    
    public static Employee getEmployee() {
        return employee;
    }

    public static void setEmployee(Employee employee) {
        LoginOperation.employee = employee;
    }

}
