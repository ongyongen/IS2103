/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Outlet;
import entity.Partner;
import exception.DuplicationPartnerException;
import exception.InvalidLoginCredentialException;
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
public class PartnerSessionBean implements PartnerSessionBeanRemote, PartnerSessionBeanLocal {

    @PersistenceContext(unitName = "carms-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewPartner(Partner partner) throws DuplicationPartnerException {
         String email = partner.getEmail();
        Query query = em.createQuery("SELECT partner FROM Partner partner WHERE partner.email = :email");
        query.setParameter("email", email);
        if (query.getResultList().size() != 0) {
            throw new DuplicationPartnerException("Account with email " + email + " already exists. Please choose a new email");
        } else {
            em.persist(partner);
            em.flush();
            return partner.getPartnerId();           
        }
    }
    
    public List<Partner> retrieveAllPartners() {
        Query query = em.createQuery("SELECT partner FROM Partner partner");
        return query.getResultList();
    }

    @Override
    public Partner retrievePartnerByEmailAndPassword(String email, String password) throws InvalidLoginCredentialException {
        System.out.println("checking parametre" + email + "    " + password);
        Query query = em.createQuery("SELECT partner FROM Partner partner WHERE partner.email = :email AND partner.password = :password");
        System.out.println("checking query   : " + query);
        query.setParameter("email", email);
        query.setParameter("password", password);
        if (query.getResultList().size() == 0) {
            throw new InvalidLoginCredentialException("Login credentials are incorrect");
        } else {
            return (Partner)query.getSingleResult(); 
        }
    }
    
}
