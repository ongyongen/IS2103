/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CarReservation;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author ongyongen
 */
@Remote
public interface EJBTimerSessionBeanRemote {
        public void allocateCarsToReservations(Date currentDate) throws ParseException;

}
