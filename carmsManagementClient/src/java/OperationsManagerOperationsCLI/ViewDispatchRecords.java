/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationsManagerOperationsCLI;

import ejb.session.stateless.CarSessionBeanRemote;
import ejb.session.stateless.DispatchRecordSessionBeanRemote;
import entity.Car;
import entity.DispatchRecord;
import exception.CarDoesNotExistException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ongyongen
 */
public class ViewDispatchRecords {
    private static DispatchRecordSessionBeanRemote dispatchRecordSessionBeanRemote;

    public ViewDispatchRecords(DispatchRecordSessionBeanRemote dispatchRecordSessionBeanRemote) {
        this.dispatchRecordSessionBeanRemote = dispatchRecordSessionBeanRemote;
    }
    
    public void doViewDispatchRecords() {
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.print("Enter the outlet:\n>");
            String outletName = scanner.next().trim(); 

            System.out.print("Enter the date:\n>");
            String dateStr = scanner.next().trim(); 

            SimpleDateFormat formatterDateTime = new SimpleDateFormat("dd-MM-yy");
            Date currentDate = formatterDateTime.parse(dateStr);

            System.out.println("Details of dispatch record for the outlet and day:");
            System.out.printf("%20s%40s%40s\n", "id", "date & time of dispatch", "car license nos");

            List<DispatchRecord> records = this.dispatchRecordSessionBeanRemote.retrieveAllDispatchRecordsForCurrentDayAndOutlet(currentDate, outletName);
            for (DispatchRecord r : records) {
                System.out.printf("%20s%40s%40s\n", 
                        r.getDispatchRecordId(),
                        r.getDateOfDispatch(),
                        r.getLicenseNosCarToPickUp());
            }   
        } catch (ParseException ex) {
            Logger.getLogger(ViewDispatchRecords.class.getName()).log(Level.SEVERE, null, ex);
        }
                    

    }
    
}


