/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Car;
import entity.Category;
import entity.Model;
import enumeration.CarStatus;
import exception.ModelDoesNotExistException;
import exception.OutletDoesNotExistException;
import java.util.List;
import javax.ejb.Remote;
import javax.persistence.Query;
import exception.CarDoesNotExistException;
import exception.DuplicateCarException;

/**
 *
 * @author ongyongen
 */
@Remote
public interface CarSessionBeanRemote {
    public Long createNewCar(Car car, String modelName, String outletName) throws OutletDoesNotExistException, ModelDoesNotExistException, DuplicateCarException;
    
    public List<Car> retrieveAllCars();
    
    public Car retrieveCarByLicensePlateNos(String licensePlateNos) throws CarDoesNotExistException;
    
    public String viewDetailsOfACar(String licensePlateNos) throws CarDoesNotExistException;
    
    public void updateCar(String oldLicensePlateNos, Car newCar, String newModelName, String outletName) 
            throws OutletDoesNotExistException, ModelDoesNotExistException, CarDoesNotExistException, DuplicateCarException;
    
    public String deleteCar(String licensePlateNos) throws CarDoesNotExistException;

    public List<Car> retrieveCarsByCategory(String catName);
    
    public List<Car> retrieveCarsByCategoryAndModel(String catName, String modelName);

    public List<Car> retrieveCarsByOutlet(String outletName);
}
