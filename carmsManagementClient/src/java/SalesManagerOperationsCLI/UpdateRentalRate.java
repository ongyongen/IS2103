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
import exception.RentalRateDoesNotExistException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author ongyongen
 */
public class UpdateRentalRate {
    
    private final RentalRateSessionBeanRemote rentalRateSessionBeanRemote;
    
    public UpdateRentalRate(RentalRateSessionBeanRemote rentalRateSessionBeanRemote) {
        this.rentalRateSessionBeanRemote = rentalRateSessionBeanRemote;
    }
    
    public void doUpdateRentalRate() {
        Scanner scanner = new Scanner(System.in);

        try {
                System.out.print("Enter the name of the rental rate to update:\n>");
                String oldName = scanner.next().trim();
                RentalRate rate = this.rentalRateSessionBeanRemote.retrieveRentalRatesByName(oldName);

                System.out.println("Details of the rental rate is:");
                System.out.printf("%20s%20s%20s%40s%40s%20s\n", "name", "rate per day", "category", "start date", "end date", "status");

                System.out.printf("%20s%20s%20s%40s%40s%20s\n",
                        rate.getName(),
                        rate.getRatePerDay(), 
                        rate.getCategory().getName(),
                        rate.getStartDate(),
                        rate.getEndDate(),
                        rate.getStatus());

                System.out.println(" ");

                System.out.print("Enter the new name of the rental rate:\n>");
                String newName = scanner.next().trim();

                System.out.print("Enter the new rental rate per day:\n>");
                BigDecimal newRentalRate = new BigDecimal(scanner.next().trim());

                System.out.print("Select the car's new rental rate type:\n "
                        + "1: Default\n "
                        + "2: Promotion\n "
                        + "3: Peak\n>");

                Integer newTypeSelected = scanner.nextInt();
                RentalRateType newType =
                        newTypeSelected == 1 ? RentalRateType.DEFAULT :
                        newTypeSelected == 2 ? RentalRateType.PROMOTION :
                        RentalRateType.PEAK;
                
                
                SimpleDateFormat formatterDateTime = new SimpleDateFormat("dd-MM-yyyy HH:mm");

                System.out.print("Enter the new date at which the rental rate's validity period starts in dd-mm-yyyy format:\n>");
                String startD = scanner.next();
            
                System.out.print("Enter the new time at which the rental rate's validity period starts in HH:mm format:\n>");
                String startT = scanner.next();

                Date startDateTime = formatterDateTime.parse(startD + " " + startT);

                System.out.print("Enter the new date at which the rental rate's validity period ends in dd-mm-yyyy format:\n>");
                String endD = scanner.next();
            
                System.out.print("Enter the new time at which the rental rate's validity period ends in HH:mm format:\n>");
                String endT = scanner.next();

                Date endDateTime = formatterDateTime.parse(startD + " " + startT);

                System.out.print("Enter the new car category that the rate applies to:\n>");
                String newCat = scanner.next().trim();

                RentalRate newRate = new RentalRate(newName, newRentalRate, newType, startDateTime, endDateTime);

                this.rentalRateSessionBeanRemote.updateRentalRate(oldName, newRate, newCat);

                System.out.println("Rental rate is updated with the name of " + newName);
                System.out.println(" ");
            } catch (ParseException ex) {
                System.out.println("Date input format is wrong. Please enter a valid date input");
            } catch (RentalRateDoesNotExistException ex) {
                System.out.println("There is no rental rate record with the rental rate name provided");
            } catch (CategoryDoesNotExistException ex) {
                System.out.println("There is no category record with the category name provided");
            } 
            //catch (EndDateBeforeStartDateException ex) {
               //System.out.println("End date cannot be before start date.");
            //}
    }
    
}
