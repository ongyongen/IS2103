/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Category;
import entity.RentalRate;
import enumeration.RentalRateStatus;
import enumeration.RentalRateType;
import exception.CategoryDoesNotExistException;
import exception.DuplicateRentalRateException;
import exception.RentalRateDoesNotExistException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;

/**
 *
 * @author ongyongen
 */
@Stateless
public class RentalRateSessionBean implements RentalRateSessionBeanRemote, RentalRateSessionBeanLocal {

    @PersistenceContext(unitName = "carms-ejbPU")
    private EntityManager em;

    @EJB
    private CategorySessionBeanLocal categorySessionBeanLocal;
    
    @Override
    public Long createRentalRate(RentalRate rentalRate, String categoryName) throws CategoryDoesNotExistException, DuplicateRentalRateException {
        Category category = categorySessionBeanLocal.retrieveCategoryByName(categoryName);     
        String name = rentalRate.getName();
        Query query = em.createQuery("SELECT rate FROM RentalRate rate WHERE rate.name = :name");
        query.setParameter("name", name);
        if (query.getResultList().size() != 0) {
            throw new DuplicateRentalRateException("There is already a rental rate record with the same name. "
                    + "No new rental rate record is created");
        } else {
            rentalRate.setCategory(category);
            category.getRentalRates().add(rentalRate);
            em.persist(rentalRate);
            em.flush();
            return rentalRate.getRentalRateId();            
        }
    }
    
    public List<RentalRate> retrieveAllRentalRates() {
        Query query = em.createQuery("SELECT rate FROM RentalRate rate ORDER BY rate.category.name ASC, rate.validityPeriod ASC");
        return query.getResultList();
    }
    
    public RentalRate retrieveRentalRatesByName(String name) throws RentalRateDoesNotExistException {
        Query query = em.createQuery("SELECT rate FROM RentalRate rate WHERE rate.name = :name");
        query.setParameter("name", name);
        if (query.getResultList().size() == 0) {
            throw new RentalRateDoesNotExistException("Rental rate does not exist");
        } else {
            return (RentalRate)query.getSingleResult();
        }
    }
    
    public String viewRentalRateDetails(String name) throws RentalRateDoesNotExistException {
        RentalRate rate = retrieveRentalRatesByName(name);
        String rateName = "Name: " + rate.getName();
        String cat = "Category: " + rate.getCategory().getName();
        String rateDay = "Rate per day: " + rate.getRatePerDay();
        String type ="Rate Type: " + rate.getType();
        String start = "Start Date: " + rate.getStartDate();
        String end = "End Date: " + rate.getEndDate();
        String status = "Rate Status: " + rate.getStatus().name();
        String outputString = rateName + "\n" + cat + "\n" + rateDay + " \n" + type + "\n" + start + "\n" + end + "\n" + status;
        return outputString;
    }
    
    public void updateRentalRate(String oldRateName, RentalRate newRate, String newCategory) 
            throws CategoryDoesNotExistException, RentalRateDoesNotExistException {
        RentalRate oldRate = retrieveRentalRatesByName(oldRateName);
        oldRate.setName(newRate.getName());
        oldRate.setCategory(categorySessionBeanLocal.retrieveCategoryByName(newCategory));
        oldRate.setRatePerDay(newRate.getRatePerDay());
        oldRate.setType(newRate.getType());
        oldRate.setStartDate(newRate.getStartDate());
        oldRate.setEndDate(newRate.getEndDate());
        oldRate.setStatus(newRate.getStatus());
    }
    
    public String deleteRentalRate(String name) throws RentalRateDoesNotExistException {
        RentalRate rate = retrieveRentalRatesByName(name);
        if (rate.getStatus().equals(RentalRateStatus.NOT_IN_USE)) {
            rate.getCategory().getRentalRates().remove(rate);
            em.remove(rate);
            return "Rental rate with name " + name + " is deleted";
        } else {
            rate.setStatus(RentalRateStatus.DISABLED);  
            return "Rental rate with name " + name + " is disabled";
        }
        
    }
    
    public RentalRate retrieveLowestRentalRateForADay(String categoryName, Date currentDay) {
       /** Query query = em.createQuery("SELECT r FROM RentalRate r "
        * r.startDate BEFORE :inCurrentDay AND r.endDate AFTER :inCurrentDay
        * OR r.startDate = :inCurrentDay AND r.endDate = :inCurrentDay
                + "WHERE (r.startDate <= :inCurrentDay AND r.endDate >= :inCurrentDay) "
                + "OR (r.startDate IS NULL AND r.endDate IS NULL "
                + "AND r.category.name = :inCategoryName"
                
                * + "WHERE r.startDate IS NULL AND r.endDate IS NULL"

                + "ORDER BY r.ratePerDay ASC"); **/
       
        Query query = em.createQuery("SELECT r FROM RentalRate r "
                + "WHERE (r.startDate IS NULL AND r.endDate IS NULL) "
                + "OR (r.startDate <= :inCurrentDay AND r.endDate >= :inCurrentDay) " 
                + "ORDER BY r.ratePerDay ASC ");
        
        query.setParameter("inCurrentDay", currentDay);
        //query.setParameter("inCategoryName", categoryName);
        
        List<RentalRate> ratesAllCat = query.getResultList();
        List<RentalRate> rates = new ArrayList();
        
        for (RentalRate r : ratesAllCat) {
            if (r.getCategory().getName().equals(categoryName)) {
                rates.add(r);
            }
        }
        
        List<RentalRateType> rateTypes = new ArrayList();
        for (RentalRate rate : rates) {
            if (rate.getStatus() != RentalRateStatus.DISABLED) {
                rateTypes.add(rate.getType()); 
            }
        }
        
        if (rateTypes.contains(RentalRateType.DEFAULT) && 
            rateTypes.contains(RentalRateType.PEAK) && 
            !rateTypes.contains(RentalRateType.PROMOTION)) {
            for (RentalRate r : rates) {
                if (r.getType().equals(RentalRateType.PEAK)) {
                    r.setStatus(RentalRateStatus.IN_USE);
                    return r;
                }
            }
        }
        rates.get(0).setStatus(RentalRateStatus.IN_USE);
        return rates.get(0);
    }
    
    public BigDecimal calculateTotalRentalRate(String categoryName, String startDate, String endDate) throws ParseException {
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        Date currentDay = formatter.parse(startDate);
        Date endDay = formatter.parse(endDate);
       
        BigDecimal rate = new BigDecimal(0);
        while (currentDay.before(endDay)) {
            RentalRate rateLowest = this.retrieveLowestRentalRateForADay(categoryName, currentDay);
            rate = rate.add(rateLowest.getRatePerDay());
            
            /**
             * 
             * rateLowest.
             */
   
            Calendar c = Calendar.getInstance(); 
            c.setTime(currentDay); 
            c.add(Calendar.DATE, 1);
            currentDay = c.getTime();
          

        }
        return rate;
    }
   
    /**
    public BigDecimal calculateTotalRentalRate(String categoryName, String startDate, String endDate) throws ParseException {
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        Date currentDay = formatter.parse(startDate);
        Date endDay = formatter.parse(endDate);
       
        BigDecimal rate = new BigDecimal(0);
        while (currentDay.before(endDay)) {
            RentalRate rateLowest = this.retrieveLowestRentalRateForADay(categoryName, currentDay);
            rate = rate.add(rateLowest.getRatePerDay());
            
            Calendar c = Calendar.getInstance(); 
            c.setTime(currentDay); 
            c.add(Calendar.DATE, 1);
            currentDay = c.getTime();

        }
        return rate;
    }**/
}
