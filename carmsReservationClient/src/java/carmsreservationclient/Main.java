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
import ejb.session.stateless.EmployeeSessionBeanRemote;
import ejb.session.stateless.OutletSessionBeanRemote;
import ejb.session.stateless.RentalRateSessionBeanRemote;
import entity.Customer;
import exception.CarDoesNotExistException;
import exception.CategoryDoesNotExistException;
import exception.ModelDoesNotExistException;
import exception.OutletDoesNotExistException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

/**
 *
 * @author jiaen
 */
public class Main {

    @EJB
    private static EJBTimerSessionBeanRemote eJBTimerSessionBeanRemote;

    @EJB
    private static RentalRateSessionBeanRemote rentalRateSessionBeanRemote;

    @EJB
    private static CarReservationSessionBeanRemote carReservationSessionBeanRemote;

    @EJB
    private static CarSessionBeanRemote carSessionBeanRemote;

    @EJB
    private static OutletSessionBeanRemote outletSessionBeanRemote;

    @EJB
    private static CustomerSessionBeanRemote customerSessionBeanRemote;
    
    
      
    public static void main(String[] args) throws ParseException {
        
        CreateAccountLoginOperation createAccountLogin = new CreateAccountLoginOperation(
                customerSessionBeanRemote, 
                outletSessionBeanRemote,
                carSessionBeanRemote, 
                carReservationSessionBeanRemote,
                rentalRateSessionBeanRemote,
                eJBTimerSessionBeanRemote
        );
        
        while (true) {
            try {
                createAccountLogin.doCreateAccountLogin();
                if (createAccountLogin.isLogOutAfterLogIn() == true) {
                    break;
                }
                
                if (CreateAccountLoginOperation.getCustomer() != null) {
                    Customer customer = CreateAccountLoginOperation.getCustomer();
                    ReservationOperations reservationOperations = new ReservationOperations(customer, customerSessionBeanRemote, carReservationSessionBeanRemote,carSessionBeanRemote,rentalRateSessionBeanRemote, outletSessionBeanRemote);
                    reservationOperations.doReservationOperations();
                }
            } catch (CarDoesNotExistException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (OutletDoesNotExistException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (CategoryDoesNotExistException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ModelDoesNotExistException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (CreateAccountLoginOperation.getCustomer() != null) {
            System.out.println(CreateAccountLoginOperation.getCustomer().getEmail());
        } else {
            System.out.println("Not logged in");
        }
    }
}


