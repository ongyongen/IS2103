/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carmsclient;


import ejb.session.stateless.CarReservationSessionBeanRemote;
import ejb.session.stateless.CarSessionBeanRemote;
import ejb.session.stateless.DispatchRecordSessionBeanRemote;
import ejb.session.stateless.EJBTimerSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import ejb.session.stateless.ModelSessionBeanRemote;
import ejb.session.stateless.RentalRateSessionBeanRemote;
import entity.Employee;
import enumeration.EmployeeRole;
import java.text.ParseException;
import javax.ejb.EJB;

/**
 *
 * @author ongyongen
 */
public class Main {

    @EJB
    private static EJBTimerSessionBeanRemote eJBTimerSessionBeanRemote;

    @EJB
    private static DispatchRecordSessionBeanRemote dispatchRecordSessionBeanRemote;

    @EJB
    private static CarReservationSessionBeanRemote carReservationSessionBeanRemote;

    @EJB
    private static RentalRateSessionBeanRemote rentalRateSessionBeanRemote;

    @EJB
    private static CarSessionBeanRemote carSessionBeanRemote;

    @EJB
    private static ModelSessionBeanRemote modelSessionBeanRemote;

    @EJB
    private static EmployeeSessionBeanRemote employeeSessionBeanRemote;
    
    
       
    public static void main(String[] args) throws ParseException   {
        
        LoginOperation login = new LoginOperation(employeeSessionBeanRemote);
        
        while (true) {
            login.doLogin();
            if (login.isLogOutAfterLogIn() == true) {
                break;
            }
            if (login.getEmployee() != null) {
                Employee employee = LoginOperation.getEmployee();
                
                if (employee.getRole() == EmployeeRole.OPERATIONS_MANAGER) {                 
                    OperationsManagerOperations operationsManagerOperations = new OperationsManagerOperations(employeeSessionBeanRemote, modelSessionBeanRemote, carSessionBeanRemote, dispatchRecordSessionBeanRemote, eJBTimerSessionBeanRemote, employee);
                    operationsManagerOperations.doOperationsManagerOperations();
                }

                if (employee.getRole() == EmployeeRole.SALES_MANAGER) {
                    SalesManagerOperations salesManagerOperations = new SalesManagerOperations(employeeSessionBeanRemote, rentalRateSessionBeanRemote, employee);
                    salesManagerOperations.doSalesManagerOperations();

                }
                
                if (employee.getRole() == EmployeeRole.CUSTOMER_SERVICES_EXECUTIVE) {
                    CustomerServiceExecutiveOperations customerServiceExecutiveOperations = new CustomerServiceExecutiveOperations(carReservationSessionBeanRemote);
                    customerServiceExecutiveOperations.doCustomerServiceExecutiveOperations();
                }
            }               
        }
    }
}
