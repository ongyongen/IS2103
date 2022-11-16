/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carmsreservationclient;

import ejb.session.stateless.CarReservationSessionBeanRemote;
import ejb.session.stateless.CarSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.OutletSessionBeanRemote;
import ejb.session.stateless.RentalRateSessionBeanRemote;
import entity.Car;
import entity.CarReservation;
import entity.Category;
import entity.Customer;
import entity.Outlet;
import enumeration.CarReservationPaymentStatus;
import exception.CarDoesNotExistException;
import exception.CategoryDoesNotExistException;
import exception.ModelDoesNotExistException;
import exception.OutletDoesNotExistException;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ongyongen
 */
public class ReservationOperations {

    private static Customer customer;
    private static CustomerSessionBeanRemote customerSessionBeanRemote;
    private static CarReservationSessionBeanRemote carReservationSessionBeanRemote;
    private static CarSessionBeanRemote carSessionBeanRemote;
    private static RentalRateSessionBeanRemote rentalRateSessionBeanRemote;
    private static OutletSessionBeanRemote outletSessionBeanRemote;
    
    public ReservationOperations(
            Customer customer, 
            CustomerSessionBeanRemote customerSessionBeanRemote,
            CarReservationSessionBeanRemote carReservationSessionBeanRemote,
            CarSessionBeanRemote carSessionBeanRemote,
            RentalRateSessionBeanRemote rentalRateSessionBeanRemote,
            OutletSessionBeanRemote outletSessionBeanRemote) {
        
        this.customer = customer;
        this.customerSessionBeanRemote = customerSessionBeanRemote;
        this.carReservationSessionBeanRemote = carReservationSessionBeanRemote;
        this.carSessionBeanRemote = carSessionBeanRemote;
        this.rentalRateSessionBeanRemote = rentalRateSessionBeanRemote;
        this.outletSessionBeanRemote = outletSessionBeanRemote;
    }
    
    
    /** Placeholder for now **/
    public void doReservationOperations() throws ParseException, CarDoesNotExistException, OutletDoesNotExistException, CategoryDoesNotExistException, ModelDoesNotExistException {
      Integer response = 0;
      Scanner scanner = new Scanner(System.in);
      while (true) {
          System.out.println(" ");
          System.out.println("CaRMS Reservation Client - Customer Portal (Logged in)");
          System.out.println("1. Reserve Car");
          System.out.println("2. Cancel Reservation");
          System.out.println("3. View Reservation Details");
          System.out.println("4. View All My Reservations");
          System.out.println("5. Search car to reserve");
          System.out.println("6. Log out");
          System.out.print(">");
          response = scanner.nextInt();
          
          System.out.println(" ");
                   
          SimpleDateFormat formatterDateTime = new SimpleDateFormat("dd-MM-yy HH:mm");

          if (response == 1) {
            
            System.out.print("Enter the pick up outlet of the car to reserve:\n>");
            String pickupOutlet = scanner.next().trim();
            
            System.out.print("Enter the drop off outlet of the car to reserve:\n>");
            String dropoffOutlet = scanner.next().trim();
            
            System.out.print("Enter the pick up date for the car:\n>");
            String pickupD = scanner.next().trim();
            
            System.out.print("Enter your pick up time in 24 hour format ie 22:30:\n>");
            String pickupT = scanner.next().trim();
            
            System.out.print("Enter the drop off date for the car:\n>");
            String dropoffD= scanner.next().trim();
            
            System.out.print("Enter your drop off time in 24 hour format ie 22:30:\n>");
            String dropoffT = scanner.next().trim();
            
            System.out.print("Enter your credit card number for reservation:\n>");
            String ccNos = scanner.next().trim();
            
            //System.out.print("Enter the model of the car to reserve:\n>");
            //String modelName = scanner.next().trim();
            
            System.out.print("Choose whether you want to make the payment now or when you collect the car :\n "
                   + "1: Pay now\n "
                   + "2: Pay during car collection\n>");
            
            Integer paymentOption = scanner.nextInt();
            CarReservationPaymentStatus payment = paymentOption == 1 ? 
                    CarReservationPaymentStatus.PAID_AT_BOOKING : 
                    CarReservationPaymentStatus.PAY_AT_COLLECTION;

            String pickupDTStr = pickupD + " " + pickupT;
            String dropoffDTStr = dropoffD + " " + dropoffT;
     
            Date pickupDT = formatterDateTime.parse(pickupDTStr);
            Date dropoffDT = formatterDateTime.parse(dropoffDTStr);
                     
            System.out.print("Choose whether you want to find a car to reserve by category or by category and model :\n "
             + "1: By category (only)\n "
             + "2: By category and model\n>");
            
            Integer filterOption = scanner.nextInt();

            
            if (filterOption == 1) {
                System.out.print("Enter the category of the car to reserve:\n>");
                String categoryName = scanner.next().trim();
                
                Integer canMakeReservation = this.carReservationSessionBeanRemote.nosCanMakeReservationByCategory(categoryName, pickupDT, dropoffDT, pickupOutlet);
                if (canMakeReservation <= 0) {
                    System.out.println("No car of the desired category is available for booking at the desired outlet");
                } else {
                    try {
                        BigDecimal price = this.rentalRateSessionBeanRemote.calculateTotalRentalRate(categoryName, pickupDTStr, dropoffDTStr);
                        Long id = this.carReservationSessionBeanRemote.createNewCarReservationByCategory(this.customer.getEmail(), categoryName, pickupDT, dropoffDT, pickupOutlet, dropoffOutlet, ccNos, price, payment);
                        if (paymentOption == 1) {
                            System.out.println("Car reservation with id " + id + " is made. Payment of $" + price + " is charged to your bank account through your credit card");
                        } else {
                            System.out.println("Car reservation with id " + id + " is made. Please make your payment of $" + price + " on the day of your car collection");
                        }
                    } catch (CategoryDoesNotExistException ex) {
                        Logger.getLogger(ReservationOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            } 
            
            if (filterOption == 2) {              
                System.out.print("Enter the category of the car to reserve:\n>");
                String categoryName = scanner.next().trim();

                System.out.print("Enter the model of the car to reserve:\n>");
                String modelName = scanner.next().trim();
            
                Integer canMakeReservation = this.carReservationSessionBeanRemote.nosCanMakeReservationByCategoryAndModel(categoryName, modelName, pickupDT, dropoffDT, pickupOutlet);

                if (canMakeReservation <= 0) {
                    System.out.println("No car of the desired category is available for booking at the desired outlet");
                } else {
                    try {
                        BigDecimal price = this.rentalRateSessionBeanRemote.calculateTotalRentalRate(categoryName, pickupDTStr, dropoffDTStr);
                        Long id = this.carReservationSessionBeanRemote.createNewCarReservationByCategoryAndModel(this.customer.getEmail(), categoryName, modelName, pickupDT, dropoffDT, pickupOutlet, dropoffOutlet, ccNos, price, payment);
                        if (paymentOption == 1) {
                            System.out.println("Car reservation with id " + id + " is made. Payment of $" + price + " is charged to your bank account through your credit card");
                        } else {
                            System.out.println("Car reservation with id " + id + " is made. Please make your payment of $" + price + " on the day of your car collection");
                        }
                    } catch (CategoryDoesNotExistException ex) {
                        Logger.getLogger(ReservationOperations.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
            } 

        }
          
        if (response == 2) {
            System.out.print("Enter the ID of your reservation:\n>");
            Integer idInput = scanner.nextInt();
            Long id = new Long(idInput);
            
            System.out.print("Enter your credit card details:\n>");
            String card = scanner.next().trim();

            System.out.print("Enter the current date:\n>");
            String date = scanner.next().trim();
            
            System.out.print("Enter the time right now:\n>");
            String time = scanner.next().trim();
            
            SimpleDateFormat formatterDateTime2 = new SimpleDateFormat("dd-MM-yy HH:mm");
            Date currentDT = formatterDateTime.parse(date + " " + time);
            
            String result = this.carReservationSessionBeanRemote.cancelReservation(id, currentDT, card);
            
            System.out.println(result);
        }
          
        if (response == 3) {
              System.out.print("Enter the ID of your reservation:\n>");
              Integer idInput = scanner.nextInt();
              Long id = new Long(idInput);
              CarReservation r = this.carReservationSessionBeanRemote.retrieveCarReservationById(id);
              System.out.println("Details of your reservation:");

              System.out.printf("%20s%20s%20s%40s%40s%20s%20s\n", 
                      "Reservation Id",
                      "Pickup Outlet", "Dropoff Outlet",
                      "Pickup Time", "Dropoff Time",
                      "Rental Fee", "Payment Status");

              System.out.printf("%20s%20s%20s%40s%40s%20s%20s\n",
                      r.getCarReservationId(),
                      r.getPickUpOutlet().getOutletName(),
                      r.getDropOffOutlet().getOutletName(),
                      r.getPickupDateTime(),
                      r.getDropoffDateTime(),
                      r.getRentalFee(),
                      r.getPayment().toString());
              System.out.println(" ");             
        } 
        
        if (response == 4) {
            
            String email = customer.getEmail();
            
            System.out.println("Details of all your reservations:");
            
            System.out.printf("%20s%20s%20s%40s%40s%20s%20s\n", 
                        "Reservation ID",
                    "Pickup Outlet", "Dropoff Outlet",
                    "Pickup Time", "Dropoff Time",
                    "Rental Fee", "Payment Status");

            //Long id = this.customer.getCustomerId();
            List<CarReservation> reservations = this.carReservationSessionBeanRemote.retrieveCarReservationByCustomer(email);
            for (CarReservation r : reservations) {
                reservations.size();
                System.out.printf("%20s%20s%20s%40s%40s%20s%20s\n",
                      r.getCarReservationId(),
                      r.getPickUpOutlet().getOutletName(),
                      r.getDropOffOutlet().getOutletName(),
                      r.getPickupDateTime(),
                      r.getDropoffDateTime(),
                      r.getRentalFee(),
                      r.getPayment().toString());
            }
            System.out.println(" ");             
        }
        
         if (response == 5) {
                    
            SimpleDateFormat formatterDate = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");
            SimpleDateFormat formatterDateTimeSearch = new SimpleDateFormat("dd-MM-yyyy HH:mm");

            System.out.print("Enter the name of your desired outlet to pick up the car:\n>");
            String pickupName = scanner.next().trim();
            Outlet pickupOutlet = this.outletSessionBeanRemote.retrieveOutletByName(pickupName);    

            System.out.print("Enter the name of your desired outlet to drop off the car:\n>");
            String dropoffName = scanner.next().trim();
            Outlet dropoffOutlet = this.outletSessionBeanRemote.retrieveOutletByName(dropoffName);

            System.out.print("Enter your pick up date in dd-MM-yyyy format:\n>");       
            String pickupD = scanner.next().trim();
            Date pickupDate = formatterDate.parse(pickupD);

            System.out.print("Enter your pick up time in hh:mm am/pm format:\n>");       
            String pickupT = scanner.next().trim();
            Date pickupTime = formatterTime.parse(pickupT);

            System.out.print("Enter your drop off date in dd-MM-yyyy format:\n>");
            String dropoffD = scanner.next().trim();
            Date dropoffDate = formatterDate.parse(dropoffD);

            System.out.print("Enter your drop off time in hh:mm format:\n>");
            String dropoffT = scanner.next().trim();
            Date dropoffTime = formatterTime.parse(dropoffT);                  

            Date pickupDateTime =  formatterDateTimeSearch.parse(pickupD + " " + pickupT);
            Date dropoffDateTime = formatterDateTimeSearch.parse(dropoffD + " " + dropoffT);

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
                dropoffTime.before(dropoffClose) ||
                (pickupTime.equals(pickupOpen) && dropoffTime.equals(dropoffOpen))) {

                System.out.print("Choose whether you want to search by category or category, make and model :\n "
                + "1: Search by category\n "
                + "2: Search by category, make and model\n "
                + "3: Search by outlet\n> ");

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
                    HashMap<Category, Integer> countOfCatReserved = new HashMap<Category, Integer>();
                    HashMap<Category, BigDecimal> rentalRates = new HashMap<Category, BigDecimal>();

                    List<Car> allCars = this.carSessionBeanRemote.retrieveAllCars();
                    for (Car car : allCars) {
                        Category cat = car.getModel().getCategory();
                        if (countOfCatReserved.containsKey(cat)) {
                            Integer count = countOfCatReserved.get(cat);
                            Integer newCount = count + 1;
                            countOfCatReserved.put(cat, newCount);
                        } else {
                            countOfCatReserved.put(cat, 1);
                            BigDecimal rate = this.rentalRateSessionBeanRemote.calculateTotalRentalRate(cat.getName(), pickupDateTimeStr, dropoffDateTimeStr);
                            rentalRates.put(cat, rate);
                        }
                    }
   
                    List<CarReservation> reservations = this.carReservationSessionBeanRemote.retrieveAllCarReservations();
                 
                    
                    for (CarReservation r : reservations) {
                        Category cat = r.getCarCategory();
                        
                        Date reservationPickupDT = r.getPickupDateTime();
                        Date reservationDropoffDT = r.getDropoffDateTime();
             
                        boolean cancreateNewRes = this.carReservationSessionBeanRemote.canCreateReservation(reservationPickupDT, reservationDropoffDT, pickupDateTime, dropoffDateTime);
                        if (cancreateNewRes == false) {
                            Integer count = countOfCatReserved.get(cat);
                            Integer newCount = count - 1;
                            countOfCatReserved.put(cat, newCount);
                        }
                    }
                    
                    System.out.printf("%30s%50s%50s\n", "category","number of cars available", "rate");
                    boolean resultPrinted = false;
                    for (Map.Entry<Category, Integer> entry : countOfCatReserved.entrySet()) {
                        Category cat = entry.getKey();
                        Integer count = entry.getValue();

                        if (count > 0) {
                            resultPrinted = true;
                            System.out.printf("%30s%50s%50s\n", cat.getName(), count, rentalRates.get(cat));
                        } else {
                            resultPrinted = true;
                            System.out.printf("%30s%50s%50s\n", cat.getName(), "No cars left for reservation" , "Nil");    
                        }                         
                    }
                    
                    if (resultPrinted == false) {
                        System.out.println("No cars available");
                    }

                    System.out.println(" ");
                }

                    /**ArrayList<Car> available = new ArrayList<Car>();
                    for (Car car : allCars) {

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
                        System.out.println(" ");**/
                
                    }
             else {
                    System.out.println("No car is available for booking at the desired outlet at the desired time");

            }                            
        }
        
        if (response == 6) {
            break;
        }
        
      }
    }
}
