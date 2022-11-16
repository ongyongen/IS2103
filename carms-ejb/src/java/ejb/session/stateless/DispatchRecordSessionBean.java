/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.CarReservation;
import entity.Customer;
import entity.DispatchRecord;
import entity.Employee;
import enumeration.CarStatus;
import enumeration.DispatchRecordStatus;
import enumeration.EmployeeDispatchStatus;
import exception.EmployeeAlreadyAssignedToDispatchRecordException;
import exception.EmployeeDoesNotExistException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class DispatchRecordSessionBean implements DispatchRecordSessionBeanRemote, DispatchRecordSessionBeanLocal {

    @EJB
    private EmployeeSessionBeanLocal employeeSessionBeanLocal;

    @PersistenceContext(unitName = "carms-ejbPU")
    private EntityManager em;
    
    
    public Long createNewDispatchRecord(DispatchRecord dispatchRecord) {
        em.persist(dispatchRecord);
        em.flush();
        return dispatchRecord.getDispatchRecordId();
    }
    
    public List<DispatchRecord> retrieveAllDispatchRecords() {
        Query query = em.createQuery("SELECT record FROM DispatchRecord record");
        return query.getResultList();
    }
    
    public boolean isSameDay(Date d1, Date d2) throws ParseException {
        SimpleDateFormat formatterDateTime = new SimpleDateFormat("dd-MM-yy");
        
        return formatterDateTime.format(d1).equals(formatterDateTime.format(d2));
    }
    
    public List<DispatchRecord> retrieveAllDispatchRecordsForCurrentDayAndOutlet(Date currentDate, String outletName) throws ParseException {
        List<DispatchRecord> recordsForAnOutlet = this.retrieveAllDispatchRecords();
        List<DispatchRecord> recordsByOutletAndDay = new ArrayList<DispatchRecord>();
        for (DispatchRecord r : recordsForAnOutlet) {
            Date recordDate = r.getDateOfDispatch();
            if (isSameDay(recordDate, currentDate) && r.getOutlet().getOutletName().equals(outletName)) {
                recordsByOutletAndDay.add(r);
            }
        }
        return recordsByOutletAndDay; 
    }
    
    public DispatchRecord retrieveDispatchRecordById(Long id) {
        DispatchRecord r = em.find(DispatchRecord.class, id);
        return r;
    }
    
    public String AssignDriverToRecord(String empUsername, Long id) throws EmployeeAlreadyAssignedToDispatchRecordException, EmployeeDoesNotExistException {
        DispatchRecord r = this.retrieveDispatchRecordById(id);
        Employee employee = employeeSessionBeanLocal.retrieveEmployeeByUsername(empUsername);
        if (employee.getDispatchStatus().equals(EmployeeDispatchStatus.NOT_ASSIGNED_CAR_DISPATCH)) {
            employee.setDispatchStatus(EmployeeDispatchStatus.ASSIGNED_CAR_DISPATCH);
            r.setEmployee(employee);           
            return "Employee with username of " + empUsername + " is assigned to Dispatch Record with ID of " + id;
            
        } else {
            throw new EmployeeAlreadyAssignedToDispatchRecordException("Employee is already assigned to dispatch record");        
        }
       
    }
    
    public String updateDispatchRecordAsCompleted(Long id) {
        DispatchRecord r = this.retrieveDispatchRecordById(id);
        r.setDispatchRecordStatus(DispatchRecordStatus.COMPLETED);
        r.getCarreservation().getCar().setCarStatus(CarStatus.COMPLETED_TRANSIT_TO_OTHER_OUTLET);
        r.getEmployee().setDispatchStatus(EmployeeDispatchStatus.NOT_ASSIGNED_CAR_DISPATCH);
        return "Dispatch record is updated as completed";
    }
 
}
