/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationsManagerOperationsCLI;

import ejb.session.stateless.DispatchRecordSessionBeanRemote;
import exception.EmployeeAlreadyAssignedToDispatchRecordException;
import exception.EmployeeDoesNotExistException;
import java.text.ParseException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ongyongen
 */
public class AssignTransitDriver {
    
    private static DispatchRecordSessionBeanRemote dispatchRecordSessionBeanRemote;

    public AssignTransitDriver(DispatchRecordSessionBeanRemote dispatchRecordSessionBeanRemote) {
        this.dispatchRecordSessionBeanRemote = dispatchRecordSessionBeanRemote;
    }
    
    public void doAssignTransitDriver() {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the username of the employee:\n>");
            String name = scanner.next().trim();
            
            System.out.print("Enter the dispatch record ID:\n>");
            String idStr = scanner.next().trim(); 
            Long id = Long.valueOf(idStr);
            String res = this.dispatchRecordSessionBeanRemote.AssignDriverToRecord(name, id);
            System.out.println(res); 
        } catch (EmployeeAlreadyAssignedToDispatchRecordException ex) {
            System.out.println("Employee is already assigned to a dispatch record");
        } catch (EmployeeDoesNotExistException ex) {
            System.out.println("Employee does not exist");

        }
     
                   
    }
 
}
