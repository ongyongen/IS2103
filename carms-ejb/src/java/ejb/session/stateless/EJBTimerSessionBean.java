/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import com.sun.xml.wss.util.DateUtils;
import entity.Car;
import entity.CarReservation;
import entity.Category;
import entity.DispatchRecord;
import entity.Model;
import entity.Outlet;
import enumeration.CarStatus;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author ongyongen
 */
@Stateless
public class EJBTimerSessionBean implements EJBTimerSessionBeanRemote, EJBTimerSessionBeanLocal {

    @EJB
    private DispatchRecordSessionBeanLocal dispatchRecordSessionBeanLocal;

    @EJB
    private CarSessionBeanLocal carSessionBeanLocal;

    @EJB
    private CarReservationSessionBeanLocal carReservationSessionBeanLocal;

    private static final long HOUR = 3600*1000;
    
    public EJBTimerSessionBean() {
        
    }
    
    public void allocateCarsToReservations(Date currentDate) throws ParseException {
        List<CarReservation> reservations = this.carReservationSessionBeanLocal.retrieveReservationsByDate(currentDate);
        List<Car> carsToAllocate = this.carSessionBeanLocal.retrieveAllCars(); //.retrieveAllCarsThatCanBeAllocatedForADay();
        List<CarReservation> carReservationsNotAllocated = new ArrayList<CarReservation>();
        for (CarReservation r : reservations) {
            Category catWanted = r.getCarCategory();
            Outlet pickupOutletWanted = r.getPickUpOutlet();
            boolean carIsAllocatedToReservation = false;
            for (Car car : carsToAllocate) {
                Model carModel = car.getModel();
                Category carCat = car.getModel().getCategory();
                Outlet carOutlet = car.getOutlet();
                if (r.getModel() == null) {
                    if (carCat.equals(catWanted) && 
                        carOutlet.equals(pickupOutletWanted) &&
                        car.getCarStatus().equals(CarStatus.AVAILABLE)) { 
                        r.setCar(car);
                        r.setModel(car.getModel());
                        car.setCarReservation(r);
                        car.setCarStatus(CarStatus.JUST_ALLOCATED_TO_RESERVATION);
                        carIsAllocatedToReservation = true;
                        break;
                    } 
                } 
                
                if (r.getModel() != null) {
                    Model modelWanted = r.getModel();
                      if (carCat.equals(catWanted) && 
                        carModel.equals(modelWanted) &&
                        carOutlet.equals(pickupOutletWanted) &&
                        car.getCarStatus().equals(CarStatus.AVAILABLE)) {
                        r.setCar(car);
                        r.setModel(car.getModel());
                        car.setCarReservation(r);
                        car.setCarStatus(CarStatus.JUST_ALLOCATED_TO_RESERVATION);
                        carIsAllocatedToReservation = true;
                        break;
                    }          
                    
                }
               
            }
            
            if (carIsAllocatedToReservation == false) {
                carReservationsNotAllocated.add(r);
            }
            
        }
        
        for (CarReservation r : carReservationsNotAllocated) {
            Category cat = r.getCarCategory();
            if (r.getModel() == null) {
                List<CarReservation> reservationsByCat = carReservationSessionBeanLocal.retrieveCarReservationsByCategory(cat.getName());
                boolean assignCarFromAnotherReservation = false;
                for (CarReservation rByCat : reservationsByCat) {
                    boolean canUseOtherCar = carReservationSessionBeanLocal.canReserveCarDroppedOffAtAnotherOutlet(rByCat, r.getPickupDateTime());
                    if (canUseOtherCar == true) {
                        Car car = rByCat.getCar();
                        r.setCar(car);
                        r.setModel(car.getModel());

                        car.setCarReservation(r); // to delete?
                        car.setCarStatus(CarStatus.JUST_ALLOCATED_TO_RESERVATION); // to delete?
                        Date dispatchSentDate = new Date(r.getPickupDateTime().getTime() - 2 * HOUR);
                        DispatchRecord dispatch = new DispatchRecord(dispatchSentDate, car.getLicensePlateNos());
                        dispatch.setCarreservation(rByCat);
                        dispatch.setOutlet(r.getPickUpOutlet());
                        this.dispatchRecordSessionBeanLocal.createNewDispatchRecord(dispatch);
                        assignCarFromAnotherReservation = true;
                        break;
                    }
                }
                
                if (assignCarFromAnotherReservation == false) {
                    List<Car> carsWithSameCat = carSessionBeanLocal.retrieveCarsByCategory(cat.getName());
                    for (Car car : carsWithSameCat) {
                        if (car.getCarReservation() == null) {
                            r.setCar(car);
                            r.setModel(car.getModel());

                            car.setCarReservation(r);
                            car.setCarStatus(CarStatus.JUST_ALLOCATED_TO_RESERVATION);
                            Date dispatchSentDate = new Date(r.getPickupDateTime().getTime() - 2 * HOUR);
                            DispatchRecord dispatch = new DispatchRecord(dispatchSentDate, car.getLicensePlateNos());
                            dispatch.setCarreservation(r);
                            dispatch.setOutlet(r.getPickUpOutlet());
                            this.dispatchRecordSessionBeanLocal.createNewDispatchRecord(dispatch);
                            break;
                        }
                    }
                }
                
            } 
            
            if (r.getModel() != null) {
                Model model = r.getModel();
                List<CarReservation> reservationsByCat = carReservationSessionBeanLocal.retrieveCarReservationsByCategoryAndModel(cat.getName(), model.getModelName());
                boolean assignCarFromAnotherReservation = false;
                for (CarReservation rByCat : reservationsByCat) {
                    boolean canUseOtherCar = carReservationSessionBeanLocal.canReserveCarDroppedOffAtAnotherOutlet(rByCat, r.getPickupDateTime());
                    if (canUseOtherCar == true) {
                        Car car = rByCat.getCar();
                        r.setCar(car);
                        r.setModel(car.getModel());
                        car.setCarReservation(r); // to delete?
                        car.setCarStatus(CarStatus.JUST_ALLOCATED_TO_RESERVATION); // to delete?
                        Date dispatchSentDate = new Date(r.getPickupDateTime().getTime() - 2 * HOUR);
                        DispatchRecord dispatch = new DispatchRecord(dispatchSentDate, car.getLicensePlateNos());
                        dispatch.setCarreservation(rByCat);
                        dispatch.setOutlet(r.getPickUpOutlet());
                        this.dispatchRecordSessionBeanLocal.createNewDispatchRecord(dispatch);
                        assignCarFromAnotherReservation = true;
                        break;
                    }
                }   
                
                if (assignCarFromAnotherReservation == true) {
                    List<Car> carsWithSameCatAndModel = carSessionBeanLocal.retrieveCarsByCategoryAndModel(cat.getName(), model.getModelName());
                    for (Car car : carsWithSameCatAndModel) {
                        if (car.getCarReservation() == null) {
                            r.setCar(car);
                            r.setModel(car.getModel());

                            car.setCarReservation(r);
                            car.setCarStatus(CarStatus.JUST_ALLOCATED_TO_RESERVATION);
                            Date dispatchSentDate = new Date(r.getPickupDateTime().getTime() - 2 * HOUR);
                            DispatchRecord dispatch = new DispatchRecord(dispatchSentDate, car.getLicensePlateNos());
                            dispatch.setCarreservation(r);
                            dispatch.setOutlet(r.getPickUpOutlet());
                            this.dispatchRecordSessionBeanLocal.createNewDispatchRecord(dispatch);
                            break;
                        }
                    }
                }
            }      
        }
        
        for (CarReservation r : carReservationsNotAllocated) {
            System.out.println(r.getCarReservationId());
        }
    }
}
