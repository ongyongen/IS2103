/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.DispatchRecord;
import exception.EmployeeAlreadyAssignedToDispatchRecordException;
import exception.EmployeeDoesNotExistException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author ongyongen
 */
@Remote
public interface DispatchRecordSessionBeanRemote {
    public Long createNewDispatchRecord(DispatchRecord dispatchRecord);
    
    public List<DispatchRecord> retrieveAllDispatchRecords();
    
    public List<DispatchRecord> retrieveAllDispatchRecordsForCurrentDayAndOutlet(Date currentDate, String outletName) throws ParseException;


    public String updateDispatchRecordAsCompleted(Long id);
        
    public String AssignDriverToRecord(String empUsername, Long id) throws EmployeeAlreadyAssignedToDispatchRecordException, EmployeeDoesNotExistException;


}
