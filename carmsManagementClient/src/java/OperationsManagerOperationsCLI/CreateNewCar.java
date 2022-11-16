/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationsManagerOperationsCLI;

import ejb.session.stateless.CarSessionBeanRemote;
import entity.Car;
import enumeration.CarStatus;
import exception.DuplicateCarException;
import exception.ModelDoesNotExistException;
import exception.OutletDoesNotExistException;
import java.util.Scanner;

/**
 *
 * @author ongyongen
 */
public class CreateNewCar {
    
    private final CarSessionBeanRemote carSessionBeanRemote;
    
    public CreateNewCar(CarSessionBeanRemote carSessionBeanRemote) {
        this.carSessionBeanRemote = carSessionBeanRemote;
    }
    
    public void doCreateNewCar() {
        Scanner scanner = new Scanner(System.in);
        
        try {

            System.out.print("Enter the car's license plate number:\n>");
            String licenseNos = scanner.next().trim(); 

            System.out.print("Enter the car's make:\n>");
            String make = scanner.next().trim(); 

            System.out.print("Enter the car's model:\n>");
            String model = scanner.next().trim(); 

            System.out.print("Enter the car's colour:\n>");
            String colour = scanner.next().trim(); 

            String status = "None";
            while (true) {
                System.out.print("Select the car's status:\n "
                  + "1: Available\n "
                  + "2: In rental\n "
                  + "3: Disabled\n>");
                status = scanner.next().trim();
                if (!status.equals("1") & !status.equals("2") & !status.equals("3")) {
                    System.out.println("Select only the provided options (1, 2 or 3)");
                    System.out.println(" ");
                } else {
                    break;
                }                       
            }

            CarStatus carStatus = "1".equals(status) ? CarStatus.AVAILABLE :
                               "2".equals(status) ? CarStatus.IN_RENTAL_TRANSIT :
                               CarStatus.DISABLED;

            System.out.print("Enter the car's location:\n>");
            String location = scanner.next().trim(); 

            System.out.print("Enter the car's outlet:\n>");
            String outlet = scanner.next().trim(); 

            Car car = new Car(make, true, licenseNos, colour, carStatus, location);
            this.carSessionBeanRemote.createNewCar(car, model, outlet);
            System.out.println("Car is created with license number " + licenseNos);
            System.out.println(" ");                        

        } catch (OutletDoesNotExistException ex) {
            System.out.println("Provided outlet does not exist. No new car is created");
        } catch (ModelDoesNotExistException ex) {
            System.out.println("Provided model does not exist. No new car is created");
        } catch (DuplicateCarException ex) {
            System.out.println("There is already a record of a car with the same license plate number. No new car is created");
        }
    }
    
}
