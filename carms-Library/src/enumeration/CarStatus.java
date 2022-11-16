/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enumeration;

/**
 *
 * @author ongyongen
 */
public enum CarStatus {
    AVAILABLE,
    IN_SERVICE_OR_REPAIR,
    JUST_ALLOCATED_TO_RESERVATION,
    IN_RENTAL_TRANSIT,
    IN_TRANSIT_TO_OTHER_OUTLET_FOR_RENTAL,
    COMPLETED_TRANSIT_TO_OTHER_OUTLET,
    DISABLED
}
