/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.DispatchRecord;
import entity.Employee;
import exception.DuplicateUserException;
import exception.EmployeeDoesNotExistException;
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
public class EmployeeSessionBean implements EmployeeSessionBeanRemote, EmployeeSessionBeanLocal {

    @PersistenceContext(unitName = "carms-ejbPU")
    private EntityManager em;

    @Override
    public Long createNewEmployee(Employee employee) throws DuplicateUserException {
        String username = employee.getUsername();
        Query query = em.createQuery("SELECT employee FROM Employee employee WHERE employee.username = :username");
        query.setParameter("username", username);
        if (query.getResultList().size() != 0) {
            throw new DuplicateUserException("Account with username " + username + " already exists. Please choose a new name");
        } else {
            em.persist(employee);
            em.flush();
            return employee.getEmployeeId();           
        }
    }
    
    public List<Employee> retrieveAllEmployees() {
        Query query = em.createQuery("SELECT employee FROM Employee employee");
        return query.getResultList();
    }
    

    public Employee retrieveEmployeeByUsernameAndPassword(String username, String password) throws InvalidLoginCredentialException {
        Query query = em.createQuery("SELECT employee FROM Employee employee WHERE employee.username = :username AND employee.password = :password");
        query.setParameter("username", username);
        query.setParameter("password", password);
        if (query.getResultList().size() == 0) {
            throw new InvalidLoginCredentialException("Login credentials are incorrect");
        } else {
            return (Employee)query.getSingleResult(); 
        }
    }
    
    public Employee retrieveEmployeeByUsername(String username) throws EmployeeDoesNotExistException {
        Query query = em.createQuery("SELECT emp FROM Employee emp WHERE emp.username = :username");
        query.setParameter("username", username);
        if (query.getSingleResult().equals(null)) {
            throw new EmployeeDoesNotExistException("employee with this username does not exist");
        } else {
            return (Employee)query.getSingleResult();
        }
    }

}
