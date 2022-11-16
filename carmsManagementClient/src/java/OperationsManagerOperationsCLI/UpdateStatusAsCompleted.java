/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationsManagerOperationsCLI;

import ejb.session.stateless.DispatchRecordSessionBeanRemote;
import java.util.Scanner;

/**
 *
 * @author ongyongen
 */
public class UpdateStatusAsCompleted {
    
    private static DispatchRecordSessionBeanRemote dispatchRecordSessionBeanRemote;

    public UpdateStatusAsCompleted(DispatchRecordSessionBeanRemote dispatchRecordSessionBeanRemote) {
        this.dispatchRecordSessionBeanRemote = dispatchRecordSessionBeanRemote;
    }
    
    public void doUpdateStatusAsCompleted() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter the dispatch record ID:\n>");
            String idStr = scanner.next().trim(); 
            Long id = Long.valueOf(idStr);                    
            String res = this.dispatchRecordSessionBeanRemote.updateDispatchRecordAsCompleted(id);
            System.out.println(res);
            
        } catch (Exception ex) {
            System.out.println("Error");
        }

    }
    
}
