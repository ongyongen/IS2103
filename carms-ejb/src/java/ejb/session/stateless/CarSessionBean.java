/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Car;
import entity.Category;
import entity.Model;
import entity.Outlet;
import enumeration.CarStatus;
import exception.CarDoesNotExistException;
import exception.DuplicateCarException;
import exception.ModelDoesNotExistException;
import exception.OutletDoesNotExistException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ongyongen
 */
@Stateless
public class CarSessionBean implements CarSessionBeanRemote, CarSessionBeanLocal {

    @EJB
    private OutletSessionBeanLocal outletSessionBeanLocal;

    @PersistenceContext(unitName = "carms-ejbPU")
    private EntityManager em;
    
    @EJB
    private ModelSessionBeanLocal modelSessionBeanLocal;
    
  
    public Long createNewCar(Car car, String modelName, String outletName) throws 
        ModelDoesNotExistException, OutletDoesNotExistException, DuplicateCarException {
   
        Model model = modelSessionBeanLocal.retrieveModelByName(modelName);
        Outlet outlet = outletSessionBeanLocal.retrieveOutletByName(outletName);

        Query query = em.createQuery("SELECT c FROM Car c WHERE c.licensePlateNos = :nos");
        query.setParameter("nos", car.getLicensePlateNos());
        if (query.getResultList().size() != 0) {
            throw new DuplicateCarException("Car with the same license plate nos already exists");
        } else {
            car.setOutlet(outlet);
            car.setModel(model);
            model.getCars().add(car);
            em.persist(car);
            em.flush();
            return car.getCarId();
        }
    }

    public List<Car> retrieveAllCars() {
        Query query = em.createQuery("SELECT car FROM Car car ORDER BY car.model.category ASC, "
                + "car.make ASC, car.model ASC, car.licensePlateNos ASC");
        return query.getResultList();
    }
    
    public List<Car> retrieveCarsByCategory(String catName) {
        Query query = em.createQuery("SELECT car FROM Car car WHERE car.model.category.name = :catName");
        query.setParameter("catName", catName);
        return query.getResultList();
    }
    
    public List<Car> retrieveCarsByCategoryAndModel(String catName, String modelName) {
        Query query = em.createQuery("SELECT car FROM Car car "
                + "WHERE car.model.category.name = :catName "
                + "AND car.model.modelName = :modelName");

        query.setParameter("catName", catName);
        query.setParameter("modelName", modelName);
       
        return query.getResultList();
    }
    
    public List<Car> retrieveCarsByOutlet(String outletName) {
        Query query = em.createQuery("SELECT car FROM Car car "
                + "WHERE car.outlet.outletName = :outletName ");

        query.setParameter("outletName", outletName);
       
        return query.getResultList();
    }
    
    public Car retrieveCarByLicensePlateNos(String licensePlateNos) throws CarDoesNotExistException {
        Query query = em.createQuery("SELECT car FROM Car car WHERE car.licensePlateNos = :nos");
        query.setParameter("nos", licensePlateNos);
        if (query.getResultList().size() == 0) {
            throw new CarDoesNotExistException("Car does not exist");
        }
        return (Car)query.getSingleResult();               
    }
    
    public List<Car> retrieveAllCarsThatCanBeAllocatedForADay() {
        CarStatus notBookedStatus = CarStatus.AVAILABLE;
        Query query = em.createQuery("SELECT car FROM Car car WHERE car.carStatus = :notBookedStatus");
        query.setParameter("notBookedStatus", notBookedStatus);
        
        return query.getResultList();
        
    }
    

       
    public String viewDetailsOfACar(String licensePlateNos) throws CarDoesNotExistException {
        Car car = retrieveCarByLicensePlateNos(licensePlateNos);
        String nos = car.getLicensePlateNos();
        String colour = car.getLicensePlateNos();
        String location = car.getLocation();
        String make = car.getMake();
        String model = car.getModel().getModelName();
        String category = car.getModel().getCategory().getName();
        String status = car.getCarStatus().toString();
        String available = car.isAvailable() == true ? "available" : "not available";
        return nos + "\n" + colour + "\n" + location + "\n" + make + "\n" + 
                model + "\n" + category + "\n" + status + "\n" + available;
    }
    
    public void updateCar(String oldLicensePlateNos, Car newCar, String newModelName, String newOutletName) throws 
            ModelDoesNotExistException, OutletDoesNotExistException, CarDoesNotExistException, DuplicateCarException {
        
        Model model = modelSessionBeanLocal.retrieveModelByName(newModelName);
        Outlet outlet = outletSessionBeanLocal.retrieveOutletByName(newOutletName);
        
        Query query = em.createQuery("SELECT c FROM Car c WHERE c.licensePlateNos = :nos");
        query.setParameter("nos", newCar.getLicensePlateNos());
        if (query.getResultList().size() != 0) {
            throw new DuplicateCarException("Car with the same license plate number already exists");
        } else {
            Car car = retrieveCarByLicensePlateNos(oldLicensePlateNos);
            car.setAvailable(newCar.isAvailable());
            car.setColour(newCar.getLicensePlateNos());
            car.setLicensePlateNos(newCar.getLicensePlateNos());
            car.setLocation(newCar.getLocation());
            car.setMake(newCar.getMake());
            car.setModel(model);
            car.setCarStatus(newCar.getCarStatus());
            car.setOutlet(outlet);          
        }
    }
    
    public void updateCarOutlet(String oldLicensePlateNos, String newOutletName) throws OutletDoesNotExistException, CarDoesNotExistException {
        Outlet outlet = outletSessionBeanLocal.retrieveOutletByName(newOutletName);
        Car car = retrieveCarByLicensePlateNos(oldLicensePlateNos);
        car.setOutlet(outlet);
    }
    
    public String deleteCar(String licensePlateNos) throws CarDoesNotExistException {
        Car car = retrieveCarByLicensePlateNos(licensePlateNos);
        if (car.getCarStatus() == CarStatus.AVAILABLE) {
            car.getModel().getCars().remove(car);
            em.remove(car);
            return "Car with license plate number " + licensePlateNos + " is deleted";
        } else {
            car.setCarStatus(CarStatus.DISABLED);
            return "Car with license plate number " + licensePlateNos + " is disabled";
        }
    }

    
}
