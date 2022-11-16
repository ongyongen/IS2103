/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CarReservation;
import entity.Category;
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
import javax.ejb.Local;
import javax.persistence.Query;

/**
 *
 * @author ongyongen
 */
@Local
public interface CarReservationSessionBeanLocal {
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
    public List<CarReservation> retrieveAllCarReservations();
    public List<CarReservation> retrieveReservationsByDate(Date currentDate) throws ParseException;
    public boolean canReserveCarDroppedOffAtAnotherOutlet(CarReservation reservation, Date pickup);
    public List<CarReservation> retrieveCarReservationsByCategory(String catName);
    public List<CarReservation> retrieveCarReservationsByCategoryAndModel(String catName, String modelName);

}

