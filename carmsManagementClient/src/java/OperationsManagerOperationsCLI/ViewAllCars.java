/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationsManagerOperationsCLI;

import ejb.session.stateless.CarSessionBeanRemote;
import entity.Car;
import java.util.List;

/**
 *
 * @author ongyongen
 */
public class ViewAllCars {
    
    private final CarSessionBeanRemote carSessionBeanRemote;
    
    public ViewAllCars(CarSessionBeanRemote carSessionBeanRemote) {
        this.carSessionBeanRemote = carSessionBeanRemote;
    }
    
    public void doViewAllCars() {
        System.out.println("Details of all models are:");
        System.out.printf("%25s%25s%25s%25s\n", "category", "make", "model", "license plate number");

        List<Car> cars = this.carSessionBeanRemote.retrieveAllCars();
        for (Car car : cars) {
            System.out.printf("%25s%25s%25s%25s\n", 
                    car.getModel().getCategory().getName(),
                    car.getMake(), 
                    car.getModel().getModelName(),
                    car.getLicensePlateNos());
        }

        System.out.println(" ");
    }
    
}
