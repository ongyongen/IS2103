/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carmsclient;

import OperationsManagerOperationsCLI.AssignTransitDriver;
import OperationsManagerOperationsCLI.CreateNewCar;
import OperationsManagerOperationsCLI.CreateNewModel;
import OperationsManagerOperationsCLI.DeleteCar;
import OperationsManagerOperationsCLI.DeleteModel;
import OperationsManagerOperationsCLI.TriggerManualDispatch;
import OperationsManagerOperationsCLI.UpdateCar;
import OperationsManagerOperationsCLI.UpdateModel;
import OperationsManagerOperationsCLI.UpdateStatusAsCompleted;
import OperationsManagerOperationsCLI.ViewAllCars;
import OperationsManagerOperationsCLI.ViewAllModels;
import OperationsManagerOperationsCLI.ViewCarDetails;
import OperationsManagerOperationsCLI.ViewDispatchRecords;
import ejb.session.stateless.CarSessionBeanRemote;
import ejb.session.stateless.DispatchRecordSessionBeanRemote;
import ejb.session.stateless.EJBTimerSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import ejb.session.stateless.ModelSessionBeanRemote;
import entity.Car;
import entity.DispatchRecord;
import entity.Employee;
import entity.Model;
import enumeration.CarStatus;
import exception.CarDoesNotExistException;
import exception.CategoryDoesNotExistException;
import exception.DuplicateCarException;
import exception.DuplicateCategoryException;
import exception.DuplicateModelException;
import exception.InvalidMenuOptionException;
import exception.ModelDoesNotExistException;
import java.lang.reflect.InvocationTargetException;
import exception.OutletDoesNotExistException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author ongyongen
 */
public class OperationsManagerOperations {
    
    private static EmployeeSessionBeanRemote employeeSessionBeanRemote;
    private static ModelSessionBeanRemote modelSessionBeanRemote;
    private static CarSessionBeanRemote carSessionBeanRemote;
    private static DispatchRecordSessionBeanRemote dispatchRecordSessionBeanRemote;
    private static EJBTimerSessionBeanRemote eJBTimerSessionBeanRemote;
    private static Employee employee;

    public OperationsManagerOperations() {
    }

    public OperationsManagerOperations(EmployeeSessionBeanRemote employeeSessionBeanRemote, 
            ModelSessionBeanRemote modelSessionBeanRemote, CarSessionBeanRemote carSessionBeanRemote, 
            DispatchRecordSessionBeanRemote dispatchRecordSessionBeanRemote, 
            EJBTimerSessionBeanRemote eJBTimerSessionBeanRemote, Employee employee) {
        this.employeeSessionBeanRemote = employeeSessionBeanRemote;
        this.modelSessionBeanRemote = modelSessionBeanRemote;
        this.carSessionBeanRemote = carSessionBeanRemote;
        this.dispatchRecordSessionBeanRemote = dispatchRecordSessionBeanRemote;
        this.eJBTimerSessionBeanRemote = eJBTimerSessionBeanRemote;
        this.employee = employee;
    }

    public static EmployeeSessionBeanRemote getEmployeeSessionBeanRemote() {
        return employeeSessionBeanRemote;
    }

    public static void setEmployeeSessionBeanRemote(EmployeeSessionBeanRemote employeeSessionBeanRemote) {
        OperationsManagerOperations.employeeSessionBeanRemote = employeeSessionBeanRemote;
    }

    public static ModelSessionBeanRemote getModelSessionBeanRemote() {
        return modelSessionBeanRemote;
    }

    public static void setModelSessionBeanRemote(ModelSessionBeanRemote modelSessionBeanRemote) {
        OperationsManagerOperations.modelSessionBeanRemote = modelSessionBeanRemote;
    }
    
    public static Employee getEmployee() {
        return employee;
    }

    public static void setEmployee(Employee employee) {
        OperationsManagerOperations.employee = employee;
    }
    

    public void doOperationsManagerOperations() throws ParseException {
      while (true) {
          try {
                System.out.println(" ");
                System.out.println("CaRMS Management Client - Operations Manager Portal");
                System.out.println("1. Create New Model");
                System.out.println("2. View All Models");
                System.out.println("3. Update Model");
                System.out.println("4. Delete Model");
                System.out.println("5. Create New Car");
                System.out.println("6. View All Cars");
                System.out.println("7. View Car Details");
                System.out.println("8. Update Car");
                System.out.println("9. Delete Car");
                System.out.println("10. View Transit Driver Dispatch Records for Current Day Reservations");
                System.out.println("11. Assign Transit Driver");
                System.out.println("12. Update Transit As Completed");
                System.out.println("13. Manually allocate cars to current day reservations (use case 5)");
                System.out.println("14. Log out");
                System.out.print(">");

                Integer response = 0;
                Scanner scanner = new Scanner(System.in);

                response = scanner.nextInt();

                System.out.println(" ");

                if (response == 1) {
                    CreateNewModel optionOne = new CreateNewModel(modelSessionBeanRemote);
                    optionOne.doCreateNewModel();                            
                }

                if (response == 2) {
                    ViewAllModels optionTwo = new ViewAllModels(modelSessionBeanRemote);
                    optionTwo.doViewAllModels();
                }

                if (response == 3) {
                    UpdateModel optionThree = new UpdateModel(modelSessionBeanRemote);
                    optionThree.doUpdateModel();                 
                } 

                if (response == 4) {
                    DeleteModel optionFour = new DeleteModel(modelSessionBeanRemote);
                    optionFour.doDeleteModel();
                }

                if (response == 5) {
                    CreateNewCar optionFive = new CreateNewCar(carSessionBeanRemote);
                    optionFive.doCreateNewCar();                            
                }                         

                if (response == 6) {
                    ViewAllCars optionSix = new ViewAllCars(carSessionBeanRemote);
                    optionSix.doViewAllCars();
                }

                if (response == 7) {
                    ViewCarDetails optionSeven = new ViewCarDetails(carSessionBeanRemote);
                    optionSeven.doViewCarDetails();
                }

                if (response == 8) {
                    UpdateCar optionEight = new UpdateCar(carSessionBeanRemote);
                    optionEight.doUpdateCar();            
                }

                if (response == 9) {
                    DeleteCar optionNine = new DeleteCar(carSessionBeanRemote);
                    optionNine.doDelete();           
                }

                if (response == 10) {
                    ViewDispatchRecords optionTen = new ViewDispatchRecords(dispatchRecordSessionBeanRemote);
                    optionTen.doViewDispatchRecords();
                }

                
                if (response == 11) {
                    AssignTransitDriver optionEleven = new AssignTransitDriver(dispatchRecordSessionBeanRemote);
                    optionEleven.doAssignTransitDriver();
                }
                
                if (response == 12) {
                    UpdateStatusAsCompleted optionTwelve = new UpdateStatusAsCompleted(dispatchRecordSessionBeanRemote);
                    optionTwelve.doUpdateStatusAsCompleted();
                }
                
                if (response == 13) {
                    TriggerManualDispatch triggerManualDispatch = new TriggerManualDispatch(eJBTimerSessionBeanRemote);
                    triggerManualDispatch.doTriggerManualDispatch();                
                }

                if (response == 14) {
                    break;
                }
                
                if (response == null) {
                    continue;
                }

            } catch (InputMismatchException ex) {
                System.out.println("Menu option does not exist");
                continue;
            }         
        }
    }
      
}

/**
 *  /**
                    System.out.print("Enter the outlet:\n>");
                    String outletName = scanner.next().trim(); 
                    
                    System.out.print("Enter the date:\n>");
                    String dateStr = scanner.next().trim(); 
                    
                    SimpleDateFormat formatterDateTime = new SimpleDateFormat("dd-MM-yy");
                    Date currentDate = formatterDateTime.parse(dateStr);
                    
                    System.out.println("Details of dispatch record for the outlet and day:");
                    System.out.printf("%20s%40s%40s\n", "id", "date & time of dispatch", "car license nos");

                    List<DispatchRecord> records = this.dispatchRecordSessionBeanRemote.retrieveAllDispatchRecordsForCurrentDayAndOutlet(currentDate, outletName);
                    for (DispatchRecord r : records) {
                        System.out.printf("%20s%30s%30s\n", 
                                r.getDispatchRecordId(),
                                r.getDateOfDispatch(),
                                r.getLicenseNosCarToPickUp());
                                * 
                                *        System.out.print("Enter the username of the employee:\n>");
                    String name = scanner.next().trim(); 
                    
                    System.out.print("Enter the dispatch record ID:\n>");
                    String idStr = scanner.next().trim(); 
                    Long id = Long.valueOf(idStr);
                    String res = this.dispatchRecordSessionBeanRemote.AssignDriverToRecord(name, id);
                    System.out.println(res);
                    *      System.out.print("Enter the dispatch record ID:\n>");
                    String idStr = scanner.next().trim(); 
                    Long id = Long.valueOf(idStr);                    
                    String res = this.dispatchRecordSessionBeanRemote.updateDispatchRecordAsCompleted(id);
                    System.out.println(res);
                    }**/
 