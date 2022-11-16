/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carmsclient;

import ejb.session.stateless.CarReservationSessionBeanRemote;
import ejb.session.stateless.EmployeeSessionBeanRemote;
import ejb.session.stateless.RentalRateSessionBeanRemote;
import entity.Employee;
import java.util.Scanner;

/**
 *
 * @author ongyongen
 */
public class CustomerServiceExecutiveOperations {

    private static CarReservationSessionBeanRemote carReservationSessionBeanRemote;
    
    public CustomerServiceExecutiveOperations(CarReservationSessionBeanRemote carReservationSessionBeanRemote) {
        this.carReservationSessionBeanRemote = carReservationSessionBeanRemote;
    }
    
    public void doCustomerServiceExecutiveOperations() {
        while (true) {
            System.out.println(" ");
            System.out.println("CaRMS Management Client - Customer Service Executive Portal");
            System.out.println("1. Pickup Car");
            System.out.println("2. Return Car");
            System.out.println("3. Log out");
            System.out.print(">");

            Integer response = 0;
            Scanner scanner = new Scanner(System.in);
            response = scanner.nextInt();
            
            System.out.println(" ");

            if (response == 1) {
                System.out.print("Enter your car reservation ID:\n>");
                Integer idInput = scanner.nextInt();
                Long id = new Long(idInput);
                
                String result = this.carReservationSessionBeanRemote.registerCarPickup(id);
                System.out.println(result);
            }

            if (response == 2) {
                System.out.print("Enter your car reservation ID:\n>");
                Integer idInput = scanner.nextInt();
                Long id = new Long(idInput);
                
                String result = this.carReservationSessionBeanRemote.registerCarReturn(id);
                System.out.println(result);
            }

            if (response == 3) {
                break;
            }
        }
    }
    
}
