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
public class ViewCarDetails {
    
    private final CarSessionBeanRemote carSessionBeanRemote;
    
    public ViewCarDetails(CarSessionBeanRemote carSessionBeanRemote) {
        this.carSessionBeanRemote = carSessionBeanRemote;
    }    
    
    public void doViewCarDetails() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter the car's license plate number:\n>");
            String nos = scanner.next().trim();

            Car car = this.carSessionBeanRemote.retrieveCarByLicensePlateNos(nos);
            String licensePlateNos = "License plate nos: " + nos;
            String colour = "Colour: " + car.getColour();
            String location = "Location: " + car.getLocation();
            String make = "Make: " + car.getMake();
            String model = "Model: " + car.getModel().getModelName();
            String cat = "Category: " + car.getModel().getCategory().getName();
            String status = "Status: " + car.getCarStatus();
            String outlet = "Outlet: " + car.getOutlet().getOutletName();
            System.out.println(licensePlateNos + "\n" 
                    + colour + "\n"
                    + location + "\n"
                    + make + "\n"
                    + model + "\n"
                    + cat + "\n"
                    + status + "\n" 
                    + outlet);
            System.out.println(" ");
        } catch (CarDoesNotExistException ex) {
            System.out.println("Car with the provided license plate number does not exist");
        }
    }   
}
