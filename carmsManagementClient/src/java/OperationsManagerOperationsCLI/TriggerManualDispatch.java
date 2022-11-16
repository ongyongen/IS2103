/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationsManagerOperationsCLI;

import ejb.session.stateless.EJBTimerSessionBeanRemote;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

/**
 *
 * @author ongyongen
 */
public class TriggerManualDispatch {
    private static EJBTimerSessionBeanRemote eJBTimerSessionBeanRemote;

    public TriggerManualDispatch(EJBTimerSessionBeanRemote eJBTimerSessionBeanRemote) {
        this.eJBTimerSessionBeanRemote = eJBTimerSessionBeanRemote;
    }
    
    public void doTriggerManualDispatch() throws ParseException {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the date to trigger the manual dispatch:\n>");
        String dateStr = scanner.next().trim();
        
        System.out.print("Enter the time to trigger the manual dispatch:\n>");
        String timeStr = scanner.next().trim();

        SimpleDateFormat formatterDateTime = new SimpleDateFormat("dd-MM-yy HH:mm");
        Date dateTimedispatch = formatterDateTime.parse(dateStr + " " + timeStr);
        this.eJBTimerSessionBeanRemote.allocateCarsToReservations(dateTimedispatch);
        
        System.out.println("Car reservations are allocated");
    }
    
}
