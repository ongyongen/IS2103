/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Employee;
import exception.DuplicateUserException;
import exception.EmployeeDoesNotExistException;
import exception.InvalidLoginCredentialException;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author ongyongen
 */
@Remote
public interface EmployeeSessionBeanRemote {
        
    public Long createNewEmployee(Employee employee) throws DuplicateUserException;
    
    public List<Employee> retrieveAllEmployees();
    
    public Employee retrieveEmployeeByUsernameAndPassword(String username, String password) throws InvalidLoginCredentialException;    

    public Employee retrieveEmployeeByUsername(String username) throws EmployeeDoesNotExistException;

}
