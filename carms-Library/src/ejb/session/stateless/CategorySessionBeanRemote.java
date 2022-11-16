/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Category;
import exception.CategoryDoesNotExistException;
import exception.DuplicateCategoryException;
import javax.ejb.Remote;

/**
 *
 * @author ongyongen
 */
@Remote
public interface CategorySessionBeanRemote {
    
   public Long createNewCategory(Category category) throws DuplicateCategoryException;
    
    public Category retrieveCategoryByName(String name)  throws CategoryDoesNotExistException;
}
