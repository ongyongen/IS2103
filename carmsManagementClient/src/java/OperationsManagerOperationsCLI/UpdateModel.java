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
import exception.ModelDoesNotExistException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author ongyongen
 */
public class UpdateModel {
    
    private final ModelSessionBeanRemote modelSessionBeanRemote;
    
    public UpdateModel(ModelSessionBeanRemote modelSessionBeanRemote) {
        this.modelSessionBeanRemote = modelSessionBeanRemote;
    }
    
    public void doUpdateModel() {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.println("Details of all models are:");
            System.out.printf("%20s%20s%20s\n", "name", "make", "category");

            List<Model> models = this.modelSessionBeanRemote.retrieveAllModels();
            for (Model model : models) {
                System.out.printf("%20s%20s%20s\n", model.getModelName(), model.getMake(), model.getCategory().getName());
            }
            
            System.out.print("Enter the name for the original car model:\n>");
            String oldName = scanner.next().trim();

            System.out.print("Enter the name for the updated car model:\n>");
            String name = scanner.next().trim();

            System.out.print("Enter the make for the updated car model:\n>");
            String make = scanner.next().trim();

            System.out.print("Enter the category for the updated the car model:\n>");
            String categoryName = scanner.next().trim();

            Model newModel = new Model(make, name);

            this.modelSessionBeanRemote.updateModel(oldName, newModel, categoryName); 

            System.out.println("Model is updated with name of " + name);
            System.out.println(" ");

        } catch (CategoryDoesNotExistException ex) {
            System.out.println("The category you provided does not exist");

        } catch (DuplicateModelException ex) {
            System.out.println("There is a already a model with the same name as the one you provided");

        } catch (ModelDoesNotExistException ex) {
            System.out.println("Model with the name you provided does not exist");
        }
        
    }
    
}
