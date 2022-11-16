/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CarReservation;
import entity.Customer;
import exception.InvalidLoginCredentialException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author ongyongen
 */
@Remote
public interface CustomerSessionBeanRemote {
    public Long createNewCustomer(Customer customer);
   
    public List<Customer> retrieveAllCustomers();
    
    public Customer retrieveCustomerByEmailAndPassword(String email, String password) throws InvalidLoginCredentialException;

    public List<CarReservation> retrieveAllReservationsByCustomerId(Long id);

}
