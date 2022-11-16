/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import enumeration.DispatchRecordStatus;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author ongyongen
 */
@Entity
public class DispatchRecord implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dispatchRecordId;
    
    @Column(length = 6)
    private Date dateOfDispatch;
    
    private DispatchRecordStatus dispatchRecordStatus;
    
    private String licenseNosCarToPickUp;
    
    @OneToOne
    private CarReservation carreservation;
    
    @OneToOne
    private Outlet outlet;
    
    @OneToOne(optional = true)
    private Employee employee; 
     

    public DispatchRecord() {
    }

    public DispatchRecord(Date dateOfDispatch, String licenseNosCarToPickUp) {
        this.dateOfDispatch = dateOfDispatch;
        this.licenseNosCarToPickUp = licenseNosCarToPickUp;
        this.dispatchRecordStatus = DispatchRecordStatus.SCHEDULED_BUT_NOT_COMPLETED;
    }
    
    public Long getDispatchRecordId() {
        return dispatchRecordId;
    }

    public void setDispatchRecordId(Long dispatchRecordId) {
        this.dispatchRecordId = dispatchRecordId;
    }

    public Date getDateOfDispatch() {
        return dateOfDispatch;
    }

    public void setDateOfDispatch(Date dateOfDispatch) {
        this.dateOfDispatch = dateOfDispatch;
    }

    public DispatchRecordStatus getDispatchRecordStatus() {
        return dispatchRecordStatus;
    }

    public void setDispatchRecordStatus(DispatchRecordStatus dispatchRecordStatus) {
        this.dispatchRecordStatus = dispatchRecordStatus;
    }

    public CarReservation getCarreservation() {
        return carreservation;
    }

    public void setCarreservation(CarReservation carreservation) {
        this.carreservation = carreservation;
    }

    public String getLicenseNosCarToPickUp() {
        return licenseNosCarToPickUp;
    }

    public void setLicenseNosCarToPickUp(String licenseNosCarToPickUp) {
        this.licenseNosCarToPickUp = licenseNosCarToPickUp;
    }

    public Outlet getOutlet() {
        return outlet;
    }

    public void setOutlet(Outlet outlet) {
        this.outlet = outlet;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dispatchRecordId != null ? dispatchRecordId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the dispatchRecordId fields are not set
        if (!(object instanceof DispatchRecord)) {
            return false;
        }
        DispatchRecord other = (DispatchRecord) object;
        if ((this.dispatchRecordId == null && other.dispatchRecordId != null) || (this.dispatchRecordId != null && !this.dispatchRecordId.equals(other.dispatchRecordId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.DispatchRecord[ id=" + dispatchRecordId + " ]";
    }
    
}
