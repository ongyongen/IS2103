/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import enumeration.CarStatus;
import enumeration.Category;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author ongyongen hello world
 */
@Entity
public class Car implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long carId;
    
    @Column(length = 32)
    private String make;
   
    @Column
    private boolean available;
    
    @Column(length = 32)
    private String licensePlateNos;
    
    @Column(length = 32)
    private String colour;
    
    @Column(length = 32)
    private CarStatus carStatus;
    
    @Column(length = 32)
    private String location;
    
    
    @ManyToOne(optional = false)
    @JoinColumn
    private Model model;
    
    @OneToOne(optional = true, fetch = FetchType.EAGER)
    private CarReservation carReservation;
    
    @ManyToOne
    private Outlet outlet;
    

    public Car() {
    }
    
    
    public Car(String licensePlateNos, CarStatus status) {
        this.licensePlateNos = licensePlateNos;
        this.carStatus = status;
    }


    public Car(String make, boolean available, String licensePlateNos, String colour, CarStatus status, String location) {
        this.make = make;
        this.available = available;
        this.licensePlateNos = licensePlateNos;
        this.colour = colour;
        this.carStatus = status;
        this.location = location;
    }

    
    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public String getLicensePlateNos() {
        return licensePlateNos;
    }

    public void setLicensePlateNos(String licensePlateNos) {
        this.licensePlateNos = licensePlateNos;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public CarStatus getCarStatus() {
        return carStatus;
    }

    public void setCarStatus(CarStatus carStatus) {
        this.carStatus = carStatus;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Outlet getOutlet() {
        return outlet;
    }

    public void setOutlet(Outlet outlet) {
        this.outlet = outlet;
    }

    public CarReservation getCarReservation() {
        return carReservation;
    }

    public void setCarReservation(CarReservation carReservation) {
        this.carReservation = carReservation;
    }



    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (carId != null ? carId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the carId fields are not set
        if (!(object instanceof Car)) {
            return false;
        }
        Car other = (Car) object;
        if ((this.carId == null && other.carId != null) || (this.carId != null && !this.carId.equals(other.carId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Car[ id=" + carId + " ]";
    }
    
}
