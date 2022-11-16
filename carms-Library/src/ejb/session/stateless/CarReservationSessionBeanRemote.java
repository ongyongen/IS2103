/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Car;
import entity.CarReservation;
import entity.Category;
import entity.Customer;
import entity.Outlet;
import enumeration.CarReservationPaymentStatus;
import exception.CarDoesNotExistException;
import exception.CategoryDoesNotExistException;
import exception.ModelDoesNotExistException;
import exception.OutletDoesNotExistException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;
import javax.persistence.Query;

/**
 *
 * @author ongyongen
 */
@Remote
public interface CarReservationSessionBeanRemote {
    public Long createNewCarReservationByCategory(
            String customerEmail,
            String categoryName,
            Date pickupDateTime,
            Date dropoffDateTime,
            String pickupOutletName, 
            String dropoffOutletName,
            String ccNos,
            BigDecimal rentalFee,
            CarReservationPaymentStatus payment
    ) throws CarDoesNotExistException, OutletDoesNotExistException, ParseException, CategoryDoesNotExistException;
   
    public Long createNewCarReservationByCategoryAndModel(
            String customerEmail,
            String categoryName,
            String modelName,
            Date pickupDateTime,
            Date dropoffDateTime,
            String pickupOutletName, 
            String dropoffOutletName,
            String ccNos,
            BigDecimal rentalFee,
            CarReservationPaymentStatus payment
    ) throws CarDoesNotExistException, OutletDoesNotExistException, ParseException, CategoryDoesNotExistException, ModelDoesNotExistException;
        
    public List<CarReservation> retrieveAllCarReservations();
   
    public Integer nosCanMakeReservationByCategory(String categoryName, Date start1, Date end1, String pickupOutletName) throws CarDoesNotExistException, OutletDoesNotExistException, CategoryDoesNotExistException;    

    public CarReservation retrieveCarReservationById(Long id );
    
    public Integer nosCanMakeReservationByCategoryAndModel(String categoryName, String modelName, Date start1, Date end1, String pickupOutletName) throws CarDoesNotExistException, OutletDoesNotExistException, CategoryDoesNotExistException;

    public List<CarReservation> retrieveCarReservationByCustomer(String email);

    public String cancelReservation(Long reservationId, Date currentDate, String creditCard);
    
    public String registerCarPickup(Long reservationId);
    
    public String registerCarReturn(Long reservationId);
    
    public boolean canMakeReservation(String carNos, Date start1, Date end1, String pickupOutletName) throws CarDoesNotExistException, OutletDoesNotExistException;

    public boolean canCreateReservation(Date start1, Date end1, Date start2, Date end2);

}


