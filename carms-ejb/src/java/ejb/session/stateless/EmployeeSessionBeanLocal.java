/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Employee;
import exception.DuplicateUserException;
import exception.EmployeeDoesNotExistException;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author ongyongen
 */
@Local
public interface EmployeeSessionBeanLocal {
    public Long createNewEmployee(Employee employee) throws DuplicateUserException;
    
    public List<Employee> retrieveAllEmployees();
    public Employee retrieveEmployeeByUsername(String username) throws EmployeeDoesNotExistException;
}
