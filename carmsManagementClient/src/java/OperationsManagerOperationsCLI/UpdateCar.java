/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationsManagerOperationsCLI;

import ejb.session.stateless.CarSessionBeanRemote;
import entity.Car;
import enumeration.CarStatus;
import exception.CarDoesNotExistException;
import exception.DuplicateCarException;
import exception.ModelDoesNotExistException;
import exception.OutletDoesNotExistException;
import java.util.Scanner;

/**
 *
 * @author ongyongen
 */
public class UpdateCar {
    
    private final CarSessionBeanRemote carSessionBeanRemote;
    
    public UpdateCar(CarSessionBeanRemote carSessionBeanRemote) {
        this.carSessionBeanRemote = carSessionBeanRemote;
    }    
    
    public void doUpdateCar() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter the license plate number of the car to be updated:\n>");
            String oldNos = scanner.next().trim();

            Car car = this.carSessionBeanRemote.retrieveCarByLicensePlateNos(oldNos);

            String oldLicensePlateNos = "License plate nos: " + oldNos;
            String colour = "Colour: " + car.getColour();
            String location = "Location: " + car.getLocation();
            String make = "Make: " + car.getMake();
            String model = "Model: " + car.getModel().getModelName();
            String cat = "Category: " + car.getModel().getCategory().getName();
            String status = "Status: " + car.getCarStatus();
            String outlet = "Outlet: " + car.getOutlet().getOutletName();
            System.out.println(oldLicensePlateNos + "\n" 
                    + colour + "\n"
                    + location + "\n"
                    + make + "\n"
                    + model + "\n"
                    + cat + "\n"
                    + status + "\n"
                    + outlet);

            System.out.println(" ");
            System.out.print("Enter the updated car's license plate number:\n>");
            String newNos = scanner.next().trim();

            System.out.print("Enter the updated car's make:\n>");
            String newMake = scanner.next().trim();

            System.out.print("Enter the updated car's model:\n>");
            String newModel = scanner.next().trim();

            System.out.print("Enter the updated car's colour:\n>");
            String newColour = scanner.next().trim();

            System.out.print("Select the updated car's status:\n "
                    + "1: In Outlet (Not Booked)\n "
                    + "2: In rental\n "
                    + "3: Disabled\n>");
            Integer newStatus = scanner.nextInt();
            CarStatus newCarStatus = newStatus == 1 ? CarStatus.AVAILABLE :
                    newStatus == 2 ? CarStatus.IN_RENTAL_TRANSIT :
                    CarStatus.DISABLED;

            System.out.print("Enter the updated car's location:\n>");
            String newLocation = scanner.next().trim();

            System.out.print("Enter the car's new outlet:\n>");
            String newOutlet = scanner.next().trim();

            System.out.print("Is the car still available?:\n "
                    + "1: Yes\n "
                    + "2: No\n>");

            String availability = scanner.next().trim(); 
            Boolean available = availability == "1" ? true : false;

            Car newCar = new Car(newMake, available, newNos, newColour, newCarStatus, newLocation);
            this.carSessionBeanRemote.updateCar(oldNos, newCar, newModel, newOutlet);
            System.out.println("Car is updated with license number " + newNos);
            System.out.println(" ");

        } catch (OutletDoesNotExistException ex) {
           System.out.println("Provided outlet does not exist. No updates are made to the car");
           System.out.println(" ");

        } catch (ModelDoesNotExistException ex) {
            System.out.println("Provided model does not exist. Np updates are made to the car");            
            System.out.println(" ");

        } catch (DuplicateCarException ex) {
            System.out.println("Car with the same license plate number already exists");
            System.out.println(" ");

        } catch (CarDoesNotExistException ex) {
            System.out.println("Car with the license plate number provided, does not exist");
            System.out.println(" ");
        }       
    }
}
