package ru.alex.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.alex.entity.Record;
import ru.alex.entity.RecordStatus;

import java.util.Collections;
import java.util.List;


@Repository

public class RecordDao {

    @PersistenceContext
    private EntityManager entityManager;



    public List<Record> findAllRecords() {

        Query query = entityManager.createQuery("select r from Record r order by r.id asc");
        List<Record> records = query.getResultList();
        return records;
    }


    public void saveRecord(Record record) {

        entityManager.persist(record);

    }


    public void updateRecordStatus(int id, RecordStatus newstatus) {

        Query query = entityManager.createQuery("update Record set status = :status where id = :id");
        query.setParameter("id", id);
        query.setParameter("status", newstatus);
        query.executeUpdate();

    }


    public void deleteRecord(int id) {

        Query query = entityManager.createQuery("delete Record where id= :id");
        query.setParameter("id", id);
        query.executeUpdate();

    }

}
