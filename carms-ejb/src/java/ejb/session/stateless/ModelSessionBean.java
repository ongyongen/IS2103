/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Category;
import entity.Model;
import enumeration.ModelStatus;
import exception.CategoryDoesNotExistException;
import exception.DuplicateModelException;
import exception.ModelDoesNotExistException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ongyongen
 */
@Stateless
public class ModelSessionBean implements ModelSessionBeanRemote, ModelSessionBeanLocal {

    @PersistenceContext(unitName = "carms-ejbPU")
    private EntityManager em;

    @EJB
    private CategorySessionBeanLocal categorySessionBeanLocal;

    

    public Long createNewModel(Model model, String categoryName) throws CategoryDoesNotExistException, DuplicateModelException {        
        Category category = categorySessionBeanLocal.retrieveCategoryByName(categoryName);

        String name = model.getModelName();
        Query query = em.createQuery("SELECT model FROM Model model WHERE model.modelName = :name");
        query.setParameter("name", name);
        if (query.getResultList().size() != 0) {
            throw new DuplicateModelException("There is already a model record with the same name. No new model record is created");
        } else {
            model.setCategory(category);
            category.getModels().add(model);      
            em.persist(model);
            em.flush();
            return model.getModelId();
        }
    }
   
 
    public List<Model> retrieveAllModels() {
        Query query = em.createQuery("SELECT model FROM Model model ORDER BY model.category.name ASC, model.make ASC, model.modelName ASC");
        return query.getResultList();
    }
 
    
    public Model retrieveModelByName(String modelName) throws ModelDoesNotExistException {
        Query query = em.createQuery("SELECT model FROM Model model WHERE model.modelName = :name");
        query.setParameter("name", modelName);
        if (query.getResultList().size() == 0) {
            throw new ModelDoesNotExistException("Model with the name " + modelName + " does not exist");
        }
        return (Model)query.getSingleResult();
    }
    
    public void updateModel(String oldModelName, Model newModel, String categoryName) throws CategoryDoesNotExistException, DuplicateModelException, ModelDoesNotExistException {
        Model oldModel = retrieveModelByName(oldModelName);
        Category newCat = this.categorySessionBeanLocal.retrieveCategoryByName(categoryName);
        oldModel.setModelName(newModel.getModelName());
        oldModel.setCategory(newCat);
        oldModel.setMake(newModel.getMake());
        oldModel.setStatus(newModel.getStatus());
        em.persist(oldModel);
    }
    
    public String deleteModel(String modelName) throws ModelDoesNotExistException {
        Model model = retrieveModelByName(modelName);
        if (model.getCars().isEmpty()) {
            model.getCategory().getModels().remove(model);
            em.remove(model);
            return "Model is deleted";
        } else {
            model.setStatus(ModelStatus.DISABLED);
            em.persist(model);
            return "Model is disabled";
        }
    }
}
