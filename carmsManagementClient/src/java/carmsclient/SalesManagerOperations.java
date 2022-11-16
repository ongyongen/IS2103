/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carmsclient;

import SalesManagerOperationsCLI.CreateRentalRate;
import SalesManagerOperationsCLI.DeleteRentalRate;
import SalesManagerOperationsCLI.RetrieveAllRentalRates;
import SalesManagerOperationsCLI.UpdateRentalRate;
import SalesManagerOperationsCLI.ViewRentalRateDetails;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import ejb.session.stateless.RentalRateSessionBeanRemote;
import entity.Employee;
import entity.RentalRate;
import enumeration.RentalRateType;
import exception.CategoryDoesNotExistException;
import exception.DuplicateRentalRateException;
import exception.EndDateBeforeStartDateException;
import exception.InvalidMenuOptionException;
import exception.RentalRateDoesNotExistException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ongyongen
 */
public class SalesManagerOperations {
    
    private static EmployeeSessionBeanRemote employeeSessionBeanRemote;
    private static RentalRateSessionBeanRemote rentalRateSessionBeanRemote;
    private static Employee employee;
   

    public SalesManagerOperations() {
    }

    public SalesManagerOperations(EmployeeSessionBeanRemote employeeSessionBeanRemote, 
            RentalRateSessionBeanRemote rentalRateSessionBeanRemote,
            Employee employee) {
        this.employeeSessionBeanRemote = employeeSessionBeanRemote;
        this.rentalRateSessionBeanRemote = rentalRateSessionBeanRemote;
        this.employee = employee;
    }

    public static Employee getEmployee() {
        return employee;
    }

    public static void setEmployee(Employee employee) {
        SalesManagerOperations.employee = employee;
    }
    
    public void doSalesManagerOperations()  {
   
        while (true) {
            try {
                System.out.println(" ");
                System.out.println("CaRMS Management Client - Sales Manager Portal");
                System.out.println("1. Create New Rental Rate");
                System.out.println("2. View All Rental Rates");
                System.out.println("3. View Rental Rate Details");
                System.out.println("4. Update Rental Rate");
                System.out.println("5. Delete Rental Rate");
                System.out.println("6. Log out");
                System.out.print(">");
                Integer response = 0;
                Scanner scanner = new Scanner(System.in);
                response = scanner.nextInt();
                System.out.println(" ");
                if (response == 1) {
                    CreateRentalRate optionOne = new CreateRentalRate(rentalRateSessionBeanRemote);
                    optionOne.doCreateRentalRate();
                }
                if (response == 2) {
                    RetrieveAllRentalRates optionTwo = new RetrieveAllRentalRates(rentalRateSessionBeanRemote);
                    optionTwo.doRetrieveAllRentalRates();
                }
                if (response == 3) {
                    ViewRentalRateDetails optionThree = new ViewRentalRateDetails(rentalRateSessionBeanRemote);
                    optionThree.doViewRentalRateDetails();
                }
                if (response == 4) {
                    UpdateRentalRate optionFour = new UpdateRentalRate(rentalRateSessionBeanRemote);
                    optionFour.doUpdateRentalRate();
                }
                if (response == 5) {
                    DeleteRentalRate optionFive = new DeleteRentalRate(rentalRateSessionBeanRemote);
                    optionFive.doDeleteRentalRate();
                }
                if (response == 6) {
                    break;
                }        
                
            } catch (InputMismatchException ex) {
                System.out.println("Menu option does not exist");
            }
        }
    }
}
