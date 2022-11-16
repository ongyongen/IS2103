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
import entity.Model;
import entity.Outlet;
import enumeration.CarReservationPaymentStatus;
import enumeration.CarReservationStatus;
import enumeration.CarStatus;
import exception.CarDoesNotExistException;
import exception.CarReservationDoesNotExistException;
import exception.CategoryDoesNotExistException;
import exception.ModelDoesNotExistException;
import exception.OutletDoesNotExistException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author ongyongen
 */
@Stateless
public class CarReservationSessionBean implements CarReservationSessionBeanRemote, CarReservationSessionBeanLocal {

    @EJB
    private CustomerSessionBeanLocal customerSessionBeanLocal;

    @EJB
    private RentalRateSessionBeanLocal rentalRateSessionBeanLocal;

    @EJB
    private CarSessionBeanLocal carSessionBeanLocal;

    @EJB
    private OutletSessionBeanLocal outletSessionBeanLocal;

    @EJB
    private ModelSessionBeanLocal modelSessionBeanLocal;

    @EJB
    private CategorySessionBeanLocal categorySessionBeanLocal;
    
    

    @PersistenceContext(unitName = "carms-ejbPU")
    private EntityManager em;
  

    
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
    ) throws CarDoesNotExistException, OutletDoesNotExistException, ParseException, CategoryDoesNotExistException  {
        
        Customer customer = this.customerSessionBeanLocal.retrieveCustomerByEmail(customerEmail);
        Category cat = this.categorySessionBeanLocal.retrieveCategoryByName(categoryName);
        Outlet pickupOutlet = this.outletSessionBeanLocal.retrieveOutletByName(pickupOutletName);
        Outlet dropoffOutlet = this.outletSessionBeanLocal.retrieveOutletByName(dropoffOutletName);
        
        CarReservation cr = new CarReservation(cat,pickupDateTime, 
                dropoffDateTime,pickupOutlet, dropoffOutlet, ccNos, rentalFee, payment);
          
        cr.setCarCategory(cat);
        
        cr.setCustomer(customer);
        em.persist(cr);

        cr.getCustomer().getCarReservations().add(cr);

        //cr.getCar().getCarReservations().add(cr);

        em.flush();
        return cr.getCarReservationId();
    }
    
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
    ) throws CarDoesNotExistException, OutletDoesNotExistException, ParseException, CategoryDoesNotExistException, ModelDoesNotExistException  {
        
        Customer customer = this.customerSessionBeanLocal.retrieveCustomerByEmail(customerEmail);
        Category cat = this.categorySessionBeanLocal.retrieveCategoryByName(categoryName);
        Model model = this.modelSessionBeanLocal.retrieveModelByName(modelName);
        Outlet pickupOutlet = this.outletSessionBeanLocal.retrieveOutletByName(pickupOutletName);
        Outlet dropoffOutlet = this.outletSessionBeanLocal.retrieveOutletByName(dropoffOutletName);
        
        CarReservation cr = new CarReservation(cat, model,pickupDateTime, 
                dropoffDateTime,pickupOutlet, dropoffOutlet, ccNos, rentalFee, payment);
          
        cr.setCarCategory(cat);
        cr.setModel(model);
        
        cr.setCustomer(customer);
        em.persist(cr);

        cr.getCustomer().getCarReservations().add(cr);

        em.flush();
        return cr.getCarReservationId();
    }
    
    public BigDecimal calculatePenaltyIncurred(CarReservation reservation, Date currentDate) {
        BigDecimal fee = reservation.getRentalFee();
        BigDecimal penalty = new BigDecimal(-1);
        Long diff = currentDate.getTime() - reservation.getPickupDateTime().getTime();
        Long days = (diff / (1000*60*60*24));
        if (days >= 14) {
            penalty = new BigDecimal(0);
        } else {
            if (days >= 7 && days < 14) {
                penalty = fee.multiply(new BigDecimal(0.2));
            } 
            else if (days >= 3 && days < 7) {
                penalty = fee.multiply(new BigDecimal(0.5));
            }
            else {
                penalty = fee.multiply(new BigDecimal(0.7));
            }
        }
        
        if (reservation.getPayment().equals(CarReservationPaymentStatus.PAY_AT_COLLECTION)) {
            return penalty;
        } else {
            return fee.subtract(penalty);
        }
    }
    
    public String cancelReservation(Long reservationId, Date currentDate, String creditCard) {
       
        CarReservation reservation = retrieveCarReservationById(reservationId);
        
        BigDecimal penalty = calculatePenaltyIncurred(reservation, currentDate);
        penalty =  penalty.setScale(2, BigDecimal.ROUND_HALF_EVEN);
       
        reservation.getCustomer().getCarReservations().remove(reservation);     
        reservation.setCustomer(null);

        if (reservation.getCar() != null) {
            if (reservation.getCar().getCarReservation().equals(reservation)) {
                reservation.getCar().setCarReservation(null);
            }
        }
                
        em.remove(reservation);
        
        return "A penalty of $" + penalty + " is charged to your credit card: " + creditCard;
  
    }
    
    public String registerCarPickup(Long reservationId) {
        CarReservation reservation = retrieveCarReservationById(reservationId);
        Car reservedCar = reservation.getCar();
        
        reservedCar.setLocation("In transit to " + reservation.getDropOffOutlet().getOutletName());
        
        reservedCar.setCarStatus(CarStatus.IN_RENTAL_TRANSIT);
        
        if (reservation.getPayment().equals(CarReservationPaymentStatus.PAY_AT_COLLECTION)) {
            return "Car is picked up. Please make a payment of $" + reservation.getRentalFee();
        } else {
            return "Car is picked up. Payment has already been made during booking";
        }
    }
    
    public String registerCarReturn(Long reservationId) {
        CarReservation reservation = retrieveCarReservationById(reservationId);
        Car reservedCar = reservation.getCar();
        
        reservation.setReservationStatus(CarReservationStatus.JOURNEY_COMPLETED);
        reservedCar.setOutlet(reservation.getDropOffOutlet());
        reservedCar.setLocation(reservation.getDropOffOutlet().getOutletName());
        reservedCar.setCarStatus(CarStatus.AVAILABLE); 
        reservedCar.setCarReservation(null);
        
        return "Car is returned to Outlet " + reservation.getDropOffOutlet().getOutletName();

    }
    
    public List<CarReservation> retrieveAllCarReservations() {
        Query query = em.createQuery("SELECT reservation FROM CarReservation reservation");
        return query.getResultList();
    }
    
    public CarReservation retrieveCarReservationById(Long id) {
        CarReservation r = em.find(CarReservation.class, id);
        return r;
    }
    
    public List<CarReservation> retrieveReservationsByDate(Date currentDate) throws ParseException {
        //SimpleDateFormat formatterDateTime = new SimpleDateFormat("dd-MM-yy HH:mm");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy HH:mm");

      
        Date currentDay = dateFormat.parse(dateFormat.format(currentDate));
        Date nextDay = new Date(currentDate.getTime() + TimeUnit.DAYS.toMillis( 1 ));
        Query query = em.createQuery("SELECT r FROM CarReservation r "
                + "WHERE r.pickupDateTime >= :currentDay "
                + "AND r.pickupDateTime < :nextDay");
        query.setParameter("currentDay", currentDay);
        query.setParameter("nextDay", nextDay);
        return query.getResultList();
    }


    public List<CarReservation> retrieveCarReservationsByCategory(String catName) {
         Query query = em.createQuery("SELECT r FROM CarReservation r "
                + "WHERE r.carCategory.name = :catName");
        query.setParameter("catName", catName);
        return query.getResultList();
    }
    
   
    public List<CarReservation> retrieveCarReservationsByCategoryAndModel(String catName, String modelName) {
         Query query = em.createQuery("SELECT r FROM CarReservation r "
                + "WHERE r.carCategory.name = :catName "
                 + "AND r.model.modelName = :modelName");
        query.setParameter("catName", catName);
        query.setParameter("modelName", modelName);
        return query.getResultList();
    }
    
    public List<CarReservation> retrieveCarReservationByCustomer(String email) {
        Query query = em.createQuery("SELECT r FROM CarReservation r "
                + "WHERE r.customer.email = :email");
        query.setParameter("email", email);
        return query.getResultList();
    }
    
    public boolean doesDateRangeOverlap(Date start1, Date end1, Date start2, Date end2) {
        /* 
        From : https://stackoverflow.com/questions/325933/determine-whether-two-date-ranges-overlap
        (StartDate1 <= EndDate2) and (StartDate2 <= EndDate1)      
        */
        return (start1.before(end2) || start1.equals(end2)) && 
               (start2.before(end1) || start2.equals(end1));   
    }
    
    /* Already filtered by Category & model */
    public boolean canReserveCarDroppedOffAtAnotherOutlet(CarReservation reservation, Date pickup) {
        
        Date prevDropoffTime = reservation.getDropoffDateTime();
        Date newPickUpTime = pickup;
        long diff = newPickUpTime.getTime() - prevDropoffTime.getTime();
        Integer diffHours = (int) TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
        Integer diffHoursAbs = Math.abs(diffHours);
        if (prevDropoffTime.before(newPickUpTime) && diffHoursAbs >= 2) {
            return true;
        } else {
            return false;
        }
    }
    
    public boolean canCreateReservation(Date start1, Date end1, Date start2, Date end2) {
        boolean overlap = this.doesDateRangeOverlap(start1, end1, start2, end2);
        if (overlap == false) {
            return true;
        } else {
            long diff = start2.getTime() - end1.getTime();
            Integer diffHours = (int) TimeUnit.HOURS.convert(diff, TimeUnit.MILLISECONDS);
            Integer diffHoursAbs = Math.abs(diffHours);
            if (end1.before(start2) && diffHoursAbs >= 2) {
                return true;
            } else {
                return false;
            }
        }  
    }
    
   
    //To delete
    public boolean canMakeReservation(String carNos, Date start1, Date end1, String pickupOutletName) throws CarDoesNotExistException, OutletDoesNotExistException {
        Car car = carSessionBeanLocal.retrieveCarByLicensePlateNos(carNos);
        Outlet pickupOutlet = outletSessionBeanLocal.retrieveOutletByName(pickupOutletName);
        if (car.getCarReservation() == null) {
            if (car.getOutlet().getOutletName() == pickupOutlet.getOutletName()) {
                return true;
            }
        } else {
           CarReservation r = car.getCarReservation();
           Date start2 = r.getPickupDateTime();
           Date end2 = r.getDropoffDateTime();
           if (this.doesDateRangeOverlap(start1, end1, start2, end2) == false) {
               if (car.getOutlet() == pickupOutlet) {
                   return true;
               } else {
                   if (this.canReserveCarDroppedOffAtAnotherOutlet(r, start1)) {
                       return true;
                   }
               }
           }
        }
               
        return false;
    }
    

    
    public Integer nosCanMakeReservationByCategory(String categoryName, Date start1, Date end1, String pickupOutletName) throws CarDoesNotExistException, OutletDoesNotExistException, CategoryDoesNotExistException {
        List<CarReservation> reservations = this.retrieveCarReservationsByCategory(categoryName);
        Outlet pickupOutlet = outletSessionBeanLocal.retrieveOutletByName(pickupOutletName);
       
        List<Car> allCarsOfChosenCategory = this.carSessionBeanLocal.retrieveCarsByCategory(categoryName);
        Integer unavailable = 0;
        for (CarReservation r : reservations) {
            Date start2 = r.getPickupDateTime();
            Date end2 = r.getDropoffDateTime();
            if (this.doesDateRangeOverlap(start1, end1, start2, end2) == false) {
                /** Exclude reservations made that returns car to ie outlet A when user wants outlet c **/
                if (!r.getDropOffOutlet().equals(pickupOutlet)) {
                    if (!this.canReserveCarDroppedOffAtAnotherOutlet(r, start1)) {
                        unavailable += 1;
                    }
                }
            } else {
                /** Exclude reservations made that overlap with desired time period **/
                unavailable += 1;
            }
        }
        
        Integer available = allCarsOfChosenCategory.size() - unavailable;
        
        return available;
 
    }
    
    public Integer nosCanMakeReservationByCategoryAndModel(String categoryName, String modelName, Date start1, Date end1, String pickupOutletName) throws CarDoesNotExistException, OutletDoesNotExistException, CategoryDoesNotExistException {

        List<CarReservation> reservations = this.retrieveCarReservationsByCategoryAndModel(categoryName, modelName);
        Outlet pickupOutlet = outletSessionBeanLocal.retrieveOutletByName(pickupOutletName);
       
        List<Car> allCarsOfChosenCategoryAndModel = this.carSessionBeanLocal.retrieveCarsByCategoryAndModel(categoryName, modelName);
        Integer unavailable = 0;
        for (CarReservation r : reservations) {
            Date start2 = r.getPickupDateTime();
            Date end2 = r.getDropoffDateTime();
            if (this.doesDateRangeOverlap(start1, end1, start2, end2) == false) {
                /** Exclude reservations made that returns car to ie outlet A when user wants outlet c **/
                if (!r.getDropOffOutlet().equals(pickupOutlet)) {
                    if (!this.canReserveCarDroppedOffAtAnotherOutlet(r, start1)) {
                        unavailable += 1;
                    }                      
                }
            } else {
                /** Exclude reservations made that overlap with desired time period **/
                unavailable += 1;
            }
        }
        
        Integer available = allCarsOfChosenCategoryAndModel.size() - unavailable;
        
        return available;
 
    }

}

          