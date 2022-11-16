/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carmsreservationclient;

import ejb.session.stateless.CarReservationSessionBeanRemote;
import ejb.session.stateless.CarSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.EJBTimerSessionBeanRemote;
import ejb.session.stateless.OutletSessionBeanRemote;
import ejb.session.stateless.RentalRateSessionBeanRemote;
import entity.Car;
import entity.Customer;
import entity.Employee;
import entity.Outlet;
import enumeration.CarStatus;
import exception.CarDoesNotExistException;
import exception.InvalidLoginCredentialException;
import exception.OutletDoesNotExistException;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
public class CreateAccountLoginOperation {
    
    private static CustomerSessionBeanRemote customerSessionBeanRemote;
    private static OutletSessionBeanRemote outletSessionBeanRemote;
    private static CarSessionBeanRemote carSessionBeanRemote;
    private static CarReservationSessionBeanRemote carReservationSessionBeanRemote;
    private static RentalRateSessionBeanRemote rentalRateSessionBeanRemote;
    
    private static Customer customer;
    private boolean logOutAfterLogIn;
    
    private static EJBTimerSessionBeanRemote eJBTimerSessionBeanRemote;
    
    

    public CreateAccountLoginOperation() {
        
    }
    
    public CreateAccountLoginOperation(CustomerSessionBeanRemote customerSessionBeanRemote,
            OutletSessionBeanRemote outletSessionBeanRemote,
            CarSessionBeanRemote carSessionBeanRemote,
            CarReservationSessionBeanRemote carReservationSessionBeanRemote,
            RentalRateSessionBeanRemote rentalRateSessionBeanRemote,
            EJBTimerSessionBeanRemote eJBTimerSessionBeanRemote) {
        this.customerSessionBeanRemote = customerSessionBeanRemote;
        this.outletSessionBeanRemote = outletSessionBeanRemote;
        this.carSessionBeanRemote = carSessionBeanRemote;
        this.carReservationSessionBeanRemote = carReservationSessionBeanRemote;
        this.rentalRateSessionBeanRemote = rentalRateSessionBeanRemote;
        this.eJBTimerSessionBeanRemote = eJBTimerSessionBeanRemote;
    }

    public static Customer getCustomer() {
        return customer;
    }

    public static void setCustomer(Customer customer) {
        CreateAccountLoginOperation.customer = customer;
    }

    //CreateAccountLoginOperation(CustomerSessionBeanRemote customerSessionBeanRemote, OutletSessionBeanRemote outletSessionBeanRemote, CarSessionBeanRemote carSessionBeanRemote, CarReservationSessionBeanRemote carReservationSessionBeanRemote) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    //}
    
    public boolean isLogOutAfterLogIn() {
        return logOutAfterLogIn;
    }

    public void setLogOutAfterLogIn(boolean logOutAfterLogIn) {
        this.logOutAfterLogIn = logOutAfterLogIn;
    }
    

    public void doCreateAccountLogin() throws ParseException, CarDoesNotExistException, OutletDoesNotExistException {
       
        while (true) {
            System.out.println(" ");
            System.out.println("CaRMS Reservation Client - Customer Portal");
            System.out.println("1: Create a new account");
            System.out.println("2: Log in to existing account");           
            System.out.println("3: Search car");
            System.out.println("4: Exit");

            System.out.print(">");

            Scanner scanner = new Scanner(System.in);

            try {

                Integer response = scanner.nextInt();

                if (response == 1) {
                    System.out.print("Enter your name or email address:\n>");
                    String email = scanner.next().trim();

                    System.out.print("Enter your password for this account:\n>");
                    String password = scanner.next().trim();
                    
                    System.out.print("Enter your phone number for this account:\n>");
                    String phoneNos = scanner.next().trim();
                    
                    System.out.print("Enter your passport number for this account:\n>");
                    String passportNos = scanner.next().trim();
                    
                    System.out.print("Enter your credit card number for this account:\n>");
                    String ccNos = scanner.next().trim();
                    
                    Customer customer = new Customer(email, password, phoneNos, passportNos, ccNos);

                    customerSessionBeanRemote.createNewCustomer(customer);
                    
                    System.out.println("Your account is created! You can log in to your account now");
                }
                    
                if (response == 2) {
                     try {                        
                        System.out.print("Enter your name or email address:\n>");
                        String email = scanner.next().trim();

                        System.out.print("Enter your password for this account:\n>");
                        String password = scanner.next().trim();
                        
                        Customer customer = customerSessionBeanRemote.retrieveCustomerByEmailAndPassword(email, password);
                        this.setCustomer(customer);
                        System.out.println("Credentials are correct. You are logged in.");
                        break;
                    } catch (InvalidLoginCredentialException ex) {
                        System.out.println("Login credentials are wrong. Please try again.");
                    }               
                }      
     
                if (response == 3) {
                    
                    SimpleDateFormat formatterDate = new SimpleDateFormat("dd-M-yyyy");
                    SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");
                    SimpleDateFormat formatterDateTime = new SimpleDateFormat("dd-M-yyyy HH:mm");
                   
                    System.out.print("Enter the name of your desired outlet to pick up the car:\n>");
                    String pickupName = scanner.next().trim();
                    Outlet pickupOutlet = CreateAccountLoginOperation.outletSessionBeanRemote.retrieveOutletByName(pickupName);    
                   
                    System.out.print("Enter the name of your desired outlet to drop off the car:\n>");
                    String dropoffName = scanner.next().trim();
                    Outlet dropoffOutlet = this.outletSessionBeanRemote.retrieveOutletByName(dropoffName);
            
                    System.out.print("Enter your pick up date in dd-M-yyyy format:\n>");       
                    String pickupD = scanner.next().trim();
                    Date pickupDate = formatterDate.parse(pickupD);
                    
                    System.out.print("Enter your pick up time in hh:mm am/pm format:\n>");       
                    String pickupT = scanner.next().trim();
                    Date pickupTime = formatterTime.parse(pickupT);
                    
                    System.out.print("Enter your drop off date in dd-M-yyyy format:\n>");
                    String dropoffD = scanner.next().trim();
                    Date dropoffDate = formatterDate.parse(dropoffD);
                    
                    System.out.print("Enter your drop off time in hh:mm format:\n>");
                    String dropoffT = scanner.next().trim();
                    Date dropoffTime = formatterTime.parse(dropoffT);                  

                    Date pickupDateTime =  formatterDateTime.parse(pickupD + " " + pickupT);
                    Date dropoffDateTime = formatterDateTime.parse(dropoffD + " " + dropoffT);
                   
                    String pickupDateTimeStr =  pickupD + " " + pickupT;
                    String dropoffDateTimeStr = dropoffD + " " + dropoffT;
                   
                    Time pickupOpen = pickupOutlet.getOpeningHour();
                    Time pickupClose = pickupOutlet.getClosingHour();
                    Time dropoffOpen = dropoffOutlet.getOpeningHour();
                    Time dropoffClose = dropoffOutlet.getClosingHour();
                    

                    if (pickupTime.before(pickupOpen) || pickupTime.after(pickupClose)) {
                        System.out.println("Pick up time is beyond the range of the outlet's opening hours");
                    }
                    
                    if (dropoffTime.before(dropoffOpen) || dropoffOpen.after(dropoffClose)) {
                        System.out.println("Drop off time is beyond the range of the outlet's opening hours");
                    }
                    
                    
                    if (pickupTime.after(pickupOpen) == true &&
                        pickupTime.before(pickupClose) == true &&
                        dropoffTime.after(dropoffOpen) == true &&
                        dropoffTime.before(dropoffClose)) {
                        
                        System.out.print("Choose whether you want to search by category or category, make and model :\n "
                        + "1: Search by category\n "
                        + "2: Search by category, make and model\n "
                        + "3: Search by outlet>");

                        Integer newTypeSelected = scanner.nextInt();
                        
                        if (newTypeSelected == 1) {
                            System.out.print("Enter your desired Car category:\n>");
                            String catName = scanner.next().trim();                    
                            List<Car> filteredCars = this.carSessionBeanRemote.retrieveCarsByCategory(catName);
                            ArrayList<Car> available = new ArrayList<Car>();
                            for (Car car : filteredCars) {

                                boolean canMakeReservation = this.carReservationSessionBeanRemote.canMakeReservation(car.getLicensePlateNos(), pickupDateTime, dropoffDateTime, pickupOutlet.getOutletName());

                                if (canMakeReservation == true) {
                                    available.add(car);
                                }
                            }

                            if (available.size() == 0) {
                                System.out.println("No car of the desired category is available for booking at the desired outlet");
                            } else {
                                System.out.println("Details of all cars available for reservation are:");
                                System.out.printf("%25s%25s%25s%35s%25s\n", "category", "make", "model", "license plate number", "outlet");

                                for (Car c : available) {
                                    System.out.printf("%25s%25s%25s%35s%25s\n", 
                                    c.getModel().getCategory().getName(),
                                    c.getMake(), 
                                    c.getModel().getModelName(),
                                    c.getLicensePlateNos(),
                                    c.getOutlet().getOutletName());        
                                }                          
                                System.out.println(" ");

                                BigDecimal price = this.rentalRateSessionBeanRemote.calculateTotalRentalRate(catName, pickupDateTimeStr, dropoffDateTimeStr);
                                System.out.println("The rental cost is " + price);

                            }  

                        } if (newTypeSelected == 2) {
                            System.out.print("Enter your desired Car category:\n>");
                            String catName = scanner.next().trim();   
                                                     
                            System.out.print("Enter your desired Car model:\n>");
                            String modelName = scanner.next().trim();   
                            List<Car> filteredCars = this.carSessionBeanRemote.retrieveCarsByCategoryAndModel(catName, modelName);
                            ArrayList<Car> available = new ArrayList<Car>();
                            for (Car car : filteredCars) {

                                boolean canMakeReservation = this.carReservationSessionBeanRemote.canMakeReservation(car.getLicensePlateNos(), pickupDateTime, dropoffDateTime, pickupOutlet.getOutletName());

                                if (canMakeReservation == true) {
                                    available.add(car);
                                }
                            }

                            if (available.size() == 0) {
                                System.out.println("No car of the desired category is available for booking at the desired outlet");
                            } else {
                                System.out.println("Details of all cars available for reservation are:");
                                System.out.printf("%25s%25s%25s%35s%25s\n", "category", "make", "model", "license plate number", "outlet");

                                for (Car c : available) {
                                    System.out.printf("%25s%25s%25s%35s%25s\n", 
                                    c.getModel().getCategory().getName(),
                                    c.getMake(), 
                                    c.getModel().getModelName(),
                                    c.getLicensePlateNos(),
                                    c.getOutlet().getOutletName());        
                                }                          
                                System.out.println(" ");

                                BigDecimal price = this.rentalRateSessionBeanRemote.calculateTotalRentalRate(catName, pickupDateTimeStr, dropoffDateTimeStr);
                                System.out.println("The rental cost is " + price);
                            }
                        }
                        
                        if (newTypeSelected == 3) {
  
                            // do we need to filter by outlet?
                            List<Car> filteredCars = this.carSessionBeanRemote.retrieveAllCars();
                            ArrayList<Car> available = new ArrayList<Car>();
                            for (Car car : filteredCars) {

                                boolean canMakeReservation = this.carReservationSessionBeanRemote.canMakeReservation(car.getLicensePlateNos(), pickupDateTime, dropoffDateTime, pickupOutlet.getOutletName());

                                if (canMakeReservation == true) {
                                    available.add(car);
                                }
                            }

                            if (available.size() == 0) {
                                System.out.println("No car of the desired category is available for booking at the desired outlet");
                            } else {
                                System.out.println("Details of all cars available for reservation are:");
                                System.out.printf("%25s%25s%25s%35s%25s%30s\n", "category", "make", "model", "license plate number", "outlet", "rental rate");

                                for (Car c : available) {
                                    String cat = c.getModel().getCategory().getName();
                                    BigDecimal price = this.rentalRateSessionBeanRemote.calculateTotalRentalRate(cat, pickupDateTimeStr, dropoffDateTimeStr);
                                    System.out.printf("%25s%25s%25s%35s%25s%30s\n", 
                                    c.getModel().getCategory().getName(),
                                    c.getMake(), 
                                    c.getModel().getModelName(),
                                    c.getLicensePlateNos(),
                                    c.getOutlet().getOutletName(),
                                    price);        
                                }                          
                                System.out.println(" ");
                            }

                        }
                    } else {
                            System.out.println("No car is available for booking at the desired outlet at the desired time");

                    }                            
               }

                if (response == 4) {
                    this.setLogOutAfterLogIn(true);
                    System.out.println("You have exited the client");
                    break;
                }
                
                
            } catch (NullPointerException | InputMismatchException ex) {
                System.out.println("Menu option is invalid. Please select a valid option");
            }
     
        }
    }   
}
