/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.sql.Time;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import java.text.SimpleDateFormat;
import java.text.ParseException;

/**
 *
 * @author ongyongen
 */
@Entity
public class Outlet implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long outletId;
    
    @Column(length = 32, unique = true)
    private String outletName;
    
    @Column(length = 32)
    private String address;
    
    @Column(length = 6)
    private Time openingHour;
    
    @Column(length = 6)
    private Time closingHour;
    
    @OneToMany(mappedBy="outlet")
    private List<Employee> employees;
    
    @OneToOne(mappedBy="outlet")
    private DispatchRecord dispatchRecord;
    
    @OneToMany(mappedBy="outlet")
    private List<Car> cars;
    
    
    public Outlet() {
    }
    
    public Outlet(String outletName) {
        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");
        this.outletName = outletName;
        this.openingHour = Time.valueOf("00:00:00");
        this.closingHour = Time.valueOf("23:59:59");
    }
    
    public Outlet(String outletName, Time openingHour, Time closingHour) {
        this.outletName = outletName;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
    }

    public Outlet(String outletName, String address, Time openingHour, Time closingHour) {
        this.outletName = outletName;
        this.address = address;
        this.openingHour = openingHour;
        this.closingHour = closingHour;
    }
    
    public Long getOutletId() {
        return outletId;
    }

    public void setOutletId(Long outletId) {
        this.outletId = outletId;
    }

    public String getOutletName() {
        return outletName;
    }

    public void setOutletName(String outletName) {
        this.outletName = outletName;
    }
    
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Time getOpeningHour() {
        return openingHour;
    }

    public void setOpeningHour(Time openingHour) {
        this.openingHour = openingHour;
    }

    public Time getClosingHour() {
        return closingHour;
    }

    public void setClosingHour(Time closingHour) {
        this.closingHour = closingHour;
    }
    
    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void setCars(List<Car> cars) {
        this.cars = cars;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (outletId != null ? outletId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the outletId fields are not set
        if (!(object instanceof Outlet)) {
            return false;
        }
        Outlet other = (Outlet) object;
        if ((this.outletId == null && other.outletId != null) || (this.outletId != null && !this.outletId.equals(other.outletId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Outlet[ id=" + outletId + " ]";
    }
    
}