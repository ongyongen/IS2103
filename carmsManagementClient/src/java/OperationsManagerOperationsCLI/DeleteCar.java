/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationsManagerOperationsCLI;

import ejb.session.stateless.CarSessionBeanRemote;
import entity.Car;
import exception.CarDoesNotExistException;
import java.util.Scanner;

/**
 *
 * @author ongyongen
 */
public class DeleteCar {
    
    private final CarSessionBeanRemote carSessionBeanRemote;
    
    public DeleteCar(CarSessionBeanRemote carSessionBeanRemote) {
        this.carSessionBeanRemote = carSessionBeanRemote;
    }    
    
    public void doDelete() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter the license plate number of the car to be deleted:\n>");
            String oldNos = scanner.next().trim(); 

            Car car = this.carSessionBeanRemote.retrieveCarByLicensePlateNos(oldNos);

            System.out.print("Confirm deletion of this car record?\n>");
            String oldLicensePlateNos = "License plate nos: " + car.getLicensePlateNos();
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

            System.out.print("Re-enter the license plate number of the car to be confirm deletion:\n>");
            String oldNosConfirm = scanner.next().trim(); 
            String deletedMsg = this.carSessionBeanRemote.deleteCar(oldNosConfirm);

            System.out.println(deletedMsg);
            System.out.println(" ");

            } catch (CarDoesNotExistException ex) {
                System.out.println("Car with that license plate number provided does not exist");
                System.out.println(" ");
            }
    }
}
    

   
