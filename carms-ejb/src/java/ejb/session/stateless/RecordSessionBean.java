/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb.session.stateless;

import entity.Record;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author ongyongen
 */
@Stateless
public class RecordSessionBean implements RecordSessionBeanRemote, RecordSessionBeanLocal {

    @PersistenceContext(unitName = "carms-ejbPU")
    private EntityManager em;
    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
    @Override
    public Long createNewRecord(Record record) {
        em.persist(record);
        em.flush(); // as we use identity generation strategy for PK of Record
        return record.getRecordId();
    }
    
    @Override
    public List<Record> retrieveAllRecords() {
        Query query = em.createQuery("SELECT r FROM Record r");
        return query.getResultList();
    }
}
