/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SalesManagerOperationsCLI;

import ejb.session.stateless.RentalRateSessionBeanRemote;
import entity.RentalRate;
import java.util.List;

/**
 *
 * @author ongyongen
 */
public class RetrieveAllRentalRates {
    
    private final RentalRateSessionBeanRemote rentalRateSessionBeanRemote;
    
    public RetrieveAllRentalRates(RentalRateSessionBeanRemote rentalRateSessionBeanRemote) {
        this.rentalRateSessionBeanRemote = rentalRateSessionBeanRemote;
        
    }
    
    public void doRetrieveAllRentalRates() {
        List<RentalRate> rates = this.rentalRateSessionBeanRemote.retrieveAllRentalRates();
        System.out.println("Details of all rental rates are:");
        System.out.printf("%40s%40s%40s%40s%40s\n", "name", "rate per day", "category", "start date", "end date");

        for (RentalRate rate : rates) {
            System.out.printf("%40s%40s%40s%40s%40s\n", 
                    rate.getName(), 
                    rate.getRatePerDay(), 
                    rate.getCategory().getName(), 
                    rate.getStartDate(),
                    rate.getEndDate());
        }
        System.out.println(" ");
    }    
}
