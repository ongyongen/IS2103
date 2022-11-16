/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import enumeration.CarReservationPaymentStatus;
import enumeration.CarReservationStatus;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
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
public class CarReservation implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carReservationId;
   
    @Column
    private BigDecimal rentalFee;
    
    @Column
    private Date pickupDateTime;
    
    @Column
    private Date dropoffDateTime;
    
    @Column
    private String ccNos;
        
    @Column
    private CarReservationPaymentStatus payment;
    
    @Column
    private CarReservationStatus reservationStatus;
        
    @ManyToOne
    private Outlet pickUpOutlet;
 
    @ManyToOne
    private Outlet dropOffOutlet;

    @ManyToOne
    private Model model;
    
    @ManyToOne
    private Category carCategory;
    
    @ManyToOne
    private Customer customer;
   
    @OneToOne(optional = true)
    private Car car;
    
    @OneToOne(mappedBy="carreservation")
    private DispatchRecord dispatchRecord;
    
    public CarReservation() {
    }

    public CarReservation(Category category, Date pickupDateTime, Date dropoffDateTime, 
            Outlet pickUpOutlet, Outlet returnOutlet, String ccNos, BigDecimal rentalFee,
            CarReservationPaymentStatus payment) {
        this.pickupDateTime = pickupDateTime;
        this.dropoffDateTime = dropoffDateTime;
        this.pickUpOutlet = pickUpOutlet;
        this.dropOffOutlet = returnOutlet;
        this.ccNos = ccNos;
        this.rentalFee = rentalFee;
        this.payment = payment;
        this.reservationStatus = CarReservationStatus.JOURNEY_NOT_COMPLETED;
    }
    
    public CarReservation(Category category, Model model, Date pickupDateTime, Date dropoffDateTime, 
            Outlet pickUpOutlet, Outlet returnOutlet, String ccNos, BigDecimal rentalFee,
            CarReservationPaymentStatus payment) {
        //this.car = car;
        this.pickupDateTime = pickupDateTime;
        this.dropoffDateTime = dropoffDateTime;
        this.pickUpOutlet = pickUpOutlet;
        this.dropOffOutlet = returnOutlet;
        this.ccNos = ccNos;
        this.rentalFee = rentalFee;
        this.payment = payment;
        this.carCategory = category;
        this.model = model;
        this.reservationStatus = CarReservationStatus.JOURNEY_NOT_COMPLETED;
    }

    public BigDecimal getRentalFee() {
        return rentalFee;
    }

    public void setRentalFee(BigDecimal rentalFee) {
        this.rentalFee = rentalFee;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }


    public String getCcNos() {
        return ccNos;
    }

    public void setCcNos(String ccNos) {
        this.ccNos = ccNos;
    }

    public CarReservationPaymentStatus getPayment() {
        return payment;
    }

    public void setPayment(CarReservationPaymentStatus payment) {
        this.payment = payment;
    }
   
    public Long getCarReservationId() {
        return carReservationId;
    }

    public void setCarReservationId(Long carReservationId) {
        this.carReservationId = carReservationId;
    }

    public Outlet getPickupOutlet() {
        return pickUpOutlet;
    }

    public void setPickupOutlet(Outlet pickupOutlet) {
        this.pickUpOutlet = pickupOutlet;
    }

    public Outlet getDropOffOutlet() {
        return dropOffOutlet;
    }

    public void setReturnLocation(Outlet returnOutlet) {
        this.dropOffOutlet = returnOutlet;
    }

    public Date getPickupDateTime() {
        return pickupDateTime;
    }

    public void setPickupDateTime(Date pickupDateTime) {
        this.pickupDateTime = pickupDateTime;
    }

    public Date getDropoffDateTime() {
        return dropoffDateTime;
    }

    public void setDropoffDateTime(Date dropoffDateTime) {
        this.dropoffDateTime = dropoffDateTime;
    }

    public Outlet getPickUpOutlet() {
        return pickUpOutlet;
    }

    public void setPickUpOutlet(Outlet pickUpOutlet) {
        this.pickUpOutlet = pickUpOutlet;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Category getCarCategory() {
        return carCategory;
    }

    public void setCarCategory(Category carCategory) {
        this.carCategory = carCategory;
    }

    public CarReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(CarReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }
    
    
     
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (carReservationId != null ? carReservationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the carReservationId fields are not set
        if (!(object instanceof CarReservation)) {
            return false;
        }
        CarReservation other = (CarReservation) object;
        if ((this.carReservationId == null && other.carReservationId != null) || (this.carReservationId != null && !this.carReservationId.equals(other.carReservationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CarReservation[ id=" + carReservationId + " ]";
    }
    
}
