/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SalesManagerOperationsCLI;

import ejb.session.stateless.RentalRateSessionBeanRemote;
import entity.RentalRate;
import exception.RentalRateDoesNotExistException;
import java.util.Scanner;

/**
 *
 * @author ongyongen
 */
public class DeleteRentalRate {
    
    private final RentalRateSessionBeanRemote rentalRateSessionBeanRemote;
    
    public DeleteRentalRate(RentalRateSessionBeanRemote rentalRateSessionBeanRemote) {
        this.rentalRateSessionBeanRemote = rentalRateSessionBeanRemote;
    }
    
    public void doDeleteRentalRate() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter the name of the rental rate to delete:\n>");
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

            System.out.print("Re-enter the name of the rental rate to confirm deletion:\n>");
            String newName = scanner.next().trim();
            String outputMsg = this.rentalRateSessionBeanRemote.deleteRentalRate(newName);

            System.out.println(outputMsg);
            System.out.println(" ");
        } catch (RentalRateDoesNotExistException ex) {
            System.out.println("There is no rental rate record with the name provided");
        }
        
    } 
}
