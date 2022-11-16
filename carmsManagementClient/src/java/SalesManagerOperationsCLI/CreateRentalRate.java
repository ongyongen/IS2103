/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SalesManagerOperationsCLI;

import ejb.session.stateless.RentalRateSessionBeanRemote;
import entity.RentalRate;
import enumeration.RentalRateType;
import exception.CategoryDoesNotExistException;
import exception.DuplicateRentalRateException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class CreateRentalRate {
    
    private final RentalRateSessionBeanRemote rentalRateSessionBeanRemote;
  
   
    public CreateRentalRate(RentalRateSessionBeanRemote rentalRateSessionBeanRemote) {
        this.rentalRateSessionBeanRemote = rentalRateSessionBeanRemote;
    }
    
    public void doCreateRentalRate() {
        Scanner scanner = new Scanner(System.in);
        
        try {
                System.out.print("Enter the name of the rental rate:\n>");
                String name = scanner.next().trim();

                System.out.print("Enter the rental rate per day:\n>");
                BigDecimal rentalRate = new BigDecimal(scanner.next().trim());

                System.out.print("Select the car's rental rate type:\n "
                        + "1: Default\n "
                        + "2: Promotion\n "
                        + "3: Peak\n>");

                Integer typeSelected = scanner.nextInt();
                RentalRateType type =
                        typeSelected == 1 ? RentalRateType.DEFAULT :
                        typeSelected == 2 ? RentalRateType.PROMOTION :
                        RentalRateType.PEAK;

                SimpleDateFormat formatterDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm");

                System.out.print("Enter the date at which the rental rate's validity period starts in dd-mm-yyyy format:\n>");
                String startD = scanner.next();
            
                System.out.print("Enter the time at which the rental rate's validity period starts in HH:mm format:\n>");
                String startT = scanner.next();

                Date startDateTime = formatterDateTime.parse(startD + " " + startT);

                System.out.print("Enter the date at which the rental rate's validity period ends in dd-mm-yy format:\n>");
                String endD = scanner.next();
            
                System.out.print("Enter the time at which the rental rate's validity period ends in HH:mm format:\n>");
                String endT = scanner.next();

                Date endDateTime = formatterDateTime.parse(endD + " " + endT);

                //   if (endDate.before(startDate)) {
                //        throw new EndDateBeforeStartDateException();
                //  }             


                System.out.print("Enter the car category that the rate applies to:\n>");
                String cat = scanner.next().trim();

                RentalRate rate = new RentalRate(name, rentalRate, type, startDateTime, endDateTime);

                this.rentalRateSessionBeanRemote.createRentalRate(rate, cat);

                System.out.println("Rental rate is created with the name of " + name);
                System.out.println(" ");        
                
            } catch (ParseException ex) {
                System.out.println("Date input format is wrong. Please enter a valid date input");
                
            } catch (CategoryDoesNotExistException ex) {
                System.out.println("Car category that you entered does not exist. No new rental rate record is created");
            
            } catch (DuplicateRentalRateException ex) {
                System.out.println("There is already a rental rate record with the same name. "
                        + "No new rental rate record is created. Please use a different name.");
            
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a numeric amount for the rental rate");
            }
            // catch (EndDateBeforeStartDateException ex) {
            //      System.out.println("End date cannot be before start date.");
            // }

    }
}
