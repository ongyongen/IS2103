/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Category;
import entity.RentalRate;
import exception.CategoryDoesNotExistException;
import exception.DuplicateRentalRateException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.Query;

/**
 *
 * @author ongyongen
 */
@Local
public interface RentalRateSessionBeanLocal {
    public Long createRentalRate(RentalRate rentalRate, String categoryName) throws CategoryDoesNotExistException, DuplicateRentalRateException;
    
    public List<RentalRate> retrieveAllRentalRates();
    
    public BigDecimal calculateTotalRentalRate(String categoryName, String startDate, String endDate) throws ParseException;

    
}
