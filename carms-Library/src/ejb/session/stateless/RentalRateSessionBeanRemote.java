/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Category;
import entity.RentalRate;
import enumeration.RentalRateStatus;
import exception.CategoryDoesNotExistException;
import exception.DuplicateRentalRateException;
import exception.RentalRateDoesNotExistException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import javax.ejb.Remote;
import javax.persistence.Query;

/**
 *
 * @author ongyongen
 */
@Remote
public interface RentalRateSessionBeanRemote {
    public Long createRentalRate(RentalRate rentalRate, String categoryName) throws CategoryDoesNotExistException, DuplicateRentalRateException;
    
    public List<RentalRate> retrieveAllRentalRates();
    
    public RentalRate retrieveRentalRatesByName(String name) throws RentalRateDoesNotExistException;
    
    public String viewRentalRateDetails(String name) throws RentalRateDoesNotExistException;
    
    public void updateRentalRate(String oldRateName, RentalRate newRate, String newCategory) throws CategoryDoesNotExistException, RentalRateDoesNotExistException;
    
    public String deleteRentalRate(String name) throws RentalRateDoesNotExistException;
    
    public BigDecimal calculateTotalRentalRate(String categoryName, String startDate, String endDate) throws ParseException;

}
