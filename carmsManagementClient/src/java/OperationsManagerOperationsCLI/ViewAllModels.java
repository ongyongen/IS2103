/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package OperationsManagerOperationsCLI;

import ejb.session.stateless.ModelSessionBeanRemote;
import entity.Model;
import java.util.List;

/**
 *
 * @author ongyongen
 */
public class ViewAllModels {
    
    private final ModelSessionBeanRemote modelSessionBeanRemote;
    
    public ViewAllModels(ModelSessionBeanRemote modelSessionBeanRemote) {
        this.modelSessionBeanRemote = modelSessionBeanRemote;
    }
    
    public void doViewAllModels() {
        System.out.println("Details of all models are:");
        System.out.printf("%20s%20s%20s\n", "name", "make", "category");

        List<Model> models = this.modelSessionBeanRemote.retrieveAllModels();
        for (Model model : models) {
            System.out.printf("%20s%20s%20s\n", model.getModelName(), model.getMake(), model.getCategory().getName());
        }

        System.out.println(" ");
    }
    
}
