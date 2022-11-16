/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationsManagerOperationsCLI;

import ejb.session.stateless.ModelSessionBeanRemote;
import entity.Model;
import exception.CategoryDoesNotExistException;
import exception.DuplicateModelException;
import java.util.Scanner;

/**
 *
 * @author ongyongen
 */
public class CreateNewModel {
    
    private final ModelSessionBeanRemote modelSessionBeanRemote;
    
    public CreateNewModel(ModelSessionBeanRemote modelSessionBeanRemote) {
        this.modelSessionBeanRemote = modelSessionBeanRemote;
    }
    
    public void doCreateNewModel() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter name of the car model:\n>");
            String name = scanner.next().trim();

            System.out.print("Enter make of the car model:\n>");
            String make = scanner.next().trim();

            System.out.print("Enter category of the car model:\n>");
            String category = scanner.next().trim();

            Model model = new Model (make, name);
            this.modelSessionBeanRemote.createNewModel(model, category);
            System.out.println("Model is created with name of " + name);  
            System.out.println(" ");
        } catch (CategoryDoesNotExistException ex) {
            System.out.println("The category you provided does not exist. No new model is created");
        } catch (DuplicateModelException ex) {
            System.out.println("A model with the same name already exists. No new model is created");
        }    
    }
    
}
