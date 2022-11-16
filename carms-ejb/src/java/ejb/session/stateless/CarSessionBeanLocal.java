/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Car;
import exception.ModelDoesNotExistException;
import exception.OutletDoesNotExistException;
import exception.CarDoesNotExistException;
import exception.DuplicateCarException;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.Query;

/**
 *
 * @author ongyongen
 */
@Local
public interface CarSessionBeanLocal {
    public Long createNewCar(Car car, String modelName, String outletName) throws OutletDoesNotExistException, ModelDoesNotExistException, DuplicateCarException;

    public Car retrieveCarByLicensePlateNos(String licensePlateNos) throws CarDoesNotExistException;
    
    public List<Car> retrieveAllCars();
    public List<Car> retrieveCarsByCategory(String catName);
    
    public List<Car> retrieveCarsByCategoryAndModel(String catName, String modelName);
    public List<Car> retrieveAllCarsThatCanBeAllocatedForADay();
    
}
