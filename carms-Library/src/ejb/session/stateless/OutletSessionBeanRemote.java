/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Outlet;
import exception.DuplicateOutletException;
import exception.OutletDoesNotExistException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author ongyongen
 */
@Remote
public interface OutletSessionBeanRemote {
        
    public Long createNewOutlet(Outlet outlet) throws DuplicateOutletException;
  
    public List<Outlet> retrieveAllOutlets();
    
    public Outlet retrieveOutletByName(String name) throws OutletDoesNotExistException;
    
}
