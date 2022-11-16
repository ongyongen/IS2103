/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationsManagerOperationsCLI;

import ejb.session.stateless.ModelSessionBeanRemote;
import entity.Model;
import exception.ModelDoesNotExistException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author ongyongen
 */
public class DeleteModel {
    private final ModelSessionBeanRemote modelSessionBeanRemote;
    
    public DeleteModel(ModelSessionBeanRemote modelSessionBeanRemote) {
        this.modelSessionBeanRemote = modelSessionBeanRemote;
    }
    
    public void doDeleteModel() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Details of all models are:");
            System.out.printf("%20s%20s%20s\n", "name", "make", "category");

            List<Model> models = this.modelSessionBeanRemote.retrieveAllModels();
            for (Model model : models) {
                System.out.printf("%20s%20s%20s\n", model.getModelName(), model.getMake(), model.getCategory().getName());
            }

            System.out.print("Enter the name for the car model to be deleted:\n>");
            String name = scanner.next().trim();
            String res = this.modelSessionBeanRemote.deleteModel(name);
            System.out.println(res);
            System.out.println(" ");

        } catch (ModelDoesNotExistException ex) {
            System.out.println("Model does not exist");
            System.out.println(" ");
        }
    }
    
}
