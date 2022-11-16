/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Partner;
import exception.DuplicationPartnerException;
import exception.InvalidLoginCredentialException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author ongyongen
 */
@Remote
public interface PartnerSessionBeanRemote {
   
    public Long createNewPartner(Partner partner)throws DuplicationPartnerException;;
    
    public List<Partner> retrieveAllPartners();
    
    //public Partner retrievePartnerByEmailAndPassword(String email, String password);
    public Partner retrievePartnerByEmailAndPassword(String email, String password) throws InvalidLoginCredentialException;
    
}
