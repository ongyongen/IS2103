/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package holidayreservationsystem;

//import ejb.session.stateless.PartnerSessionBeanRemote;
import ejb.session.stateless.PartnerSessionBeanRemote;
import entity.Partner;
//import entity.Partner;
import exception.InvalidLoginCredentialException;
import exception.InvalidMenuOptionException;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.ejb.EJB;
/**

public class LoginOperation {
    
    private static Partner partner;
    
    private boolean logOutAfterLogIn;
    private Long currentPartnerId = new Long(0);
        
    public LoginOperation() {
        
    }

   
    public boolean isLogOutAfterLogIn() {
        return logOutAfterLogIn;
    }

    public void setLogOutAfterLogIn(boolean logOutAfterLogIn) {
        this.logOutAfterLogIn = logOutAfterLogIn;
    }
    
    public void doLogin() throws InvalidLoginCredentialException{
       Integer response = 0;
       Scanner scanner = new Scanner(System.in);
       String email = "";
       String password = "";
        while (true) {
            System.out.println("Welcome to the Holiday Reservation System!");
            System.out.println("1: Log in");
            System.out.println("2: Exit");
            response = 0;
     
                while( response < 1 || response >2){
                     System.out.print(">");
                     response = scanner.nextInt();
                if (response == 1) {
                    System.out.print("Enter your email:\n>");
                    email = scanner.next().trim();
                  
                    System.out.print("Enter your password:\n>");
                    password = scanner.next().trim();
                   
                    try {
                        if(email.length() > 0 && password.length() > 0){
                            Partner partners = retrievePartnerByEmailAndPassword(email, password);
                        this.setPartner(partners);
                        
                        System.out.print(partners);
                        System.out.println("Credentials are correct. You are logged in.");
                        break;
                        }
                    } 
                    catch (InvalidLoginCredentialException ex) {
                        System.out.println("Login credentials are wrong. Please try again.");
                    } 

                }

                else if (response == 2) {
                    this.setLogOutAfterLogIn(true);
                    System.out.println("You have exited the system");
                    break;
                }
                else{
                System.out.println("Menu option is invalid. Please select a valid option");
                }
                }
                 if(response == 2)
            {
                break;
            }
        }
    }

    
    public static Partner getPartner() {
        return partner;
    }

    public static void setPartner(Partner partner) {
        LoginOperation.partner = partner;
    }
    
    /**
     private static Partner retrievePartnerByEmailAndPassword(java.lang.String arg0, java.lang.String arg1) throws InvalidLoginCredentialException {
        PartnerWebService_Service service = new PartnerWebService_Service();
        PartnerWebService port = service.getPartnerWebServicePort();
        return port.retrievePartnerByEmailAndPassword(arg0, arg1);
    }

     
}

**/