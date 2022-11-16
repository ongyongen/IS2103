/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CarReservation;
import entity.Customer;
import entity.Model;
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
public class CustomerSessionBean implements CustomerSessionBeanRemote, CustomerSessionBeanLocal {

    @PersistenceContext(unitName = "carms-ejbPU")
    private EntityManager em;

    public Long createNewCustomer(Customer customer) {
        em.persist(customer);
        em.flush();
        return customer.getCustomerId();
    }
    
    
    public List<Customer> retrieveAllCustomers() {
        Query query = em.createQuery("SELECT customer FROM Customer customer");
        return query.getResultList();
    }
    
    public Customer retrieveCustomerByEmail(String email)  {
        Query query = em.createQuery("SELECT customer FROM Customer customer "
                + "WHERE customer.email = :email");
        query.setParameter("email", email);
        return (Customer)query.getSingleResult();
    }
    
    public Customer retrieveCustomerByEmailAndPassword(String email, String password) throws InvalidLoginCredentialException {
        Query query = em.createQuery("SELECT customer FROM Customer customer "
                + "WHERE customer.email = :email AND customer.password = :password");
        query.setParameter("email", email);
        query.setParameter("password", password);
        if (query.getResultList().size() != 0) {
            return (Customer)query.getSingleResult();
        } else {
            throw new InvalidLoginCredentialException("Login credentials are incorrect. Please try again.");
        }
    }
    
    public List<CarReservation> retrieveAllReservationsByCustomerId(Long id) {
        Customer cust = em.find(Customer.class, id);
        return cust.getCarReservations();
    }
    
}
