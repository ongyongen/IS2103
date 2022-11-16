package carmsclient;

import entity.Employee;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ongyongen
 */
public class LogoutOperation {
    
    private static Employee employee;

    public void doLogout(Employee employee) {
        
        Integer response = 0;
        Scanner scanner = new Scanner(System.in);
        while (response < 4) {
            System.out.print("");
            System.out.println("1. Task 1");
            System.out.println("2. Task 2");
            System.out.println("3. Task 3");
            System.out.println("4. Log out");
            response = scanner.nextInt();
            if (response == 4) {
                System.out.println("Employee is logged out");
                break;
            }
        } 
    }
}
