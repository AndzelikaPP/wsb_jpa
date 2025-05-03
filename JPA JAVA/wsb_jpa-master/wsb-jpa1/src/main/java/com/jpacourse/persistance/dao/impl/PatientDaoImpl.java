package com.jpacourse.persistance.dao.impl;

import com.jpacourse.persistance.dao.DoctorDao;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.dao.VisitDao;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PatientDaoImpl extends AbstractDao<PatientEntity, Long> implements PatientDao
{

    @Override
    public List<PatientEntity> findByLastName(String lastName) {
        String jpql = "SELECT p FROM PatientEntity p WHERE p.lastName = :lastName";
        return entityManager.createQuery(jpql, PatientEntity.class)
                .setParameter("lastName", lastName)
                .getResultList();
    }

    @Override
    public List<PatientEntity> findPatientsWithMoreThanXVisits(long visitCount) {
        String jpql = """
        SELECT p FROM PatientEntity p
        WHERE (SELECT COUNT(v) FROM VisitEntity v WHERE v.patient = p) > :count
    """;
        return entityManager.createQuery(jpql, PatientEntity.class)
                .setParameter("count", visitCount)
                .getResultList();
    }


    @Autowired
    private VisitDao visitDao;

    @Autowired
    private DoctorDao doctorDao;

    @Override
    public VisitEntity addVisit(Long patientId, Long doctorId, LocalDateTime time, String description) {
        VisitEntity visitToSave = new VisitEntity();
        visitToSave.setPatient(this.findOne(patientId));
        visitToSave.setDoctor(doctorDao.findOne(doctorId));
        visitToSave.setTime(time);
        visitToSave.setDescription(description);
        return visitDao.save(visitToSave);
    }

    @Override
    public List<PatientEntity> findByRegistrationDateAfter(LocalDate date) {
        String jpql = "SELECT p FROM PatientEntity p WHERE p.registrationDate > :date";
        return entityManager.createQuery(jpql, PatientEntity.class)
                .setParameter("date", date)
                .getResultList();
    }


}
