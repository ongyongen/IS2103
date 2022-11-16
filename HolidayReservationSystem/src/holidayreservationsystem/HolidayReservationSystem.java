/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
package holidayreservationsystem;

import ejb.session.stateless.PartnerSessionBeanRemote;
import entity.Partner;
import exception.InvalidLoginCredentialException;

import javax.ejb.EJB;


/**
 *
 * @author jiaen
 
public class HolidayReservationSystem {

     public static void main(String[] args) throws InvalidLoginCredentialException {
        // TODO code application logic here
             
        LoginOperation login = new LoginOperation();
        while (true) {
            login.doLogin();
            if (login.isLogOutAfterLogIn() == true) {
                break;
            }
            if (login.getPartner() != null) {
                Partner partner = LoginOperation.getPartner();
               System.out.println(partner);
            } 
        }
    }
}


**/