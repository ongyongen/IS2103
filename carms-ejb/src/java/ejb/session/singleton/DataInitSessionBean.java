/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.singleton;

import ejb.session.stateless.CarReservationSessionBeanLocal;
import ejb.session.stateless.CarSessionBeanLocal;
import ejb.session.stateless.CategorySessionBeanLocal;
import ejb.session.stateless.EmployeeSessionBeanLocal;
import ejb.session.stateless.ModelSessionBeanLocal;
import ejb.session.stateless.OutletSessionBeanLocal;
import ejb.session.stateless.PartnerSessionBeanLocal;
import ejb.session.stateless.RecordSessionBeanLocal;
import ejb.session.stateless.RentalRateSessionBeanLocal;
import entity.Car;
import entity.Category;
import entity.Employee;
import entity.Model;
import entity.Outlet;
import entity.Partner;
import entity.RentalRate;
import enumeration.CarReservationPaymentStatus;
import enumeration.CarStatus;
import enumeration.EmployeeRole;
import enumeration.RentalRateType;
import exception.CategoryDoesNotExistException;
import exception.DuplicateCarException;
import exception.DuplicateCategoryException;
import exception.DuplicateModelException;
import exception.DuplicateOutletException;
import exception.DuplicateRentalRateException;
import exception.DuplicateUserException;
import exception.DuplicationPartnerException;
import exception.ModelDoesNotExistException;
import exception.OutletDoesNotExistException;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ongyongen
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @EJB
    private PartnerSessionBeanLocal partnerSessionBeanLocal;

    @EJB
    private CarReservationSessionBeanLocal carReservationSessionBeanLocal;

    @EJB
    private RentalRateSessionBeanLocal rentalRateSessionBeanLocal;


    @EJB
    private ModelSessionBeanLocal modelSessionBeanLocal;

    @EJB
    private CarSessionBeanLocal carSessionBeanLocal;

    @EJB
    private CategorySessionBeanLocal categorySessionBeanLocal;

    @EJB
    private EmployeeSessionBeanLocal employeeSessionBeanLocal;

    @EJB
    private OutletSessionBeanLocal outletSessionBeanLocal;

    @EJB(name = "RecordSessionBeanLocal")
    private RecordSessionBeanLocal recordSessionBeanLocal;
    
    

    @PersistenceContext(unitName = "carms-ejbPU")
    private EntityManager em;

    @PostConstruct
    public void postConstruct()  {
        if (em.find(Outlet.class, 1l) == null) {

            try {
                // Create new outlet
                Outlet outletA = new Outlet("Outlet_A");
                Outlet outletB = new Outlet("Outlet_B");
                Outlet outletC = new Outlet("Outlet_C",  Time.valueOf("08:00:00"), Time.valueOf("22:00:00"));
       
                outletSessionBeanLocal.createNewOutlet(outletA);
                outletSessionBeanLocal.createNewOutlet(outletB);
                outletSessionBeanLocal.createNewOutlet(outletC);
                
                // Create new employee
                Employee empA1 = new Employee("Employee_A1", outletA, EmployeeRole.SALES_MANAGER);
                Employee empA2 = new Employee("Employee_A2", outletA, EmployeeRole.OPERATIONS_MANAGER);
                Employee empA3 = new Employee("Employee_A3", outletA, EmployeeRole.CUSTOMER_SERVICES_EXECUTIVE);
                Employee empA4 = new Employee("Employee_A4", outletA, EmployeeRole.EMPLOYEE);
                Employee empA5 = new Employee("Employee_A5", outletA, EmployeeRole.EMPLOYEE);
                
                Employee empB1 = new Employee("Employee_B1", outletB, EmployeeRole.SALES_MANAGER);
                Employee empB2 = new Employee("Employee_B2", outletB, EmployeeRole.OPERATIONS_MANAGER);
                Employee empB3 = new Employee("Employee_B3", outletB, EmployeeRole.CUSTOMER_SERVICES_EXECUTIVE);

                Employee empC1 = new Employee("Employee_C1", outletB, EmployeeRole.SALES_MANAGER);
                Employee empC2 = new Employee("Employee_C2", outletB, EmployeeRole.OPERATIONS_MANAGER);
                Employee empC3 = new Employee("Employee_C3", outletB, EmployeeRole.CUSTOMER_SERVICES_EXECUTIVE);
                
                employeeSessionBeanLocal.createNewEmployee(empA1);
                employeeSessionBeanLocal.createNewEmployee(empA2);
                employeeSessionBeanLocal.createNewEmployee(empA3);
                employeeSessionBeanLocal.createNewEmployee(empA4);
                employeeSessionBeanLocal.createNewEmployee(empA5);
                employeeSessionBeanLocal.createNewEmployee(empB1);
                employeeSessionBeanLocal.createNewEmployee(empB2);
                employeeSessionBeanLocal.createNewEmployee(empB3);
                employeeSessionBeanLocal.createNewEmployee(empC1);
                employeeSessionBeanLocal.createNewEmployee(empC2);
                employeeSessionBeanLocal.createNewEmployee(empC3);

                // Create new Category
                Category ss = new Category("Standard_Sedan");
                Category fs = new Category("Family_Sedan");
                Category ls = new Category("Luxury_Sedan");
                Category sm = new Category("SUV_and_Minivan");

                categorySessionBeanLocal.createNewCategory(ss);
                categorySessionBeanLocal.createNewCategory(fs);
                categorySessionBeanLocal.createNewCategory(ls);
                categorySessionBeanLocal.createNewCategory(sm);

                Model corolla = new Model("Toyota", "Corolla");
                Model civic = new Model("Honda", "Civic");
                Model sunny = new Model("Nissan", "Sunny");   
                Model eClass = new Model("Mercedes", "E Class");
                Model fiveseries = new Model("BMW", "5 Series");
                Model a6 = new Model("Audi", "A6");

                modelSessionBeanLocal.createNewModel(corolla, "Standard_Sedan");
                modelSessionBeanLocal.createNewModel(civic, "Standard_Sedan");
                modelSessionBeanLocal.createNewModel(sunny, "Standard_Sedan");
                modelSessionBeanLocal.createNewModel(eClass, "Luxury_Sedan");
                modelSessionBeanLocal.createNewModel(fiveseries, "Luxury_Sedan");
                modelSessionBeanLocal.createNewModel(a6, "Luxury_Sedan");              
  
                Car c1 = new Car("SS00A1TC", CarStatus.AVAILABLE);
                Car c2 = new Car("SS00A2TC", CarStatus.AVAILABLE);
                Car c3 = new Car("SS00A3TC", CarStatus.AVAILABLE);
                Car c4 = new Car("SS00B1HC",  CarStatus.AVAILABLE);
                Car c5 = new Car("SS00B2HC",CarStatus.AVAILABLE);
                Car c6 = new Car("SS00B3HC", CarStatus.AVAILABLE);
                Car c7 = new Car("SS00C1NS", CarStatus.AVAILABLE);
                Car c8 = new Car("SS00C2NS",  CarStatus.AVAILABLE);
                Car c9 = new Car("LS00A4ME",  CarStatus.AVAILABLE);
                Car c10 = new Car("LS00B4B5",  CarStatus.AVAILABLE);
                Car c11 = new Car("LS00C4A6",  CarStatus.AVAILABLE);

                carSessionBeanLocal.createNewCar(c1, "Corolla", "Outlet_A");
                carSessionBeanLocal.createNewCar(c2, "Corolla", "Outlet_A");
                carSessionBeanLocal.createNewCar(c3, "Corolla", "Outlet_A");
                carSessionBeanLocal.createNewCar(c4, "Civic", "Outlet_B");
                carSessionBeanLocal.createNewCar(c5, "Civic", "Outlet_B");
                carSessionBeanLocal.createNewCar(c6, "Civic", "Outlet_B");
                carSessionBeanLocal.createNewCar(c7, "Sunny", "Outlet_C");
                carSessionBeanLocal.createNewCar(c8, "Sunny", "Outlet_C");
                carSessionBeanLocal.createNewCar(c9, "E Class", "Outlet_A");
                carSessionBeanLocal.createNewCar(c10, "5 Series", "Outlet_B");
                carSessionBeanLocal.createNewCar(c11, "A6", "Outlet_C");

              
                SimpleDateFormat formatterDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                RentalRate r1 = new RentalRate("Standard_Sedan-Default", RentalRateType.DEFAULT, new BigDecimal(100));              
                RentalRate r2 = new RentalRate("Standard_Sedan-Weekend Promo", new BigDecimal(80), RentalRateType.PROMOTION,formatterDateTime.parse("09-12-2022 12:00"), formatterDateTime.parse("11-12-2022 00:00"));
                RentalRate r3 = new RentalRate("Family_Sedan-Default", RentalRateType.DEFAULT, new BigDecimal(200));
                RentalRate r4 = new RentalRate("Luxury_Sedan-Default", RentalRateType.DEFAULT,new BigDecimal(300));
                RentalRate r5 = new RentalRate("Luxury_Sedan-Monday", new BigDecimal(310), RentalRateType.PEAK, formatterDateTime.parse("05-12-2022 00:00"), formatterDateTime.parse("05-12-2022 23:59"));
                RentalRate r6 = new RentalRate("Luxury_Sedan-Tuesday", new BigDecimal(320), RentalRateType.PEAK, formatterDateTime.parse("06-12-2022 00:00"), formatterDateTime.parse("06-12-2022 23:59"));              
                RentalRate r7 = new RentalRate("Luxury_Sedan-Wednesday", new BigDecimal(330), RentalRateType.PEAK, formatterDateTime.parse("07-12-2022 00:00"), formatterDateTime.parse("07-12-2022 23:59")); 
                RentalRate r8 = new RentalRate("Luxury_Sedan-Weekday Promo", new BigDecimal(250), RentalRateType.PROMOTION, formatterDateTime.parse("07-12-2022 12:00"), formatterDateTime.parse("08-12-2022 12:00"));
                RentalRate r9 = new RentalRate("SUV_and_Minivan-Default", RentalRateType.DEFAULT, new BigDecimal(400));

                rentalRateSessionBeanLocal.createRentalRate(r1, "Standard_Sedan");
                rentalRateSessionBeanLocal.createRentalRate(r2, "Standard_Sedan");  
                rentalRateSessionBeanLocal.createRentalRate(r3, "Family_Sedan");
                rentalRateSessionBeanLocal.createRentalRate(r4, "Luxury_Sedan");
                rentalRateSessionBeanLocal.createRentalRate(r5, "Luxury_Sedan");
                rentalRateSessionBeanLocal.createRentalRate(r6, "Luxury_Sedan");
                rentalRateSessionBeanLocal.createRentalRate(r7, "Luxury_Sedan");
                rentalRateSessionBeanLocal.createRentalRate(r8, "Luxury_Sedan");
                rentalRateSessionBeanLocal.createRentalRate(r9, "SUV_and_Minivan");

                Partner partner = new Partner("Holiday.com");
                partnerSessionBeanLocal.createNewPartner(partner);
                
            } catch (CategoryDoesNotExistException ex) {
                System.out.println("Categories do not exist");
            } catch (DuplicateOutletException ex) {
                System.out.println("Outlet already exists");
            } catch (OutletDoesNotExistException ex) {
                System.out.println("Outlet does not exist");
            } catch (DuplicateUserException ex) {
                System.out.println("Username already exists");
            } catch (DuplicateCategoryException ex) {
                System.out.println("Category already exists");
            } catch (ModelDoesNotExistException ex) {
                System.out.println("Model does not exist");
            } catch (DuplicateModelException ex) {
                System.out.println("Model already exists");
            } catch (DuplicateCarException ex) {
                System.out.println("Car already exists");
            } catch (DuplicateRentalRateException ex) {
                Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            } catch (DuplicationPartnerException ex) {
                Logger.getLogger(DataInitSessionBean.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    } 
}
