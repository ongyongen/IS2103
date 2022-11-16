/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Car;
import entity.Category;
import entity.Model;
import exception.CategoryDoesNotExistException;
import exception.DuplicateCategoryException;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ongyongen
 */
@Stateless
public class CategorySessionBean implements CategorySessionBeanRemote, CategorySessionBeanLocal {

    @PersistenceContext(unitName = "carms-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewCategory(Category category) throws DuplicateCategoryException {
        String name = category.getName();
        Query query = em.createQuery("SELECT cat FROM Category cat WHERE cat.name = :name");
        query.setParameter("name", name);
        if (query.getResultList().size() != 0) {
            throw new DuplicateCategoryException("Category with the name " + name + " already exists. No new category is created");
        } else {
            em.persist(category);
            em.flush();
            return category.getCategoryId();  
        }
    }
    
    public List<Category> retrieveAllCategories() {
        Query query = em.createQuery("SELECT cat FROM Category cat");
        return query.getResultList();
    }
    

    public Category retrieveCategoryByName(String name) throws CategoryDoesNotExistException {
        Query query = em.createQuery("SELECT cat FROM Category cat WHERE cat.name = :name");
        query.setParameter("name", name);
        if (query.getResultList().size() != 0) {
            return (Category)query.getSingleResult();
        } else {
            throw new CategoryDoesNotExistException("There is no category with the name " + name);
        }
    }
   
   
}
