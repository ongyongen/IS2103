/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import enumeration.EmployeeDispatchStatus;
import enumeration.EmployeeRole;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author ongyongen
 */
@Entity
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;
    
    @ManyToOne(optional = false)
    @JoinColumn
    private Outlet outlet;
    
    @Column(nullable = false)
    private EmployeeRole role;

    @Column(length = 32, unique = true)
    private String username;
    
    @Column(length = 32)
    private String password;
    
    @OneToOne(mappedBy="employee")
    private DispatchRecord dispatchRecord;
    
 
    private EmployeeDispatchStatus dispatchStatus;

    public Employee() {
    }
    
    
    public Employee(String employeeName, Outlet outlet, EmployeeRole role) {
        this.username = employeeName;
        this.outlet = outlet;
        this.role = role;
        this.password = "password";      
    }
    

    public Employee(Outlet outlet, EmployeeRole role, String username, String password) {
        this.outlet = outlet;
        this.role = role;
        this.username = username;
        this.password = password;
        this.dispatchStatus = EmployeeDispatchStatus.NOT_ASSIGNED_CAR_DISPATCH;
    }
    
    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public Outlet getOutlet() {
        return outlet;
    }

    public void setOutlet(Outlet outlet) {
        this.outlet = outlet;
    }

    public EmployeeRole getRole() {
        return role;
    }

    public void setRole(EmployeeRole role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public DispatchRecord getDispatchRecord() {
        return dispatchRecord;
    }

    public void setDispatchRecord(DispatchRecord dispatchRecord) {
        this.dispatchRecord = dispatchRecord;
    }

    public EmployeeDispatchStatus getDispatchStatus() {
        return dispatchStatus;
    }

    public void setDispatchStatus(EmployeeDispatchStatus dispatchStatus) {
        this.dispatchStatus = dispatchStatus;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (employeeId != null ? employeeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the employeeId fields are not set
        if (!(object instanceof Employee)) {
            return false;
        }
        Employee other = (Employee) object;
        if ((this.employeeId == null && other.employeeId != null) || (this.employeeId != null && !this.employeeId.equals(other.employeeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Employee[ id=" + employeeId + " ]";
    }
    
}
