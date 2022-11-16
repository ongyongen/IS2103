/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import enumeration.RentalRateStatus;
import enumeration.RentalRateType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author ongyongen
 */
@Entity
public class RentalRate implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentalRateId;
    
    @Column(length = 32, nullable = false, unique = true)
    private String name;
     
    @Column(length = 32)
    private BigDecimal ratePerDay;
    
    @Column
    private RentalRateType type;
    
    @Column
    private Date startDate;
    
    @Column
    private Date endDate;

    @Column
    private RentalRateStatus status;
    
    private Long validityPeriod;

    @ManyToOne
    @JoinColumn
    private Category category;

    
    public RentalRate() {
    }

    public RentalRate(String name, RentalRateType type, BigDecimal ratePerDay) {
        this.name = name;
        this.type = type;
        this.ratePerDay = ratePerDay;
        this.status = RentalRateStatus.NOT_IN_USE;
    }
    
    public RentalRate(String name, BigDecimal ratePerDay, RentalRateType type, Date startDate, Date endDate) {
        this.name = name;
        this.ratePerDay = ratePerDay;
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = RentalRateStatus.NOT_IN_USE;
        this.validityPeriod = endDate.getTime() - startDate.getTime();
    }
   
    public Long getRentalRateId() {
        return rentalRateId;
    }

    public void setRentalRateId(Long rentalRateId) {
        this.rentalRateId = rentalRateId;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getRatePerDay() {
        return ratePerDay;
    }

    public void setRatePerDay(BigDecimal ratePerDay) {
        this.ratePerDay = ratePerDay;
    }

    public RentalRateType getType() {
        return type;
    }

    public void setType(RentalRateType type) {
        this.type = type;
    }
    
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Long getValidityPeriod() {
        return validityPeriod;
    }

    public void setValidityPeriod(Long validityPeriod) {
        this.validityPeriod = validityPeriod;
    }
    
    
    public Category getCategory() {
        return this.category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
    
    public RentalRateStatus getStatus() {
        return status;
    }

    public void setStatus(RentalRateStatus status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rentalRateId != null ? rentalRateId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the rentalRateId fields are not set
        if (!(object instanceof RentalRate)) {
            return false;
        }
        RentalRate other = (RentalRate) object;
        if ((this.rentalRateId == null && other.rentalRateId != null) || (this.rentalRateId != null && !this.rentalRateId.equals(other.rentalRateId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RentalRate[ id=" + rentalRateId + " ]";
    }
    
}
