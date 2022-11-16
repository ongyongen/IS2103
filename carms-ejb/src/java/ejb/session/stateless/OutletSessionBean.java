/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Model;
import entity.Outlet;
import exception.DuplicateOutletException;
import exception.OutletDoesNotExistException;
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
public class OutletSessionBean implements OutletSessionBeanRemote, OutletSessionBeanLocal {

    @PersistenceContext(unitName = "carms-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewOutlet(Outlet outlet) throws DuplicateOutletException {
        String name = outlet.getOutletName();
        Query query = em.createQuery("SELECT outlet FROM Outlet outlet WHERE outlet.outletName = :name");
        query.setParameter("name", name);
        if (query.getResultList().size() != 0) {
            throw new DuplicateOutletException("There is already an outlet with the same name. No new outlet is created");
        } else {
            em.persist(outlet);
            em.flush();
            return outlet.getOutletId();         
        }
    }

    public List<Outlet> retrieveAllOutlets() {
        Query query = em.createQuery("SELECT outlet FROM Outlet outlet");
        return query.getResultList();
    }
    
    public Outlet retrieveOutletByName(String name) throws OutletDoesNotExistException {
        Query query = em.createQuery("SELECT outlet FROM Outlet outlet WHERE outlet.outletName = :name");
        query.setParameter("name", name);
        if (query.getResultList().size() == 0) {
            throw new OutletDoesNotExistException("There is no outlet with the name of : " + name);            
        } else {
           return (Outlet)query.getSingleResult(); 
        }        
    }
    
    
    
}
